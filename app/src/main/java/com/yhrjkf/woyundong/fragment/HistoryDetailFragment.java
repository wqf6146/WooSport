package com.yhrjkf.woyundong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.CacheResult;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;

import com.yhrjkf.woyundong.activity.HistoryMapActivity;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.SportDayInfoBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.view.CookieBar;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/18.
 */

public class HistoryDetailFragment extends BaseFragment {

    @Bind(R.id.fhd_img_back)
    ImageView mImgBack;

    @Bind(R.id.fhd_tv_date)
    TextView mTvDate;


    @Bind(R.id.fhd_listview)
    ListView mListView;

    private IndentCusAdapter mAdapter;

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {

    }

    public static HistoryDetailFragment getInstance() {
        HistoryDetailFragment fragment = new HistoryDetailFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected void initData() {
        String monst = getArguments().getString("monst");
        String day = getArguments().getString("day");

        mTvDate.setText(monst + "-" + day);
        mHud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mHud.show();
        LoginBean.UserBean userBean = App.newInstance().getUserBean();
        if (userBean == null) {
            showToast("用户标示意外丢失,请重试~");
            return;
        }
        WooSportApi.getInstance().startGetSportDayDetail(mContext, String.valueOf(userBean.getId()),
                monst, day, new ApiCallback<CacheResult<SportDayInfoBean>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        ViseLog.e(e);
//                        showToast("网络错误,请重试~");
//                        new CookieBar.Builder(_mActivity)
//                                .setIcon(R.drawable.warning_blue)
//                                .setBackgroundColor(R.color.white)
//                                .setMessageColor(R.color.grayfont)
//                                .setMessage("网络错误,请重试")
//                                .show();
                        showTip(R.drawable.warning_red,"网络错误,请重试");
                        mHud.dismiss();
                    }

                    @Override
                    public void onCompleted() {
                        mHud.dismiss();
                    }

                    @Override
                    public void onNext(CacheResult<SportDayInfoBean> sportDayInfoBeanCacheResult) {
                        mHud.dismiss();
                        if (sportDayInfoBeanCacheResult==null)
                            return;
                        mAdapter = new IndentCusAdapter(sportDayInfoBeanCacheResult.getCacheData().getData());
                        mListView.setAdapter(mAdapter);
                    }
                });
    }

    @Override
    protected void bindEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_historydetail;
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }


    private class IndentCusAdapter extends BaseAdapter {

        List<SportDayInfoBean.DataBean> mData;

        public IndentCusAdapter(List<SportDayInfoBean.DataBean> data){
            mData = data;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            Cache cache = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.item_his, parent,false);
                cache = new Cache();
                cache.tv_id = (TextView)convertView.findViewById(R.id.ih_id);
                cache.tv_date = (TextView) convertView
                        .findViewById(R.id.ih_tv_time);
                cache.tv_itme_calorie = (TextView) convertView
                        .findViewById(R.id.ih_tv_caloria);
                cache.tv_itme_distance = (TextView) convertView
                        .findViewById(R.id.ih_tv_distance);
                convertView.setTag(cache);
            } else {
                cache = (Cache) convertView.getTag();
            }
            cache.tv_id.setText(String.valueOf(position+1) + "次");
            cache.tv_date.setText(
                    CommonUtils.secondToHMS(mData.get(position).getTime()) + "时长");
            cache.tv_itme_calorie.setText(
                    + mData.get(position).getCalorie() + "千卡");
            cache.tv_itme_distance.setText(String.format("%.2f",mData.get(position).getDistance() * 0.001) + "km");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),
                            HistoryMapActivity.class);
                    intent.putExtra("mid", mData.get(position).getId());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    class Cache {
        TextView  tv_id,tv_date, tv_itme_calorie, tv_itme_distance;
    }
}

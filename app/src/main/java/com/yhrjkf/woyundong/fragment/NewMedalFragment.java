package com.yhrjkf.woyundong.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.CacheResult;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.MedalBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import java.util.List;

import butterknife.Bind;

public class NewMedalFragment extends BaseFragment {

	@Bind(R.id.am_img_medal_goodtwo)
	ImageView mMedalGoodTwo;

	@Bind(R.id.am_img_medal_qi)
	ImageView mMedalQi;

	@Bind(R.id.am_img_medal_jianchi)
	ImageView mMedaljianchi;

	@Bind(R.id.am_img_medal_xiguan)
	ImageView mMedalxiguan;

	@Bind(R.id.am_img_medal_aishang)
	ImageView mMedalaishagn;

	@Bind(R.id.am_img_medal_60)
	ImageView mMedal60;

	@Bind(R.id.am_img_medal_90)
	ImageView mMedal90;

	@Bind(R.id.am_img_medal_120)
	ImageView mMedal120;

	@Bind(R.id.am_medal_ds_1)
	ImageView mMedal_ds_1;

	@Bind(R.id.am_medal_ds_10)
	ImageView mMedal_ds_10;

	@Bind(R.id.am_medal_ds_50)
	ImageView mMedal_ds_50;

	@Bind(R.id.am_medal_ds_100)
	ImageView mMedal_ds_100;

	@Bind(R.id.am_medal_ds_500)
	ImageView mMedal_ds_500;

	@Bind(R.id.am_medal_ds_800)
	ImageView mMedal_ds_800;

	@Bind(R.id.am_tv_medal_goodtwo)
	TextView mTvGoodTwo;

	@Bind(R.id.am_tv_medal_qi)
	TextView mTvQi;

	@Bind(R.id.am_tv_medal_jianchi)
	TextView mTvjianchi;

	@Bind(R.id.am_tv_medal_xiguan)
	TextView mTvxiguan;

	@Bind(R.id.am_tv_medal_aishang)
	TextView mTvaishang;

	@Bind(R.id.am_tv_medal_60)
	TextView mTv60;

	@Bind(R.id.am_tv_medal_90)
	TextView mTv90;

	@Bind(R.id.am_tv_medal_120)
	TextView mTv120;

	@Bind(R.id.am_img_back)
	ImageView mImgBack;

	@Bind(R.id.am_tv_rule)
	TextView mTvRule;

	// a b c d e f g h i j k l m n

	public static NewMedalFragment getInstance() {
		NewMedalFragment fragment = new NewMedalFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.activity_medal;
	}

	@Override
	protected void initView(View contentView, Bundle savedInstanceState) {

	}
	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}

	private void setUi(MedalBean medalBean){
		List<MedalBean.DataBean> list = medalBean.getData();
		for (int i=0; i<list.size(); i++){
			MedalBean.DataBean dataBean = list.get(i);

			switch ( dataBean.getMedal() ){
				case "A" :
					mMedalGoodTwo.setBackgroundResource(R.drawable.medal_light);
					mTvGoodTwo.setBackgroundResource(R.drawable.medal_light_style);
					mTvGoodTwo.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "B" :
					mMedalQi.setBackgroundResource(R.drawable.medal_light);
					mTvQi.setBackgroundResource(R.drawable.medal_light_style);
					mTvQi.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "C" :
					mMedaljianchi.setBackgroundResource(R.drawable.medal_light);
					mTvjianchi.setBackgroundResource(R.drawable.medal_light_style);
					mTvjianchi.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "D" :
					mMedalxiguan.setBackgroundResource(R.drawable.medal_light);
					mTvxiguan.setBackgroundResource(R.drawable.medal_light_style);
					mTvxiguan.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "E" :
					mMedalaishagn.setBackgroundResource(R.drawable.medal_light);
					mTvaishang.setBackgroundResource(R.drawable.medal_light_style);
					mTvaishang.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "F" :
					mMedal60.setBackgroundResource(R.drawable.medal_light);
					mTv60.setBackgroundResource(R.drawable.medal_light_style);
					mTv60.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "G" :
					mMedal90.setBackgroundResource(R.drawable.medal_light);
					mTv90.setBackgroundResource(R.drawable.medal_light_style);
					mTv90.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "H" :
					mMedal120.setBackgroundResource(R.drawable.medal_light);
					mTv120.setBackgroundResource(R.drawable.medal_light_style);
					mTv120.setTextColor(getResources().getColor(R.color.bluefont));
					break;
				case "I" :
					mMedal_ds_1.setBackgroundResource(R.drawable.medal_dis_light);
					break;
				case "J" :
					mMedal_ds_10.setBackgroundResource(R.drawable.medal_dis_light);
					break;
				case "K" :
					mMedal_ds_50.setBackgroundResource(R.drawable.medal_dis_light);
					break;
				case "L" :
					mMedal_ds_100.setBackgroundResource(R.drawable.medal_dis_light);
					break;
				case "M" :
					mMedal_ds_500.setBackgroundResource(R.drawable.medal_dis_light);
					break;
				case "N" :
					mMedal_ds_800.setBackgroundResource(R.drawable.medal_dis_light);
					break;
			}

		}
	}

	@Override
	protected void initData() {
		mHud = KProgressHUD.create(getActivity())
				.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
				.setLabel("加载中")
				.setCancellable(true);
		mHud.show();
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		WooSportApi.getInstance().startGetMedal(mContext, userBean.getId(), new ApiCallback<CacheResult<MedalBean>>() {
			@Override
			public void onStart() {

			}

			@Override
			public void onError(ApiException e) {
				ViseLog.e(e);
//				showToast("获取数据失败~请重试!");
//				new CookieBar.Builder(_mActivity)
//						.setIcon(R.drawable.warning_red)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("获取数据失败~请重试")
//						.show();
				showTip(R.drawable.warning_red,"获取数据失败,请重试");
				mHud.dismiss();
			}

			@Override
			public void onCompleted() {
			}

			@Override
			public void onNext(CacheResult<MedalBean> medalBeanCacheResult) {
				if (medalBeanCacheResult!=null){
					setUi(medalBeanCacheResult.getCacheData());
				}
				mHud.dismiss();
			}
		});
	}


	@Override
	protected void bindEvent() {
		mTvRule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				BusFactory.getBus().post(new JumpFragmentEvent(RuleFragment.getInstance()));
				start(RuleFragment.getInstance());
			}
		});

		mImgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop();
			}
		});
	}
}

package com.yhrjkf.woyundong.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.log.ViseLog;
import com.vise.xsnow.loader.ILoader;
import com.vise.xsnow.loader.LoaderFactory;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.CacheResult;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.adapter.RankAdapter;
import com.yhrjkf.woyundong.bean.MyRankBean;
import com.yhrjkf.woyundong.bean.RankBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CircleImageView;
import com.yhrjkf.woyundong.view.CookieBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

public class NewRankingFragment extends BaseFragment {

	@Bind(R.id.fr_img_back)
	ImageView mBtnBack;

	@Bind(R.id.fr_btn_all)
	Button mBtnSelectAll;

	@Bind(R.id.fr_btn_run)
	Button mBtnSelectRun;

	@Bind(R.id.fr_btn_ride)
	Button mBtnSelectRide;

	@Bind(R.id.fr_btn_day)
	TextView mBtnSelectDay;

	@Bind(R.id.fr_btn_month)
	TextView mBtnSelectMonth;

	@Bind(R.id.fr_btn_week)
	TextView mBtnSelectWeek;

	@Bind(R.id.fr_btn_alltime)
	TextView mBtnSelectAllTime;

	@Bind(R.id.fr_line_alltime)
	View mLineAllTime;

	@Bind(R.id.fr_line_day)
	View mLineDay;

	@Bind(R.id.fr_line_week)
	View mLineWeek;

	@Bind(R.id.fr_line_month)
	View mLineMonth;

	@Bind(R.id.fr_recycleview_rank)
	RecyclerView mRecycleView;

	@Bind(R.id.fr_tv_myrank)
	TextView mTvMyRank;


	@Bind(R.id.fr_tv_myname)
	TextView mTvMyName;

	@Bind(R.id.fr_img_myimg)
	ImageView mImgUserImg;

	@Bind(R.id.fr_img_rank)
	ImageView mImgUserRank;

	@Bind(R.id.fr_tv_mydistance)
	TextView mTvMyDistance;

	private final static int CLASSIFY_ALL = 0x3;
	private final static int CLASSIFY_RUN = 0x1;
	private final static int CLASSIFY_RIDE = 0x2;

	private final static int TIME_DAY = 0x1;
	private final static int TIME_WEEK = 0x2;
	private final static int TIME_MONTH = 0x3;
	private final static int TIME_ALLTIME = 0x0;

	private int mClassifySelect = 3; //1-健走 2-骑行 3-全部
	private int mTimeSelect = 1; //1-天 2-周 3-月 0-总

	public static NewRankingFragment getInstance() {
		NewRankingFragment fragment = new NewRankingFragment();
		Bundle bundle = new Bundle();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initData() {
		mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
		getRankListData();

		initClassifyBtnStyle();
		initTimeBtnStyle();
	}

	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}


	@Override
	protected void initView(View contentView, Bundle savedInstanceState) {

	}

	@Override
	protected void bindEvent() {

		mBtnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop();
			}
		});

		mBtnSelectAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mClassifySelect = CLASSIFY_ALL;
				initClassifyBtnStyle();
				getRankListData();
			}
		});

		mBtnSelectRun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mClassifySelect = CLASSIFY_RUN;
				initClassifyBtnStyle();
				getRankListData();
			}
		});
		mBtnSelectRide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mClassifySelect = CLASSIFY_RIDE;
				initClassifyBtnStyle();
				getRankListData();
			}
		});

		mBtnSelectDay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimeSelect = TIME_DAY;
				initTimeBtnStyle();
				getRankListData();
			}
		});
		mBtnSelectWeek.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimeSelect = TIME_WEEK;
				initTimeBtnStyle();
				getRankListData();
			}
		});

		mBtnSelectMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimeSelect = TIME_MONTH;
				initTimeBtnStyle();
				getRankListData();
			}
		});

		mBtnSelectAllTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimeSelect = TIME_ALLTIME;
				initTimeBtnStyle();
				getRankListData();
			}
		});
	}


	private RankAdapter mAdapter;

	private void initClassifyBtnStyle(){
		mBtnSelectAll.setBackgroundResource(R.color.transparency);
		mBtnSelectRun.setBackgroundResource(R.color.transparency);
		mBtnSelectRide.setBackgroundResource(R.color.transparency);
		mBtnSelectAll.setTextColor(getResources().getColor(R.color.white));
		mBtnSelectRun.setTextColor(getResources().getColor(R.color.white));
		mBtnSelectRide.setTextColor(getResources().getColor(R.color.white));

		switch (mClassifySelect){
			case CLASSIFY_ALL:
				mBtnSelectAll.setBackgroundResource(R.drawable.rank_btn_style);
				mBtnSelectAll.setTextColor(getResources().getColor(R.color.bluefont));
				break;
			case CLASSIFY_RUN:
				mBtnSelectRun.setBackgroundResource(R.drawable.rank_btn_style);
				mBtnSelectRun.setTextColor(getResources().getColor(R.color.bluefont));
				break;
			case CLASSIFY_RIDE:
				mBtnSelectRide.setBackgroundResource(R.drawable.rank_btn_style);
				mBtnSelectRide.setTextColor(getResources().getColor(R.color.bluefont));
				break;
		}
	}

	private void initTimeBtnStyle(){
		mBtnSelectDay.setTextColor(getResources().getColor(R.color.grayfont));
		mBtnSelectWeek.setTextColor(getResources().getColor(R.color.grayfont));
		mBtnSelectMonth.setTextColor(getResources().getColor(R.color.grayfont));
		mBtnSelectAllTime.setTextColor(getResources().getColor(R.color.grayfont));
		mLineDay.setBackgroundResource(R.color.white);
		mLineWeek.setBackgroundResource(R.color.white);
		mLineMonth.setBackgroundResource(R.color.white);
		mLineAllTime.setBackgroundResource(R.color.white);

		switch (mTimeSelect){
			case TIME_DAY:
				mBtnSelectDay.setTextColor(getResources().getColor(R.color.bluefont));
				mLineDay.setBackgroundResource(R.color.bluefont);
				break;
			case TIME_WEEK:
				mBtnSelectWeek.setTextColor(getResources().getColor(R.color.bluefont));
				mLineWeek.setBackgroundResource(R.color.bluefont);
				break;
			case TIME_MONTH:
				mBtnSelectMonth.setTextColor(getResources().getColor(R.color.bluefont));
				mLineMonth.setBackgroundResource(R.color.bluefont);
				break;
			case TIME_ALLTIME:
				mBtnSelectAllTime.setTextColor(getResources().getColor(R.color.bluefont));
				mLineAllTime.setBackgroundResource(R.color.bluefont);
				break;
		}
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.fragment_ranking_new;
	}

	private void getRankListData() {
		// TODO Auto-generated method stub

		mHud = KProgressHUD.create(getContext())
				.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
				.setCancellable(true);
		mHud.show();
		WooSportApi.getInstance().startGetRankInfo(mContext, String.valueOf(mClassifySelect),
				String.valueOf(mTimeSelect), new ApiCallback<CacheResult<RankBean>>() {
			@Override
			public void onStart() {

			}

			@Override
			public void onError(ApiException e) {
				ViseLog.e(e);
//				showToast("获取数据失败,请重试~");
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
				mHud.dismiss();
			}

			@Override
			public void onNext(final CacheResult<RankBean> rankBeanCacheResult) {
				if (rankBeanCacheResult!=null){
					if (rankBeanCacheResult.getCacheData().getData() == null ||
							rankBeanCacheResult.getCacheData().getData().size() < 1){
//						showToast("暂无数据~");
						showTip(R.drawable.warning_blue,"暂无数据");
					}
					mAdapter = new RankAdapter(rankBeanCacheResult.getCacheData().getData());
					mRecycleView.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
				mHud.dismiss();
			}
		});

		WooSportApi.getInstance().startGetMyRank(mContext, new ApiCallback<MyRankBean>() {
			@Override
			public void onStart() {

			}

			@Override
			public void onError(ApiException e) {
				ViseLog.e(e);
//				showToast("获取数据失败,请重试~");
//				new CookieBar.Builder(_mActivity)
//						.setIcon(R.drawable.warning_red)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("获取数据失败~请重试")
//						.show();
				showTip(R.drawable.warning_red,"获取数据失败,请重试");
			}

			@Override
			public void onCompleted() {

			}

			@Override
			public void onNext(MyRankBean myRankBean) {

				int rank = myRankBean.getNub();
				if (rank == 1){
					mImgUserRank.setImageResource(R.drawable.theone);
				}else if (rank == 2){
					mImgUserRank.setImageResource(R.drawable.thetwo);
				}else if (rank == 3){
					mImgUserRank.setImageResource(R.drawable.thethree);
				}else{
					mImgUserRank.setVisibility(View.INVISIBLE);
				}

				Glide.with(mContext).load(myRankBean.getHeadImg()).asBitmap().centerCrop()
						.placeholder(R.drawable.df).thumbnail(0.1f).into(new BitmapImageViewTarget(mImgUserImg) {
					@Override
					protected void setResource(Bitmap resource) {
						RoundedBitmapDrawable circularBitmapDrawable =
								RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
						circularBitmapDrawable.setCircular(true);
						mImgUserImg.setImageDrawable(circularBitmapDrawable);
					}
				});

				String name = myRankBean.getName();
				if (!TextUtils.isEmpty(name)){
					if ((isNumeric(name) && getWordCount(name) > 11)){
						name = name.substring(0,name.length() > 11 ? 11 : name.length()) + "...";
					}else if (getWordCount(name) > 16){
						name = name.substring(0,name.length() > 8 ? 8 : name.length()) + "...";
					}

					mTvMyName.setText(name);
//					name = name.substring(0,11) + "...";
				}

				mTvMyRank.setText(String.valueOf(rank));
				mTvMyDistance.setText(String.valueOf(myRankBean.getKm()));
			}
		});
	}
	///-=--------------
	public  int getWordCount(String s)
	{
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}
	public boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
	///-=--------------
}


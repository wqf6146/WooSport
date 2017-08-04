package com.yhrjkf.woyundong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.CacheResult;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.RecordBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.http.api.ApiSercice;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.utils.MathHelper;
import com.yhrjkf.woyundong.view.CookieBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;

public class NewRecordActivity extends BaseActivity {

	@Bind(R.id.ar_img_back)
	ImageView mImgBack;

	@Bind(R.id.am_tv_time_time)
	TextView mTvTimeTime;

	@Bind(R.id.am_tv_time_date)
	TextView mTvTimeDate;

	@Bind(R.id.am_tv_ds_date)
	TextView mTvDsDate;

	@Bind(R.id.am_tv_ds_ds)
	TextView mTvDsDs;

	@Bind(R.id.am_tv_fire_calorie)
	TextView mTvFireCalorie;

	@Bind(R.id.am_tv_fire_date)
	TextView mTvFireDate;

	@Bind(R.id.am_tv_speed_date)
	TextView mTvSpeedDate;

	@Bind(R.id.am_tv_speed_ds)
	TextView mTvSpeedDs;

	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {
		mHud = KProgressHUD.create(NewRecordActivity.this)
				.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
				.setLabel("加载中")
				.setCancellable(true);
		mHud.show();
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		WooSportApi.getInstance().startGetRecord(mContext, userBean.getId(),
				new ApiCallback<CacheResult<RecordBean>>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(ApiException e) {
						ViseLog.e(e);
//						showToast("获取数据失败~请重试!");
//						new CookieBar.Builder(NewRecordActivity.this)
//								.setIcon(R.drawable.warning_red)
//								.setBackgroundColor(R.color.white)
//								.setMessageColor(R.color.grayfont)
//								.setMessage("网络错误,请重试")
//								.show();
						showTip(R.drawable.warning_red,"网络错误,请重试");
						mHud.dismiss();
					}

					@Override
					public void onNext(CacheResult<RecordBean> recordBeanCacheResult) {
						if (recordBeanCacheResult!=null){
							if (recordBeanCacheResult.getCacheData().getRecode().equals("200"))
								setUi(recordBeanCacheResult.getCacheData());
							else{
//								showToast(recordBeanCacheResult.getCacheData().getMsg());
								showTip(R.drawable.warning_red,recordBeanCacheResult.getCacheData().getMsg());

							}
						}
						mHud.dismiss();
					}

					@Override
					public void onStart() {
					}
				});
	}

	private void setUi(RecordBean cacheData) {
		RecordBean.DataBean dataBean = cacheData.getData();
		RecordBean.DataBean.CalorieBean calorieBean = dataBean.getCalorie();
		RecordBean.DataBean.DistanceBean distanceBean = dataBean.getDistance();
		RecordBean.DataBean.SpeedBean speedBean = dataBean.getSpeed();
		RecordBean.DataBean.TimeBean timeBean = dataBean.getTime();

		mTvFireCalorie.setText(String.valueOf(calorieBean.getCalorie()));
		mTvFireDate.setText(calorieBean.getCreated_at());

		mTvDsDate.setText(distanceBean.getCreated_at());
		mTvDsDs.setText(String.valueOf(MathHelper.div(distanceBean.getDistance(),1000,2)));

		mTvSpeedDate.setText(speedBean.getCreated_at());
		mTvSpeedDs.setText(String.valueOf(speedBean.getSpeed()));

		mTvTimeDate.setText(timeBean.getCreated_at());
		mTvTimeTime.setText(String.valueOf(MathHelper.div(timeBean.getTime(),60,2)));
	}

	@Override
	protected void bindEvent() {

		mImgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

	}
}

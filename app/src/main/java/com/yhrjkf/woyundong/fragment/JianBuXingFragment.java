package com.yhrjkf.woyundong.fragment;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;

import com.vise.log.ViseLog;
import com.vise.xsnow.event.BusFactory;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yanzhenjie.permission.AndPermission;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;

import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.CSportPointBean;
import com.yhrjkf.woyundong.bean.NoticeBean;
import com.yhrjkf.woyundong.bean.WeatherBean;
import com.yhrjkf.woyundong.bean.db.CSportInfoBean;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.SignBean;
import com.yhrjkf.woyundong.bean.SportPointBean;
import com.yhrjkf.woyundong.bean.SportResBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.control.MapControl;
import com.yhrjkf.woyundong.event.JumpFragmentEvent;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.interfaces.SportProcessListening;
import com.yhrjkf.woyundong.tools.DataUtils;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.utils.IntentWrapper;
import com.yhrjkf.woyundong.utils.MathHelper;
import com.yhrjkf.woyundong.utils.VoiceUtils;
import com.yhrjkf.woyundong.view.LocOverlay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.View.GONE;

public class JianBuXingFragment extends BaseFragment implements OnClickListener,LocationSource,
		SportProcessListening {

    @Bind(R.id.map)
	MapView mMapView;
	private AMap aMap;

	@Bind(R.id.img_jbxing_start)
	ImageView img_jbxing_start;

	private Boolean startOrPause = true;

	private int mRunType = WALK;

	int calorie_uint;

	//是否在运动
	Boolean bSportBoolean = false;

	public static final int RUN = 0x1;
	public static final int PAUSE = 0x2;
	public static final int ZYGOTE = 0x3;
	private int mStatus = ZYGOTE;   // RUN PAUSE ZYGOTE

	//记录上一个点
	private LatLng mOldLatLng = null;

	@Bind(R.id.fjbx_cityname)
	TextView mTvCityName;

	@Bind(R.id.fjbx_wendu)
	TextView mTvWenDu;

	@Bind(R.id.fjbx_pm25)
	TextView mTvPm25;

	@Bind(R.id.fjbx_weatherqa)
	TextView mTvWeatherQa;

	@Bind(R.id.fjbx_distance)
	TextView mTvDistance;

	@Bind(R.id.fjbx_tv_speed)
	TextView mTvSpeed;

	@Bind(R.id.fjbx_tv_sporttime)
	TextView mTvSportTime;

	@Bind(R.id.fjbx_tv_calorie)
	TextView mTvCalorie;

	@Bind(R.id.img_state_run)
	ImageView mImgStateRun;

	@Bind(R.id.img_state_ride)
	ImageView mImgStateRide;

	@Bind(R.id.img_rank)
	ImageView mImgRank;

	@Bind(R.id.img_signin)
	ImageView mImgSignin;

	@Bind(R.id.img_jbxing_goon)
	ImageView mBtnReStartRun;

	@Bind(R.id.img_jbxing_finish)
	ImageView mBtnFinishRun;

	@Bind(R.id.fhbx_btn_location)
	ImageView mBtnLocatioin;

	@Bind(R.id.fj_img_gps)
	ImageView mImgGps;

	@Bind(R.id.fjbx_ll_location)
	LinearLayout mLLLocation;

    @Bind(R.id.fjbx_line_ride)
    View mLineRide;

    @Bind(R.id.fjbx_line_run)
    View mLineRun;

	@Bind(R.id.fjbx_img_weather)
	ImageView mImgWeather;

	private MapControl mMapControl;

	private static final int WALK = 0x1;
	private static final int RIDING = 0x2;

	public static JianBuXingFragment getInstance() {
		JianBuXingFragment fragment = new JianBuXingFragment();
		Bundle bundle = new Bundle();
		fragment.setArguments(bundle);
		return fragment;
	}

	private boolean bGetNotice = true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
		// 先判断是否有权限。
		if(AndPermission.hasPermission(_mActivity, Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,
				Manifest.permission.ACCESS_FINE_LOCATION)) {
			// 有权限，直接do anything.
		} else {
			// 申请权限。
			AndPermission.with(this)
					.requestCode(100)
					.permission(Manifest.permission.WRITE_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION,
							Manifest.permission.RECORD_AUDIO,
							Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.WRITE_EXTERNAL_STORAGE)
					.send();
		}

		if (bGetNotice){
			bGetNotice = false;
			WooSportApi.getInstance().startGetNotice(mContext, new ApiCallback<NoticeBean>() {
				@Override
				public void onStart() {
				}

				@Override
				public void onError(ApiException e) {

				}

				@Override
				public void onCompleted() {

				}

				@Override
				public void onNext(NoticeBean noticeBean) {
					if (noticeBean.getRecode().equals("200")){
						new SweetAlertDialog(_mActivity,SweetAlertDialog.NORMAL_TYPE)
								.setConfirmText("确定")
								.setTitleText("通知")
								.setContentText(noticeBean.getData())
								.showCancelButton(false)
								.setConfirmClickListener(null)
								.show();
					}
				}
			});
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	//获取卫星信号
	private LocationManager mLocationManager;
	private int mCurSateNumb=0;
	private int mCurGpsTag;
	private void setGpsSigned(){
		try {
			mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
			mLocationManager.addGpsStatusListener(new GpsStatus.Listener() {
				@Override
				public void onGpsStatusChanged(int event) {
					try {
						GpsStatus status = mLocationManager.getGpsStatus(null); // 取当前状态
						updateGpsStatus(event, status);
					}catch (SecurityException s){
						ViseLog.e(s);
					}
				}
			});
//			if (Build.VERSION.SDK_INT >= 24){
//				mLocationManager.registerGnssStatusCallback(new GnssStatus.Callback() {
//					@Override
//					public void onSatelliteStatusChanged(GnssStatus status) {
//						super.onSatelliteStatusChanged(status);
//					}
//				});
//			}else{
//				mLocationManager.addGpsStatusListener(new GpsStatus.Listener() {
//					@Override
//					public void onGpsStatusChanged(int event) {
//						try {
//							GpsStatus status = mLocationManager.getGpsStatus(null); // 取当前状态
//							updateGpsStatus(event, status);
//						}catch (SecurityException s){
//							ViseLog.e(s);
//						}
//					}
//				});
//			}
		}catch (SecurityException e){
			ViseLog.e(e);
		}

	}

	private void updateGpsStatus(int event, GpsStatus status) {
		if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
			int maxSatellites = status.getMaxSatellites();
			Iterator<GpsSatellite> it = status.getSatellites().iterator();
			int count = 0;
			while (it.hasNext() && count <= maxSatellites) {
				GpsSatellite s = it.next();
				if (s.getSnr() != 0)//只有信躁比不为0的时候才算搜到了星
					count++;
			}
			mCurSateNumb = count;
//			ViseLog.e("count:"+count);
			if(count >= 10 && mCurGpsTag != R.drawable.icon_gps_high) {
				mCurGpsTag = R.drawable.icon_gps_high;
				mImgGps.setImageResource(R.drawable.icon_gps_high);
			}else if (count <10 && count >=7 && mCurGpsTag != R.drawable.icon_gps_medium){
				mCurGpsTag = R.drawable.icon_gps_medium;
				mImgGps.setImageResource(R.drawable.icon_gps_medium);
			}else if (count < 7 && count >=1 && mCurGpsTag != R.drawable.icon_gps_low){
				mCurGpsTag = R.drawable.icon_gps_low;
				mImgGps.setImageResource(R.drawable.icon_gps_low);
			}else if(count <1 && mCurGpsTag != R.drawable.icon_gps_no){
				mCurGpsTag = R.drawable.icon_gps_no;
				mImgGps.setImageResource(R.drawable.icon_gps_no);
			}

		}
	}

	@Override
	protected void bindEvent() {
		img_jbxing_start.setOnClickListener(this);

		mImgStateRide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mRunType = RIDING;
				mImgStateRun.setImageResource(R.drawable.state_run1);
                mLineRun.setVisibility(View.INVISIBLE);
				mImgStateRide.setImageResource(R.drawable.state_ride2);
                mLineRide.setVisibility(View.VISIBLE);
			}
		});
		mImgStateRun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mRunType = WALK;
				mImgStateRun.setImageResource(R.drawable.state_run2);
                mLineRun.setVisibility(View.VISIBLE);
				mImgStateRide.setImageResource(R.drawable.state_ride );
                mLineRide.setVisibility(View.INVISIBLE);
			}
		});

		mImgRank.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BusFactory.getBus().post(new JumpFragmentEvent(NewRankingFragment.getInstance()));
			}

		});

		mImgSignin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mHud = KProgressHUD.create(getContext())
						.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
						.setCancellable(true);
				mHud.show();
				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
					showToast("用户标示意外丢失,请重试~");
					return;
				}
				WooSportApi.getInstance().startPostSign(getContext(),
						String.valueOf(userBean.getId()), new ApiCallback<SignBean>() {
							@Override
							public void onStart() {
							}

							@Override
							public void onError(ApiException e) {
								ViseLog.e(e);
//								showToast("获取数据失败,请重试~");
//								new CookieBar.Builder(_mActivity)
//										.setIcon(R.drawable.warning_blue)
//										.setBackgroundColor(R.color.white)
//										.setMessageColor(R.color.grayfont)
//										.setMessage("网络错误,请重试")
//										.show();
								showTip(R.drawable.warning_red,"网络错误,请重试");
								mHud.dismiss();
							}

							@Override
							public void onCompleted() {
								mHud.dismiss();
							}

							@Override
							public void onNext(SignBean signBean) {
								String code = signBean.getRecode();
								if (code.equals("200")){
//									new CookieBar.Builder(_mActivity)
//											.setIcon(R.drawable.warning_blue)
//											.setBackgroundColor(R.color.white)
//											.setMessageColor(R.color.grayfont)
//											.setLayoutGravity(Gravity.BOTTOM)
//											.setMessage("签到成功")
//											.show();
									showTip(R.drawable.warning_blue,"签到成功");
//									Toast.makeText(getContext(),"签到成功!",Toast.LENGTH_SHORT).show();
								}else if (code.equals("403")){
//									Toast.makeText(getContext(),"已经签到了!",Toast.LENGTH_SHORT).show();
//									new CookieBar.Builder(_mActivity)
//											.setIcon(R.drawable.warning_blue)
//											.setBackgroundColor(R.color.white)
//											.setMessageColor(R.color.grayfont)
//											.setLayoutGravity(Gravity.BOTTOM)
//											.setMessage("已经签到了")
//											.show();
									showTip(R.drawable.warning_blue,"已经签到了");
								}else{
									showTip(R.drawable.warning_blue,"网络错误,请重试");
								}
							}
						});
			}
		});

		//继续按钮
		mBtnReStartRun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mStatus = RUN;
				mCurPolyline = null;
				mBtnReStartRun.setVisibility(GONE);
				mBtnFinishRun.setVisibility(View.GONE);
				img_jbxing_start.setVisibility(View.VISIBLE);
				locationBtnAnimate(false);
				reStartSport();
			}
		});

		//结束按钮
		mBtnFinishRun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					mStatus = ZYGOTE;
					sportDown();
				}catch (Exception e){
					showTip(R.drawable.warning_red,e.toString());
				}
			}
		});

		mBtnLocatioin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (aMap==null || mOldLatLng==null)
					return;
				aMap.moveCamera(CameraUpdateFactory
						.newLatLngZoom(mOldLatLng, 17));
			}
		});
	}

	@Override
	public boolean onBackPressedSupport() {
		if (mStatus!=ZYGOTE){
//			showToast("请先结束跑步再退出!");
//			new CookieBar.Builder(_mActivity)
//					.setIcon(R.drawable.warning_blue)
//					.setBackgroundColor(R.color.white)
//					.setMessageColor(R.color.grayfont)
//					.setMessage("请先结束跑步再退出")
//					.show();
			showTip(R.drawable.warning_blue,"请先结束跑步再退出");
		}
		return mStatus!=ZYGOTE;
	}

	@Override
	protected void initView(View contentView,Bundle savedInstanceState) {
		mMapControl = MapControl.getInstance(this,_mActivity);
		//初始化地图
		initMap(savedInstanceState);
	}

	private void setWeatherUi(){
		WooSportApi.getInstance().startGetWeather(mContext, new ApiCallback<WeatherBean>() {
			@Override
			public void onStart() {

			}

			@Override
			public void onError(ApiException e) {
				ViseLog.e(e);
			}

			@Override
			public void onCompleted() {

			}

			@Override
			public void onNext(WeatherBean bean) {
				if (bean.getFlag().equals("scuss")){
					WeatherBean.MessageBean messageBean = bean.getMessage();
					mTvCityName.setText(messageBean.getCity());
					mTvWeatherQa.setText(messageBean.getQuality());
					mTvPm25.setText("pm2.5:"+messageBean.get_$PM2576());
					mTvWenDu.setText(messageBean.getTemperature());
					Glide.with(_mActivity).load(messageBean.getWeather_pic()).into(mImgWeather);
				}
			}
		});
	}

	@Override
	protected void initData() {
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null){
			showToast("用户标示意外丢失,请重试~");
			return;
		}else{
			int weight = userBean.getWeight();
			if ( weight == 0 ) {
				calorie_uint = 50;
			} else {
				calorie_uint = weight;
			}
		}
		setWeatherUi();
		setGpsSigned();

		IntentWrapper.whiteListMatters(_mActivity, "运动记录服务的持续运行");
	}

	private void recoverSports(LatLng latLng) {
		final CSportInfoBean cSportInfoBean = DbHelper.getInstance().cSportInfoBeanLongDBManager().load(1L);
		if (cSportInfoBean!=null){
            int timex = Integer.parseInt(DataUtils.getStringToDate(DataUtils
                    .getCurrentDate())) - Integer.parseInt(cSportInfoBean.getFinish_time());
			if (timex <= 600 && timex > 0 ){
				//10分钟之内恢复
				new SweetAlertDialog(_mActivity,SweetAlertDialog.NORMAL_TYPE)
						.setTitleText("提示")
						.setContentText("检测到上次运动中程序异常退出\n已自动恢复")
						.setConfirmText("确定")
						.showCancelButton(false)
						.setCancelClickListener(null)
						.setConfirmClickListener(null).show();
				//恢复轨迹
				recoverPoint(cSportInfoBean,latLng);
				//恢复运动信息
				updateSportInfoUi(cSportInfoBean);
				//恢复时间
				handlerTimerUI(Integer.parseInt(cSportInfoBean.getTime()));


			}else{
				new SweetAlertDialog(_mActivity)
						.setTitleText("提示")
						.setContentText("检测到上次数据未上传,是否尝试再次上传?\n本次取消数据将清除!")
						.setCancelText("取消")
						.setConfirmText("上传")
						.showCancelButton(true)
						.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								sDialog.dismiss();
								DbHelper.getInstance().cSportInfoBeanLongDBManager().deleteAll();
							}
						})
						.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sweetAlertDialog) {
								uploadData(cSportInfoBean,sweetAlertDialog);
							}
						}).show();
			}
		}
	}


	public boolean getSportStatue(){
		return bSportBoolean;
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.fragment_jianbuxing;
	}



	private void initMap(Bundle savedInstanceState) {
		mMapView.onCreate(savedInstanceState);
		//获取TencentMap实例
		initMap();
	}


	private void initMap(){
		if (aMap != null)
			return;
		mLocationMarker = null;
		aMap = mMapView.getMap();
		MyLocationStyle myLocationStyle;
		myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
		myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
		aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		aMap.getUiSettings().setZoomControlsEnabled(false);
		aMap.getUiSettings().setAllGesturesEnabled(true);
		// 设置定位监听
		aMap.setLocationSource(this);
		// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		aMap.setMyLocationEnabled(true);
		if (mOldLatLng != null)
			moveCamera(mOldLatLng);
	}

	/***
	 * SportProcessListening
	 */
	@Override
	public void reportVoice(String stringExtra) {
		VoiceUtils.getInstance().reportVoice(stringExtra);
	}


	public void sysPoint(AMapLocation location,LatLng latLng) {
		updateMapInfo(location,latLng);
	}

	@Override
	public void sysSportStatus(CSportInfoBean cSportInfoBean) {
		updateSportInfoUi(cSportInfoBean);
	}

	@Override
	public void sysTime(int value) {
		handlerTimerUI(value);
	}

	@Override
	public void notifySpeedOver() {
		handlerSpeedUnusual(false);
	}

	@Override
	public void notifySpeedOverTip() {
		handlerSpeedOverTip();
	}

	@Override
	public void notifySpeedSlow() {
		handlerSpeedUnusual(true);
	}

	@Override
	public void showString(String string) {
		showTip(R.drawable.warning_red,string);
	}

	@Override
	public int getStatus() {
		return mStatus;
	}

	@Override
	public void moveCamera(LatLng latLng) {
		aMap.moveCamera(CameraUpdateFactory
				.newLatLngZoom(latLng,17));
		if (mLocationMarker == null){
			BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.ic_anchor);
			mLocationMarker = aMap.addMarker(new MarkerOptions().position(latLng).icon(des)
					.anchor(0.5f, 0.5f));
		}else{
			mLocationMarker.setPosition(latLng);
		}
	}

	private boolean bFirstRecover = true;
	@Override
	public void recoverSport(LatLng latLng) {
		if (bFirstRecover) {
			recoverSports(latLng);
			bFirstRecover = false;
		}
	}

	@Override
	public void onDestroy() {
//		mMapView.onDestroy();
		super.onDestroy();
		destoryMapService();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
		initMap();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	private void startRun(){

		if (CommonUtils.isGpsOPen()) {
			if (startOrPause) {
				if (mCurSateNumb < 7){
					new SweetAlertDialog(_mActivity,SweetAlertDialog.WARNING_TYPE)
							.setTitleText("GPS信号较弱")
							.setContentText("继续运动可能导致数据异常，你可以：\n" +
									"* 绕开高大建筑物\n" +
									"* 等待GPS信号增强")
							.setCancelText("取消")
							.setConfirmText("继续运动")
							.showCancelButton(true)
							.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									sweetAlertDialog.dismiss();
									onSportStart(); //开始运动
								}
							}).show();
					return;
				}
				onSportStart(); //开始运动
			} else {
				//暂停运动
				pauseSport();
				locationBtnAnimate(true);
			}
		} else {
			Toast.makeText(_mActivity, "请开启GPS定位",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void locationBtnAnimate(boolean dire) {
		if (dire){
			Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.location_btn_out);
			mLLLocation.setVisibility(View.INVISIBLE);
			mLLLocation.startAnimation(animation);

		}else{
			Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.location_btn_in);
			mLLLocation.setVisibility(View.VISIBLE);
			mLLLocation.startAnimation(animation);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_jbxing_start:
//			throw new NullPointerException();
			//开始跑步
			if (!checkSportInfoFromDb())
				startRun();
			break;
		default:
			break;
		}
	}

	/**
	 * ------------------------------服务相关
	 */
	//继续运动
	private void reStartSport(){
		bSportBoolean = true;
		mMapControl.reStartSport();
	}


	//收到速度异常提示处理
	private void handlerSpeedOverTip() {
		new SweetAlertDialog(_mActivity,SweetAlertDialog.WARNING_TYPE)
				.setConfirmText("确定")
				.setTitleText("你的速度异常请规范运动")
				.showCancelButton(false)
				.setConfirmClickListener(null)
				.show();
	}

	private void handlerTimerUI(final int value) {
		_mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mTvSportTime.setText(CommonUtils.secondToHMS(value));
			}
		});
	}


	private void handlerSpeedUnusual(boolean slowOrOver) {
		if (slowOrOver == true){
			ViseLog.e("收到慢速提醒！");
			pauseSport();
			locationBtnAnimate(true);
		}else {
			ViseLog.e("收到超速速提醒！");
			((MainFragment)getParentFragment()).tabBarAnimateIn();
//			showToast("你的速度异常请规范运动!");
//			new CookieBar.Builder(_mActivity)
//					.setIcon(R.drawable.warning_red)
//					.setBackgroundColor(R.color.white)
//					.setMessageColor(R.color.grayfont)
//					.setMessage("你的速度异常请规范运动")
//					.show();
			showTip(R.drawable.warning_red,"你的速度异常请规范运动");
			reinitUi();
		}
	}

	//开始运动
	private void onStartSport(){
		bSportBoolean = true;
		mMapControl.startSport();
	}

	//暂停运动
	private void onPauseSport(){
		bSportBoolean = false;
		mMapControl.pauseSport();
	}

	//结束运动
	private void onSportDown(){
		bSportBoolean = false;
		mMapControl.sportDown();
	}

	//停止Map服务
	private void pauseMapService(){
		mMapControl.pauseLocatioin();
	}

	//注销Map服务
	private void destoryMapService(){
		mMapControl.onDestroy();
	}

	private void reinitUi(){

		//运动速度
		mTvSpeed.setText("00");
		//运动距离
		mTvDistance.setText("0.00");
		//卡路里
		mTvCalorie.setText("0.0");
		//运动时间
		mTvSportTime.setText("0:00:00");

		mImgStateRide.setClickable(true);
		mImgStateRun.setClickable(true);

		startOrPause = true; //控制开始结束按钮
		bSportBoolean = false; //是否运动状态
		mStatus = ZYGOTE;

		img_jbxing_start.setVisibility(View.VISIBLE);
		mBtnReStartRun.setVisibility(View.INVISIBLE);
		mBtnFinishRun.setVisibility(View.INVISIBLE);
		img_jbxing_start.setImageResource(R.drawable.start_run);
		//清除轨迹
		removePolyLine();
	}

	/***
	 * --------------------开始运动
	 */
	private void onSportStart() {

		((MainFragment)getParentFragment()).tabBarAnimateOut();
		aMap.getUiSettings().setZoomControlsEnabled(true);
		mMapControl.setSportType(mRunType,calorie_uint);


		reportVoice("运动开始!");

		//先清除状态
		reinitUi();

		img_jbxing_start.setImageResource(R.drawable.pause_run);

		mImgStateRide.setClickable(false);
		mImgStateRun.setClickable(false);

		bSportBoolean = true;
		mStatus = RUN;
		startOrPause = false;
	}
	//暂停
	private void pauseSport(){
		bSportBoolean = false;
		mCurPolyline = null;
		mStatus = PAUSE;
		onPauseSport();
		img_jbxing_start.setVisibility(View.INVISIBLE);
		mBtnReStartRun.setVisibility(View.VISIBLE);
		mBtnFinishRun.setVisibility(View.VISIBLE);
	}

	//处理点
	private void prepareData(List<SportPointBean> pointData,CSportInfoBean sportBean){

		StringBuilder path = new StringBuilder();
		for (int i=0; i<pointData.size(); i++){
			SportPointBean sportPointBean = pointData.get(i);
			path.append(getPathStr((String) sportPointBean.getType(),sportPointBean.getPoints()));
			if(i == pointData.size()-1)
				break;
			path.append(',');
		}
		sportBean.setPath(path.toString());
	}

	//结束运动
	private void sportDown() {
		pauseSport();
		new SweetAlertDialog(_mActivity)
				.setTitleText("是否结束本次跑步？")
				.setContentText("结束后自动上传数据")
				.setCancelText("继续")
				.setConfirmText("结束")
				.showCancelButton(true)
				.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(SweetAlertDialog sDialog) {
						sDialog.dismiss();
						locationBtnAnimate(false);
						mBtnFinishRun.setVisibility(View.INVISIBLE);
						mBtnReStartRun.setVisibility(View.INVISIBLE);
						img_jbxing_start.setVisibility(View.VISIBLE);
						reStartSport();
					}
				})
				.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(final SweetAlertDialog sDialog) {
						//准备上传跑步数据
						try{
							final List<SportPointBean> pointData = mMapControl.synListData();
							final CSportInfoBean sportBean = mMapControl.sportFinish();
							if (pointData != null && sportBean != null && sportBean.getDistance() > 30){
								//开始上传
								sDialog.setTitleText("上传中")
										.setContentText("")
										.showCancelButton(false)
										.setCancelClickListener(null)
										.setConfirmClickListener(null)
										.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
								sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.bluefont));
								prepareData(pointData,sportBean);
								uploadData(sportBean,sDialog);
							}else {
								//数据太少
								sDialog.dismiss();
								showTip(R.drawable.warning_blue,"跑步距离太短啦,无法保存数据");
							}

							//清场处理
							((MainFragment)getParentFragment()).tabBarAnimateIn();
							aMap.getUiSettings().setZoomControlsEnabled(false);
							onSportDown();
							reinitUi(); //初始化ui
							locationBtnAnimate(false);
							reportVoice("运动结束休息一下吧");
							mImgStateRide.setClickable(true);
							mImgStateRun.setClickable(true);
						}catch (Exception e){
							ViseLog.e(e);
							handleUploadError(sDialog);
						}

					}
				})
				.show();
	}

	//上传错误处理
	private void handleUploadError(final SweetAlertDialog sweetAlertDialog){
		final List<SportPointBean> pointData = mMapControl.synListData();
		final CSportInfoBean sportBean = mMapControl.sportFinish();
		prepareData(pointData,sportBean);
		if (sportBean!=null && sportBean.getDistance() > 30){
			sportInfoToDb(sportBean);
			if (sweetAlertDialog!=null){
				sweetAlertDialog.setTitleText("上传失败!")
						.setContentText("上传中发生错误,请尝试点击开始按钮重新上传")
						.setConfirmText("确定")
						.showCancelButton(false)
						.setCancelClickListener(null)
						.setConfirmClickListener(null)
						.changeAlertType(SweetAlertDialog.ERROR_TYPE);
			}else{
				new SweetAlertDialog(_mActivity,SweetAlertDialog.ERROR_TYPE)
						.setTitleText("上传失败")
						.setContentText("上传中发生错误,请尝试点击开始按钮重新上传")
						.setConfirmText("确定")
						.showCancelButton(false)
						.setCancelClickListener(null)
						.setConfirmClickListener(null)
						.show();

			}
		}else{
			if (sweetAlertDialog!=null) {
				sweetAlertDialog.dismiss();
			}
			showTip(R.drawable.warning_blue,"跑步距离太短啦,无法保存数据");
		}

		//清场处理
		((MainFragment)getParentFragment()).tabBarAnimateIn();
		aMap.getUiSettings().setZoomControlsEnabled(false);
		onSportDown();
		reinitUi(); //初始化ui
		locationBtnAnimate(false);
		mImgStateRide.setClickable(true);
		mImgStateRun.setClickable(true);
	}

	private String getPathStr(String type,ArrayList<LatLng> list){
		String str1 = "{ \"type\":" + "\""+type+ "\"" + "," + "\"path\":";
		StringBuilder str2 = new StringBuilder();
		str2.append("\"");
		for (int i=0; i< list.size(); ++i){
			str2.append(String.valueOf(list.get(i).latitude)).append(",")
					.append(String.valueOf(list.get(i).longitude));
			if (i != list.size()-1)
				str2.append('$');
		}
		str2.append("\"");
		return str1 + str2 + "}";
	}

	//上传数据！
	private void uploadData(final CSportInfoBean sportBean,final SweetAlertDialog sDialog){
		final LinkedHashMap hashMap = new LinkedHashMap();
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		hashMap.put("uid",userBean.getId()+"");
		hashMap.put("types",sportBean.getTypes()+"");
		hashMap.put("distance",sportBean.getDistance()+"");
		hashMap.put("calorie",sportBean.getCalorie()+"");
		hashMap.put("start_time",sportBean.getStart_time()+"");
		hashMap.put("finish_time",sportBean.getFinish_time()+"");
		hashMap.put("time",sportBean.getTime()+"");
		hashMap.put("path",sportBean.getPath());
		WooSportApi.getInstance().startPostSportInfo(mContext, hashMap, new ApiCallback<SportResBean>() {
			@Override
			public void onStart() {
			}
			@Override
			public void onError(ApiException e) {
				ViseLog.e(e);
				sportInfoToDb(sportBean);
				sDialog.setTitleText("上传失败!")
						.setContentText("网络错误")
						.setConfirmText("确定")
						.showCancelButton(false)
						.setCancelClickListener(null)
						.setConfirmClickListener(null)
						.changeAlertType(SweetAlertDialog.ERROR_TYPE);
			}
			@Override
			public void onCompleted() {}
			@Override
			public void onNext(SportResBean o) {
				if (o.getRecode().equals("200")){
//					showToast("上传跑步数据成功！");
					showTip(R.drawable.warning_blue,"上传跑步数据成功");
					removeSportInfoFromDb();
					WooSportApi.getInstance().startUpdateUser(mContext, new ApiCallback<LoginBean>() {
						@Override
						public void onStart() {

						}

						@Override
						public void onError(ApiException e) {
							ViseLog.e(e);
						}

						@Override
						public void onCompleted() {

						}

						@Override
						public void onNext(LoginBean loginBean) {
							App.newInstance().saveUserBean(loginBean);
						}
					});
					sDialog.setTitleText("上传成功!")
							.setConfirmText("确定")
							.showCancelButton(false)
							.setCancelClickListener(null)
							.setConfirmClickListener(null)
							.setContentText("")
							.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
				}else{
					sportInfoToDb(sportBean);
					sDialog.setTitleText("上传失败!")
							.setContentText(o.getMsg())
							.setConfirmText("确定")
							.showCancelButton(false)
							.setCancelClickListener(null)
							.setConfirmClickListener(null)
							.changeAlertType(SweetAlertDialog.ERROR_TYPE);
				}

			}
		});
	}

	//查看是否预留数据
	private boolean checkSportInfoFromDb(){
		if (mStatus != ZYGOTE)
			return false;
		final List<CSportInfoBean> list = DbHelper.getInstance().cSportInfoBeanLongDBManager().loadAll();
		if (list!=null && list.size()>0){
			new SweetAlertDialog(_mActivity)
					.setTitleText("提示")
					.setContentText("检测到之前数据上传失败,是否尝试再次上传?本次取消数据将清除!")
					.setCancelText("取消")
					.setConfirmText("上传")
					.showCancelButton(true)
					.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							sDialog.dismiss();
							DbHelper.getInstance().cSportInfoBeanLongDBManager().deleteAll();
						}
					})
					.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sweetAlertDialog) {
							uploadData(list.get(0),sweetAlertDialog);
						}
					}).show();
			return true;
		}
		return false;
	}

	private void sportInfoToDb(CSportInfoBean sportBean){
		DbHelper.getInstance().cSportInfoBeanLongDBManager().deleteAll();
		DbHelper.getInstance().cSportInfoBeanLongDBManager().insert(sportBean);
	}

	protected void removeSportInfoFromDb(){
		DbHelper.getInstance().cSportInfoBeanLongDBManager().deleteAll();
	}
	private List<Polyline> mPolylineList = new ArrayList<>();
	private Polyline mCurPolyline;
	/**绘制两个坐标点之间的线段,从以前位置到现在位置*/
	private void drawPointAtMap() {
		// 绘制一个大地曲线
//		ViseLog.e("画点: old:"+oldData,"new:"+newData);
		if (mCurPolyline==null){
			mCurPolyline = aMap.addPolyline((new PolylineOptions()).width(10).color(Color.RED));
			mPolylineList.add(mCurPolyline);
			if (!bSportBoolean){
				mCurPolyline.setColor(Color.GRAY);
			}
		}
		mCurPolyline.setPoints(mMapControl.getCurListLatLng());
	}

	private LatLng mHeadRLatng;
	private LatLng mTailLLatng;
	private void recoverPoint(CSportInfoBean cSportInfoBean,LatLng newLatLng){

		//开始运动
		((MainFragment)getParentFragment()).tabBarAnimateOut();
		aMap.getUiSettings().setZoomControlsEnabled(true);
		mMapControl.setSportType(Integer.parseInt(cSportInfoBean.getTypes()),calorie_uint);
		img_jbxing_start.setImageResource(R.drawable.pause_run);
		mImgStateRide.setClickable(false);
		mImgStateRun.setClickable(false);
		startOrPause = false;

		//暂停运动
		bSportBoolean = false;
		mCurPolyline = null;
		mStatus = PAUSE;
		onPauseSport();
		img_jbxing_start.setVisibility(View.INVISIBLE);
		mBtnReStartRun.setVisibility(View.VISIBLE);
		mBtnFinishRun.setVisibility(View.VISIBLE);

		List<SportPointBean> mDrawList = new ArrayList<>();
		try{
			JSONArray jsonArray = new JSONArray("[" + cSportInfoBean.getPath() + "]");
			for (int i=0; i<jsonArray.length(); i++){
				ArrayList<LatLng> latLngList = new ArrayList<>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String type = jsonObject.getString("type");
				String path = jsonObject.getString("path");
				String[] points = path.split("\\$");
				for (int j=0; j<points.length; j++){
				String[] latlont = points[j].split(",");
					try{
						LatLng latLng = new LatLng(Double.parseDouble(latlont[0]),Double.parseDouble(latlont[1]));
						latLngList.add(latLng);
					}catch (Exception e){
						ViseLog.e(e);
						continue;
					}
				}
				mDrawList.add(new SportPointBean(type,latLngList));
			}
		}catch (JSONException e){
			ViseLog.e(e);
		}

		//清理出异常数据
		for (int i=0; i<mDrawList.size();++i){
			SportPointBean sportPointBean = mDrawList.get(i);
			ArrayList<LatLng> arrayList = sportPointBean.getPoints();
			if (arrayList.size() <=5) {
				float nds = AMapUtils.calculateLineDistance(arrayList.get(0),arrayList.get(arrayList.size()-1));
				if (nds > 100){
					mDrawList.remove(i);
				}
			} else if (arrayList.size() <=10 ){
				float nds = AMapUtils.calculateLineDistance(arrayList.get(0),arrayList.get(arrayList.size()-1));
				if (nds > 200){
					mDrawList.remove(i);
				}
			}else if (arrayList.size() <=20){
				float nds = AMapUtils.calculateLineDistance(arrayList.get(0),arrayList.get(arrayList.size()-1));
				if (nds > 500){
					mDrawList.remove(i);
				}
			}
		}


		for (int i=0; i<mDrawList.size();++i){
			SportPointBean sportPointBean = mDrawList.get(i);
			String type = sportPointBean.getType();
			ArrayList<LatLng> arrayList = sportPointBean.getPoints();
			Polyline polyline;

			if (type.equals("1")){
				polyline = aMap.addPolyline((new PolylineOptions())
						.addAll(arrayList).color(Color.RED));
				polyline.setWidth(10);
			}else{
				polyline = aMap.addPolyline((new PolylineOptions())
						.addAll(arrayList).geodesic(true).color(Color.GRAY));
				polyline.setWidth(10);
			}
			mPolylineList.add(polyline);


			//补画虚线
			//step-1  准备
			if (i==0){
				mHeadRLatng = arrayList.get(arrayList.size()-1);
				continue;
			}else{
				mTailLLatng = arrayList.get(0);
			}
			//step-2 计算画线
			float nds = AMapUtils.calculateLineDistance(mHeadRLatng,mTailLLatng);
			Log.e("--------------Dis:",""+nds);
			if (nds <= 3){
				mHeadRLatng = arrayList.get(arrayList.size()-1);
				continue;
			}
			Polyline addPolyline = aMap.addPolyline((new PolylineOptions())
					.add(mHeadRLatng).add(mTailLLatng).setDottedLine(true).geodesic(true).color(Color.GRAY));
			addPolyline.setWidth(10);
			//step-3
			mHeadRLatng = arrayList.get(arrayList.size()-1);
			mPolylineList.add(addPolyline);
		}

		//----
		float nds = AMapUtils.calculateLineDistance(mHeadRLatng,newLatLng);
		if (nds > 3){
			Polyline addPolyline = aMap.addPolyline((new PolylineOptions())
					.add(mHeadRLatng).add(newLatLng).setDottedLine(true).geodesic(true).color(Color.GRAY));
			addPolyline.setWidth(10);
			mPolylineList.add(addPolyline);
		}

		//恢复control数据
		mMapControl.recoverData(mDrawList,cSportInfoBean);
	}

	private void removePolyLine(){
		mCurPolyline = null;
		mOldLatLng = null;
		for (int i=0; i<mPolylineList.size(); i++){
			mPolylineList.get(i).remove();
		}
	}

	private void updateSportInfoUi(CSportInfoBean cSportInfoBean){
		if (cSportInfoBean!=null){
			//更新速度
			updateSpeedUi(cSportInfoBean.getSpeed());
			//更新相关UI
			mTvDistance.setText(String.valueOf(MathHelper.div(cSportInfoBean.getDistance(),1000,2)));
			mTvCalorie.setText( String.valueOf(cSportInfoBean.getCalorie()));
		}
	}

	private void updatePoint(LatLng aMapLocation) {
		//画点
		if (mOldLatLng == null) {
			mOldLatLng = new LatLng(aMapLocation.latitude, aMapLocation.longitude);
		}else if(!mOldLatLng.equals(aMapLocation)){
			mOldLatLng = aMapLocation;
		}
		if (mStatus != ZYGOTE)
			drawPointAtMap();
	}

	public void updateSpeedUi(double speed) {
		mTvSpeed.setText(String.valueOf(speed));
	}

	private Marker mLocationMarker;
	//更新地图
	private void updateMapInfo(AMapLocation location,LatLng aMapLocation){

		if (mLocationMarker == null){
			BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.ic_anchor);
			mLocationMarker = aMap.addMarker(new MarkerOptions().position(aMapLocation).icon(des)
					.anchor(0.5f, 0.5f));
		}else{
			mLocationMarker.setPosition(aMapLocation);
		}
		if (bSportBoolean){
			moveCamera(aMapLocation);
		}
		updatePoint(aMapLocation);
	}

	private LocationSource.OnLocationChangedListener mOnLocationChangedListener;
	@Override
	public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
		mOnLocationChangedListener = onLocationChangedListener;
		mMapControl.startlocation();
//		if (mylocation != null)
//			aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 17));
	}

	@Override
	public void deactivate() {
		mOnLocationChangedListener = null;
//		if (mLocationClient != null) {
//			mLocationClient.stopLocation();
//			mLocationClient.onDestroy();
//		}
//		mLocationClient = null;
		mMapControl.stoplocation();
	}
}

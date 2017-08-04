package com.yhrjkf.woyundong.activity;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.CacheResult;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.CSportPointBean;

import com.yhrjkf.woyundong.bean.PathBean;

import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.utils.MathHelper;
import com.yhrjkf.woyundong.view.CookieBar;


import butterknife.Bind;

public class HistoryMapActivity extends BaseActivity implements AMap.OnMapLoadedListener {

	@Bind(R.id.map)
	MapView mMapView;

	private Intent intent;
	private String mid;
	private AMap aMap;

	@Bind(R.id.ahm_img_back)
	ImageView mImgBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_historymap);
		mImgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mMapView.onCreate(savedInstanceState);
		if (aMap == null) {
			aMap = mMapView.getMap();
			aMap.setOnMapLoadedListener(this);
		}
		intent = getIntent();
		mid = String.valueOf(intent.getIntExtra("mid",0));
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {

	}

	@Override
	protected void bindEvent() {

	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
//		mTranslateAnimator.cancelAnimation();
//		mTranslateAnimator = null;
		mMapView=null;
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onMapLoaded() {
		getData();
	}

	@Override
	public void onBackPressedSupport() {
		super.onBackPressedSupport();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mMapView.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	private List<CSportPointBean> mDrawList = new ArrayList<>();
	private List<LatLng> mPointList = new ArrayList<>();
	private LatLngBounds.Builder mBuilder;
	private List<CSportPointBean> analysisPoint(List<PathBean.DataBean> dataBean){
		mBuilder = new LatLngBounds.Builder();

		for (int i=0; i<dataBean.size();++i){
			ArrayList<LatLng> latLngList = new ArrayList<>();
			String type = dataBean.get(i).getType();
			String path = dataBean.get(i).getPath();

			String[] points = path.split("\\$");
			for (int j=0; j<points.length; j++){
				String[] latlont = points[j].split(",");
				try{
					LatLng latLng = new LatLng(Double.parseDouble(latlont[0]),Double.parseDouble(latlont[1]));
					latLngList.add(latLng);
					mBuilder.include(latLng);
				}catch (Exception e){
					ViseLog.e(e);
					continue;
				}
			}
			mPointList.addAll(latLngList);
			mDrawList.add(new CSportPointBean(type,latLngList));
		}
		return mDrawList;
	}
	public void getData() {
		mHud = KProgressHUD.create(this)
				.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
				.setCancellable(true);
		mHud.show();
		WooSportApi.getInstance().startGetSportPoint(App.newInstance(), mid, new ApiCallback<CacheResult<PathBean>>() {
			@Override
			public void onStart() {}

			@Override
			public void onError(ApiException e) {
				ViseLog.e(e);
//				showToast("网络错误,请重试~");
//				new CookieBar.Builder(HistoryMapActivity.this)
//						.setIcon(R.drawable.warning_red)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("网络错误,请重试")
//						.show();
				showTip(R.drawable.warning_red,"网络错误,请重试");
				mHud.dismiss();
			}

			@Override
			public void onCompleted() {}

			@Override
			public void onNext(CacheResult<PathBean> mapPointBeanCacheResult) {
				mHud.dismiss();
				if (mapPointBeanCacheResult==null)
					return;
				List<PathBean.DataBean> dataBean = mapPointBeanCacheResult.getCacheData().getData();
				analysisPoint(dataBean);
				setMarker();
				drawPoint();
			}
		});

	}


//	private LatLng mHeadLLatng;
	private LatLng mHeadRLatng;
	private LatLng mTailLLatng;
//	private LatLng mTailRLatng;
	private void drawPoint() {

		for (int i=0; i<mDrawList.size();++i){
			CSportPointBean sportPointBean = mDrawList.get(i);
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
			CSportPointBean sportPointBean = mDrawList.get(i);
			String type = sportPointBean.getType();
			ArrayList<LatLng> arrayList = sportPointBean.getPoints();

			if (type.equals("1")){
				Polyline polyline = aMap.addPolyline((new PolylineOptions())
						.addAll(arrayList).color(Color.RED));
				polyline.setWidth(10);

			}else{
				Polyline polyline = aMap.addPolyline((new PolylineOptions())
						.addAll(arrayList).geodesic(true).color(Color.GRAY));
				polyline.setWidth(10);
			}

			//补画虚线
			//step-1  准备
			if (i==0){
//				mHeadLLatng = arrayList.get(0);
				mHeadRLatng = arrayList.get(arrayList.size()-1);
				continue;
			}else{
				mTailLLatng = arrayList.get(0);
//				mTailRLatng = arrayList.get(arrayList.size() - 1);
			}
			//step-2 计算画线
			float nds = AMapUtils.calculateLineDistance(mHeadRLatng,mTailLLatng);
			Log.e("--------------Dis:",""+nds);
			if (nds <= 3){
//				mHeadLLatng = arrayList.get(0);
				mHeadRLatng = arrayList.get(arrayList.size()-1);
				continue;
			}
			Polyline polyline = aMap.addPolyline((new PolylineOptions())
					.add(mHeadRLatng).add(mTailLLatng).setDottedLine(true).geodesic(true).color(Color.GRAY));
			polyline.setWidth(10);
			//step-3
//			mHeadLLatng = arrayList.get(0);
			mHeadRLatng = arrayList.get(arrayList.size()-1);
		}

		SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
		// 设置滑动的图标
		smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.ic_anchor));

		LatLng drivePoint = mPointList.get(0);
		Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(mPointList, drivePoint);
		mPointList.set(pair.first, drivePoint);
		List<LatLng> subList = mPointList.subList(pair.first, mPointList.size());

		// 设置滑动的轨迹左边点
		smoothMarker.setPoints(subList);
		// 设置滑动的总时间
		smoothMarker.setTotalDuration(5);
		// 开始滑动
		smoothMarker.startSmoothMove();
	}

	/**
	 * 画标示
	 */
	private void setMarker() {
		// TODO Auto-generated method stub

		LatLng first = mPointList.get(0);
		LatLng last = mPointList.get(mPointList.size()-1);


		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 15));

//		LatLngBounds latlngBounds = mBuilder.build();
//		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds,15));


//		if (MathHelper.sub(first.latitude,last.latitude)>0){
//			LatLngBounds bounds = new LatLngBounds(last, first);
//			aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
//		}else{
//			aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 16));
//		}
//		try{
//			LatLngBounds bounds = new LatLngBounds(last, first);
//			aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
//		}catch (Exception e){
//			ViseLog.e(e);
//		}



		MarkerOptions markerOption = new MarkerOptions();
		markerOption.position(first);
		markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.mstart)));

		MarkerOptions markerOption2 = new MarkerOptions();
		markerOption2.position(last);
		markerOption2.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.mend)));
		aMap.addMarker(markerOption);
		aMap.addMarker(markerOption2);
	}

}

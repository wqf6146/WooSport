package com.yhrjkf.woyundong.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Color;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yhrjkf.woyundong.R;

/**
 * Created by my94493 on 2016/12/15.
 */

public class LocOverlay {
    private static LocOverlay mlocoverlay;
    private LatLng point;
    private float radius;
    private Marker locMarker;
    private Circle locCircle;
    private AMap aMap;

    public LocOverlay(AMap amap) {
        this.aMap = amap;
    }

    /**
     * 位置变化时调用这个方法，实现marker位置变化
     */
    public void locationChanged (LatLng latLng) {
        this.point = latLng;
        if (locMarker == null) {
            addMarker();
        }
        moveLocationMarker();
    }

    /**
     * 平滑移动动画
     */
    private void moveLocationMarker() {
        final LatLng startPoint  = locMarker.getPosition();
        final LatLng endPoint  = point;
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LatLng target = (LatLng) valueAnimator.getAnimatedValue();
                locCircle.setCenter(target);
                locMarker.setPosition(target);
            }
        });
        anim.setDuration(1000);
        anim.start();
    }
    /**
     * 添加定位marker
     */
    private void addMarker() {
        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.ic_anchor);
        locMarker = aMap.addMarker(new MarkerOptions().position(point).icon(des)
                .anchor(0.5f, 0.5f));

    }
    /**
     * 添加定位精度圈
     */
    private void addCircle() {
        locCircle = aMap.addCircle(new CircleOptions().center(point).radius(radius)
                .fillColor(Color.argb(100, 65, 105, 225))
                .strokeColor(Color.argb(255, 65, 105, 225))
                .strokeWidth(2));
    }
    public void remove(){
        if (locMarker != null){
            locMarker.remove();
            locMarker.destroy();
            locMarker = null;
        }
        if (locCircle != null){
            locCircle.remove();
            locCircle = null;
        }
    }

    public class PointEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            LatLng startPoint = (LatLng) startValue;
            LatLng endPoint = (LatLng) endValue;
            double x = startPoint.latitude + fraction * (endPoint.latitude - startPoint.latitude);
            double y = startPoint.longitude + fraction * (endPoint.longitude - startPoint.longitude);
            LatLng point = new LatLng(x, y);
            return point;
        }
    }
}

package com.yhrjkf.woyundong.interfaces;


import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.yhrjkf.woyundong.bean.db.CSportInfoBean;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface SportProcessListening {
    //时间变化
    void sysTime(int value);
    //语音播报
    void reportVoice(String voice);
    //更新坐标掉
    void sysPoint(AMapLocation aMapLocation,LatLng latLng);
    //更新运动状态
    void sysSportStatus(CSportInfoBean cSportInfoBean);
    //通知速度异常
    void notifySpeedOver();
    //通知速度缓慢
    void notifySpeedSlow();
    //通知client超速提醒
    void notifySpeedOverTip();
    //移动摄像头
    void moveCamera(LatLng latLng);
    //打印字符串
    void showString(String string);
    //检查是否恢复轨迹
    void recoverSport(LatLng latLng);
    //获取运动状态
    int getStatus();
}

package com.yhrjkf.woyundong.control;

import android.content.ComponentName;
import android.content.Context;


import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.vise.log.ViseLog;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.bean.CSportPointBean;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.SportPointBean;
import com.yhrjkf.woyundong.bean.db.CSportInfoBean;
import com.yhrjkf.woyundong.bean.db.CSportStatusBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.fragment.JianBuXingFragment;
import com.yhrjkf.woyundong.interfaces.SportProcessListening;
import com.yhrjkf.woyundong.service.NotificationService;
import com.yhrjkf.woyundong.tools.DataUtils;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.utils.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by Administrator on 2017/3/29.
 */

public class MapControl implements AMapLocationListener {
    public static String TAG = NotificationService.class.getName();
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

//    private AMapLocationClient mSportLocationClient;
//    private AMapLocationClientOption mSportLocationOption;

    private ArrayList<LatLng> mLatLngList = new ArrayList<>();
    private List<SportPointBean> mLineList = new ArrayList<>();

//    private static final String FILE_NAME = "WooSports";
//    private static final String WALK = "跑步";
//    private static final String RIDING = "骑行";
    private String TYPE_SPORTING = "1";
    private String TYPE_PAUSE = "2";

//    private int TAG_SLOW = 20;

    public static int TYPE_RUN = 0x1;
    public static int TYPE_RIDE = 0x2;

    private int mSportType;

    //运动信息
    private CSportInfoBean mSportBean;

    //是否在运动
    private boolean mSporting = false;

    //超速tag
//    private int speedOver = 0;
    //慢速tag
    private int speedSLow = 0;

    //路障
    private int roadblock;
    //纠错标志物
    private boolean bCorrection=false;

//    private boolean bResurgence = false;

    //定时
    private Timer mTimer;

//    private int mTestValue = 0;
    private int mValue = 0;//计时
    private int mCountValue=0; //语音计时

    private double mCountDistance=0;//语音距离

    private LatLng mNewPoint;

    private String[] mStrTimeUnit = new String[]{
            "小时","分钟","秒"
    };

    private int SAVE_TAG = 10;//10s保存一次数据
    private int mSaveTag=0;

    private int RUN_MAX_SPEED = 8;              //跑步最快8m/s
    private int RIDE_MAX_SPEED = 10;          //骑行最快10m/s
    private final int POSITION_INTERVAL = 1000;     //定位1秒
    private final int POSITION_LEVEL = 2;
    private CSportStatusBean mStatusBean; //播报状态对象
    private String mReportString;         //要播报的字符串
    private int mTimeTag;
    private double mDistanceTag;

    private double calorieUnit;

    private SportProcessListening mSportView;

    private Context mContext;

    private static MapControl mInstance;

    public static MapControl getInstance(SportProcessListening listening,Context context) {
        if (mInstance==null){
            mInstance =  new MapControl(listening,context);
        }
        return mInstance;
    }

    private MapControl(SportProcessListening listening,Context context){
        mSportView = listening;
        mContext = context;
        onCreate();
    }

    private void timeRun(){
        if (mTimer!=null)
            mTimer.cancel();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mSporting){
                    mValue++;
                    mCountValue++;
                    mSportView.sysTime(mValue);
                }
                mSaveTag++;
                //测试
//                mValue += 20;
//                mCountValue += 20;
            }
        },1000,1000);
    }

    public void onCreate() {
        Log.e(TAG,"onCreate");
    }


    /**
     * 开始定位。
     */
    public void startlocation() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(mContext);
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setInterval(POSITION_INTERVAL * POSITION_LEVEL);
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置为单次定位
//            mLocationOption.setOnceLocation(false);
//            mLocationOption.setMockEnable(true);
            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        } else {
            mLocationClient.startLocation();
        }
        launchNotificationService();
    }

//    /**
//     * 运动时重新配置定位
//     */
//    public void sportLocationStart(){
//        //停止普通定位
//        if (mLocationClient != null) {
//            mLocationClient.stopLocation();
////            mLocationClient.onDestroy();
//        }
//        //开启运动定位
//        if (mSportLocationClient == null) {
//            mSportLocationClient = new AMapLocationClient(mContext);
//            mSportLocationOption = new AMapLocationClientOption();
//            // 设置定位监听
//            mSportLocationClient.setLocationListener(this);
//            // 设置为高精度定位模式
//            mSportLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//            mSportLocationOption.setInterval(1000l);
//            //设置为单次定位
////            mLocationOption.setOnceLocation(false);
////            mLocationOption.setMockEnable(true);
//            // 设置定位参数
//            mSportLocationClient.setLocationOption(mSportLocationOption);
//            mSportLocationClient.startLocation();
//        } else {
//            mSportLocationClient.startLocation();
//        }
//    }
//
//    /***
//     * 运动定位关闭
//     */
//    public void sportLocationDown(){
//        if (mSportLocationClient != null) {
//            mSportLocationClient.stopLocation();
////            mSportLocationClient.onDestroy();
//        }
//        //开启普通定位
//        if (mLocationClient == null) {
//            mLocationClient = new AMapLocationClient(mContext);
//            mLocationOption = new AMapLocationClientOption();
//            // 设置定位监听
//            mLocationClient.setLocationListener(this);
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
//            //设置为单次定位
////            mLocationOption.setOnceLocation(false);
////            mLocationOption.setMockEnable(true);
//            // 设置定位参数
//            mLocationClient.setLocationOption(mLocationOption);
//            mLocationClient.startLocation();
//        } else {
//            mLocationClient.startLocation();
//        }
//    }
    private PowerManager.WakeLock mWakeLock;
    private void getWakeLock(){
        if (mWakeLock==null){
            PowerManager pm = (PowerManager)mContext.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SportRun");
        }
        mWakeLock.acquire();
    }
    private void releaseWakeLock(){
        if  (mWakeLock !=  null  && mWakeLock.isHeld()) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    /***
     * 停止定位
     */
    public void stoplocation(){
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        onReinitData();
        mLocationClient = null;
    }

    public void onDestroy() {
        destroyNotificationService();
        Log.e(TAG,"onDestroy");
    }

    public void onPauseLocatioin(){
        mSporting = false;
    }

    public void onSportDown(){
        mSporting = false;
        if (mTimer!=null){
            mTimer.cancel();
        }
        if (mNotificationBinder!=null)
            mNotificationBinder.hideNotification();
    }
    public void onReinitData(){
//        mTestValue = 0;
        mValue = 0;
        mReportString="";
        mTimeTag=0;
        mDistanceTag=0.0;
        bCorrection=false;
        errorCode=0;
        mCountDistance = 0;
        mCountValue = 0;
        mNewPoint = null;
    }

    /****
     *  接口
     */
    public void pauseLocatioin(){
        onPauseLocatioin();
    }

    public void startSport(){
        mSporting = true;
        if (mLatLngList.size() > 0){
            LatLng last = mLatLngList.get(mLatLngList.size()-1);
            mLineList.add(new SportPointBean(TYPE_PAUSE, mLatLngList));
            mLatLngList = new ArrayList<>();
            mLatLngList.add(last);
        }
        timeRun();
    }

    public void reStartSport(){
        mSporting = true;
        errorCode=0;
        if (mLatLngList.size() > 0){
            LatLng last = mLatLngList.get(mLatLngList.size()-1);
            mLineList.add(new SportPointBean(TYPE_PAUSE, mLatLngList));
            mLatLngList = new ArrayList<>();
            mLatLngList.add(last);
        }
    }

    public void pauseSport(){
        mSporting = false;

        if (mLineList !=null && mLatLngList!=null && mLatLngList.size() > 0){
            LatLng last = mLatLngList.get(mLatLngList.size()-1);
            mLineList.add(new SportPointBean(TYPE_SPORTING, mLatLngList));
            mLatLngList = new ArrayList<>();
            mLatLngList.add(last);
        }

//        if (mTimer!=null){
//            mTimer.cancel();
//        }
    }

    //恢复为暂停状态
    public void recoverData(List<SportPointBean> cSportPointBeanList,CSportInfoBean cSportInfoBean){
        mSportBean = cSportInfoBean;
        mSportBean.setPath("");

        mValue = Integer.parseInt(cSportInfoBean.getTime());
        mCountValue = 0;
        mSaveTag = 0;

        mLineList = cSportPointBeanList;
        mSporting = false;
    }

    public void sportDown(){
        onSportDown();
    }

    public List<LatLng> getCurListLatLng(){
        return mLatLngList;
    }

    public List<SportPointBean> synListData(){
        if (mLatLngList!=null && mLatLngList.size()>0){
            mLineList.add(new SportPointBean(TYPE_PAUSE, mLatLngList));
        }
        return mLineList;
    }

    //****************************
    private void saveListDataToDb(){
        Log.e("Save","Db");
        CSportInfoBean cSportInfoBean = (CSportInfoBean) mSportBean.clone();
        cSportInfoBean.setFinish_time(DataUtils.getStringToDate(DataUtils
                .getCurrentDate()));
        cSportInfoBean.setTime(String.valueOf(mValue));
        cSportInfoBean.setPath(preparePath());
        DbHelper.getInstance().cSportInfoBeanLongDBManager().deleteAll();
        DbHelper.getInstance().cSportInfoBeanLongDBManager().insert(cSportInfoBean);
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
    //处理点
    private String preparePath(){
        StringBuilder path = new StringBuilder();
        for (int i=0; i<mLineList.size(); i++){
            SportPointBean sportPointBean = mLineList.get(i);
            path.append(getPathStr(sportPointBean.getType(),sportPointBean.getPoints()));
            if(i == mLineList.size()-1 && mLatLngList.size() == 0)
                break;
            path.append(',');
        }

        if (mLatLngList.size() > 0){
            path.append(getPathStr(mSporting == true ? TYPE_SPORTING : TYPE_PAUSE,mLatLngList));
        }

        return path.toString();
    }
    //****************************

    //完成跑步
    public CSportInfoBean sportFinish(){
//        sportLocationDown();
        mSportBean.setFinish_time(DataUtils.getStringToDate(DataUtils
                .getCurrentDate()));
        mSportBean.setTime(String.valueOf(mValue));
//        onSportDown();
        releaseWakeLock();
        return mSportBean;
    }
    public void setSportType(int type, int calorie_unit){
//        sportLocationStart();

        LoginBean.UserBean userBean = App.newInstance().getUserBean();
        if (userBean!=null && !TextUtils.isEmpty(userBean.getWalk_speed()) && !TextUtils.isEmpty(userBean.getRun_speed())){
            RUN_MAX_SPEED = Math.round(Float.parseFloat(userBean.getWalk_speed()));
            RIDE_MAX_SPEED = Math.round(Float.parseFloat(userBean.getRun_speed()));
        }else{
            RUN_MAX_SPEED = 4;
            RIDE_MAX_SPEED = 10;
        }

        if (RUN_MAX_SPEED < 4 || RIDE_MAX_SPEED <10){
            RUN_MAX_SPEED = 4;
            RIDE_MAX_SPEED = 10;
        }

        getWakeLock();
        if (mNotificationBinder!=null)
            mNotificationBinder.showNotification();
        onReinitData();
        mSportType = type;
        mStatusBean = App.newInstance().getSportStatus();
        voiceParpare();
        mSportBean = new CSportInfoBean();
        mSportBean.setStart_time(DataUtils.getStringToDate(DataUtils
                .getCurrentDate()));
        mSportBean.setTypes(type == TYPE_RUN ? "1" : "2");
        calorieUnit = (type == TYPE_RUN? 0.812 : 0.325);
        roadblock = type == TYPE_RUN ? RUN_MAX_SPEED*POSITION_LEVEL : RIDE_MAX_SPEED*POSITION_LEVEL;
        mSportBean.setCalorie_unit(calorie_unit);

        mLineList.clear();

        if ( mLatLngList != null){
            mLatLngList.clear();
        }else{
            mLatLngList = new ArrayList<>();
        }
        startSport();
    }


    /****
     *
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
//        mTestValue++;
//        Log.e("TestValue:",""+mTestValue);
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                updateSportInfo(aMapLocation);
            } else {

                int errcode = aMapLocation.getErrorCode();
                String errText = "定位失败(" + errcode + ": " +
                        aMapLocation.getErrorInfo().split(" ")[0] ;
                if (errcode == 14){
                    errText += ",是否打开了沃运动的定位权限";
                }else if (errcode == 4){
                    errText += ",请检查网络连接是否正常,检查GPS信号是否正常";
                }
                errText += ")";

                ViseLog.e("AmapErr", errText);
                mSportView.showString(errText);
            }
        }
    }


    private NotificationService.NotificationBinder mNotificationBinder;
    //获取通知服务连接
	ServiceConnection notificationCon = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
            mNotificationBinder = (NotificationService.NotificationBinder)service;
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
            mNotificationBinder = null;
		}
	};

	private void launchNotificationService(){
        Intent intent = new Intent(mContext,NotificationService.class);
        mContext.startService(intent);
        mContext.bindService(intent,notificationCon,BIND_AUTO_CREATE);
    }

    private void destroyNotificationService(){
        ViseLog.e("destroyNotificationService");
        mContext.unbindService(notificationCon);
        mContext.stopService(new Intent(mContext,NotificationService.class));
    }

    private int errorCode = 0; //纠错次数

    double cslat;
    private void updateSportInfo(AMapLocation aMapLocation) {

//        测试
//        if (cslat == 0){
//            cslat = aMapLocation.getLatitude();
//        }else{
//            cslat -= 0.00014;
//        }
//        LatLng latLng = new LatLng(cslat,aMapLocation.getLongitude());
        LatLng latLng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
        if (mNewPoint == null){
            mNewPoint = latLng;
            mLatLngList.add(mNewPoint);
            mSportView.moveCamera(mNewPoint);
            mSportView.recoverSport(mNewPoint);
            return;
        }
        float nds=0f;
        if (mSporting){
            nds = AMapUtils.calculateLineDistance(mNewPoint,latLng);

            if (nds > roadblock || nds<0) {
                errorCode++;
                mNewPoint = latLng;
                mSportView.showString("速度异常:"+String.valueOf(MathHelper.div(nds,2,2))+"m/s");
                ViseLog.e("errorcode:" + errorCode+"|distance:"+nds);
                if (errorCode > 4){
                    //超速提醒
                    doSpeedOver(errorCode);
                }
                return;
            }
            errorCode=0;
        }

        //正常距离点往下走
        //更新点
        if (!mNewPoint.equals(latLng)){
            mNewPoint = latLng;
            mLatLngList.add(mNewPoint);
        }

        if (mSporting){
            double curDs = mSportBean.getDistance();
            curDs += nds;
            Log.e("curdistance:" + nds,"|distance:"+curDs);
            mCountDistance += nds;
            //速度检测
            speedWatch(nds,mSportType);
            //语音检测
            voiceblockWatch(curDs);
            Double distance = MathHelper.div(curDs,1000,2);
            Double time = MathHelper.div(mValue == 0 ? 1 : mValue,3600);
            Double speed = MathHelper.div(distance,time,2);
            Double calorie = MathHelper.mul(mSportBean.getCalorie_unit() * distance,calorieUnit);
            mSportBean.setDistance(curDs);
            mSportBean.setSpeed(speed);
            if (mNotificationBinder!=null)
                mNotificationBinder.updateNotification(distance);
            mSportBean.setCalorie(Double.parseDouble(String.format("%.2f", calorie)));
            mSportView.sysSportStatus(mSportBean);
        }
        mSportView.sysPoint(aMapLocation,mNewPoint);

        //保存数据到db
        if (mSportView.getStatus() != JianBuXingFragment.ZYGOTE && mSportBean.getDistance() >= 30 && mSaveTag > SAVE_TAG){
            mSaveTag=0;
            saveListDataToDb();
        }

    }
    /**
     * 时间
     *
     *距离 你已经跑步9.8公里
     *时间 你已经跑步47分6秒
     *速度 none
     */

    /**
     * 距离
     *
     *距离 你已经跑步9.8公里
     *时间 你已经跑步47分6秒
     *速度 平均速度为
     */
    //生成播报字符串
    private void voiceParpare(){
        mReportString = "";
        if (!mStatusBean.getBVoiceSports())
            return;
        Boolean bDistance = mStatusBean.getBVoiceDistance();
        Boolean bTime = mStatusBean.getBVoiceTime();
        Boolean bSpeed = mStatusBean.getBVoiceSpeed();
        String distance = mStatusBean.getMDistance();
        String time =mStatusBean.getMTime();
        if (TextUtils.isEmpty(distance)){
            mTimeTag = Integer.parseInt(String.valueOf(time.subSequence(0,time.indexOf("分")))) * 60;
            mDistanceTag=0.0;
        }else{
            mDistanceTag = Double.parseDouble(String.valueOf(distance.subSequence(0,distance.indexOf("公")))) * 1000;
            mTimeTag=0;
        }
        if (bDistance && bTime && bSpeed) {
            //你已经跑步9.8公里 用时5分钟 平均速度为
            mReportString = "你已经跑步%s 用时%s 平均速度为%s";
        }else if (bTime && bSpeed){
            //你已经跑步5分钟 平均速度为
            mReportString = "你已经跑步%s平均速度为%s";
        }else if (bDistance && bSpeed){
            //你已经跑步9.8公里 平均速度为
            mReportString = "你已经跑步%s平均速度为%s";
        }else if (bDistance && bTime){
            //你已经跑步9.8公里 用时5分钟
            mReportString = "你已经跑步%s用时%s";
        }else if (bDistance){
            //你已经跑步9.8公里
            mReportString = "你已经跑步%s";
        }else if (bTime){
            //你已经跑步5分钟
            mReportString = "你已经跑步%s";
        }else if (bSpeed){
            //你当前的平均速度为
            mReportString = "你当前的平均速度为%s";
        }
    }

    private void doReportVoice(double curDs){
        String time = CommonUtils.secondToHMS(mValue);
        Boolean bDistance = mStatusBean.getBVoiceDistance();
        Boolean bTime = mStatusBean.getBVoiceTime();
        Boolean bSpeed = mStatusBean.getBVoiceSpeed();
        String speed = String.format("%.2f", curDs/mValue) + "米每秒";
        String sDis = String.format("%.2f", curDs) + "米";
        String str="";
        String[] tStr = time.split(":");
        time="";
        for (int i=0; i<tStr.length; i++){
            if (tStr[i].equals("00") || tStr[i].equals("0")){
            }else{
                time += tStr[i] + mStrTimeUnit[i];
            }
        }
        if (bDistance && bTime && bSpeed) {
            //你已经跑步9.8公里 用时5分钟 平均速度为
            str = String.format(mReportString, sDis, time, speed);
        }else if (bTime && bSpeed){
            //你已经跑步5分钟 平均速度为
            str = String.format(mReportString,time,speed);
        }else if (bDistance && bSpeed){
            //你已经跑步9.8公里 平均速度为
            str = String.format(mReportString,sDis,speed);
        }else if (bDistance && bTime){
            //你已经跑步9.8公里 用时5分钟
            str = String.format(mReportString,sDis,time);
        }else if (bDistance){
            //你已经跑步9.8公里
            str = String.format(mReportString,sDis);
        }else if (bTime){
            //你已经跑步5分钟
            str = String.format(mReportString,time);
        }else if (bSpeed){
            //你当前的平均速度为
            str = String.format(mReportString,speed);
        }
        if (TextUtils.isEmpty(str))
            return;
        ViseLog.e(str);
        mSportView.reportVoice(str);
    }

    private void voiceblockWatch(double curDs) {
        if (!mStatusBean.getBVoiceSports())
            return;

        if (mTimeTag >0){
            //时间播报
            if (mCountValue >= mTimeTag){
                doReportVoice(curDs);
                mCountDistance = 0;
                mCountValue = 0;
            }
        }else{
            //距离播报
            if (mCountDistance >= mDistanceTag){
                doReportVoice(curDs);
                mCountDistance = 0;
                mCountValue = 0;
            }
        }
    }

    private void doSpeedOver(int errcode){
//        if(errcode > 20){
//            onSportDown();
//            notifyClientSpeedOver();
//            mSportView.reportVoice("你的速度异常请规范运动");
//        }else
        if (errcode > 5){
            mSportView.reportVoice("你的速度异常,已自动暂停");
            notifyClientSpeedSlow();
//            notifyClientSpeedOverTip();
        }

    }

    private void speedWatch(double ds, int mSportType) {
        int tag = mSportType == TYPE_RIDE ? RIDE_MAX_SPEED : RUN_MAX_SPEED;
        //正常
        if (ds < tag && ds > 1){

        }else if (ds > tag){
            //超速
        }else{
            //缓慢
            if (!mStatusBean.getBAutoPause())
                return;
            speedSLow ++;
            if (speedSLow > 10){
                Log.e(TAG,"缓慢提醒");
                notifyClientSpeedSlow();
                mSportView.reportVoice("你的速度过慢已自动暂停");
                speedSLow = 0;
            }
        }
    }
    //通知client速度异常
    private void notifyClientSpeedOver() {
        mSportView.notifySpeedOver();
    }

    //通知client速度缓慢
    private void notifyClientSpeedSlow() {
        mSportView.notifySpeedSlow();
    }

    //通知client超速提醒
    private void notifyClientSpeedOverTip() {
        mSportView.notifySpeedOverTip();
    }
}

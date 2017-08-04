package com.yhrjkf.woyundong.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.vise.log.ViseLog;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.activity.MainActivity;



/**
 * Created by Administrator on 2017/3/6.
 */

public class NotificationService extends Service {

    public static String TAG = NotificationService.class.getName();

    private NotificationManager mNotificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new NotificationBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }

    private NotificationManager getNotificationManager(){
        if (mNotificationManager==null){
            mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"onUnbind");
        //清空
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");

    }
    public class NotificationBinder extends Binder{
        public void hideNotification(){
            onHideNotification();
        }
        public void updateNotification(double speed){
            onUpdateNotification(speed);
        }
        public void showNotification(){
            onShowNotification();
        }
//
    }

    //启动前台通知
    private void onShowNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.logo)
                .setContentTitle("--KM")
                .setContentText("里程");

        // 设置启动的程序，如果存在则找出，否则新的启动
        Intent intent = new Intent();
        intent.setAction("com.yhrjkf.woyundong.activity.action.RETURN");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, MainActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);//将经过设置了的Intent绑定给PendingIntent
        //获取通知服务
        NotificationManager nm = getNotificationManager();
//		//构建通知
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        notification.contentIntent = contentIntent;
//		//显示通知
        nm.notify(1,notification);
//		//启动为前台服务
        startForeground(1,notification);
    }
    private void onUpdateNotification(double curds){

        String ds = curds + "KM";

        NotificationManager nm = getNotificationManager();
//		//构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(ds)
                .setContentText("里程");
        Intent intent = new Intent();
        intent.setAction("com.yhrjkf.woyundong.activity.action.RETURN");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(this, MainActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);//将经过设置了的Intent绑定给PendingIntent
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        notification.contentIntent = contentIntent;
//		//显示通知
        nm.notify(1,notification);
    }

    private void onHideNotification() {
        onUpdateNotification(0d);
        NotificationManager nm = getNotificationManager();
        nm.cancelAll();
    }
}


package com.yhrjkf.woyundong;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


import com.vise.log.ViseLog;
import com.vise.log.common.LogConvert;
import com.vise.xsnow.ViseApplication;
import com.vise.xsnow.cache.SpCache;
import com.vise.xsnow.loader.LoaderFactory;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.wanjian.cockroach.Cockroach;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.db.CSportStatusBean;
import com.yhrjkf.woyundong.bean.db.CUserBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.service.ScreenReceiver;
import com.yhrjkf.woyundong.utils.AECHFileWriter;
import com.yhrjkf.woyundong.utils.SharedPreferencesUtil;
import com.yhrjkf.woyundong.utils.VoiceUtils;
import com.yhrjkf.woyundong.utils.WooConfig;

import java.io.File;
import java.util.List;

/**
 * App
 * 
 * @author minking
 */
public class App extends ViseApplication {
	private static App mApp;
	private static LoginBean.UserBean mUserBean;

	public static App newInstance(){
		return mApp;
	}

	private File mUserFile;

	//语音提示
	private Boolean mVoiceTip=true;
	private CSportStatusBean mSportStatus;

	@Override
	public void onCreate() {
		// 初始化ImageLoader
		super.onCreate();
		mApp = this;

		Cockroach.install(new Cockroach.ExceptionHandler() {

			// handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

			@Override
			public void handlerException(final Thread thread, final Throwable throwable) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						try {
							//写本地
							AECHFileWriter.getInstance(mApp).writeEx2File(throwable);
							//写服务器
							WooSportApi.getInstance().startPushException(mApp,LogConvert.objectToString(throwable));
							Toast.makeText(App.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
						} catch(Throwable e){
							WooSportApi.getInstance().startPushException(mApp,LogConvert.objectToString(e));
							Toast.makeText(App.this, "Exception Happend\n" + thread + "\n" + e.toString(), Toast.LENGTH_SHORT).show();
							ViseLog.e(e);
						}
					}
				});
			}
		});

		DbHelper.getInstance().init(this);
		LoaderFactory.getLoader().init(this);
		VoiceUtils.init(this);
//		initDb();
		initSportStatus();

		registerScreenReceiver();
	}

	private void initDb() {
		mSportStatus = new CSportStatusBean(true,true,false,false,false,"每一公里播报","1公里","");
		DbHelper.getInstance().cSportStatusBeanLongDBManager().deleteAll();
		DbHelper.getInstance().cSportStatusBeanLongDBManager().insert(mSportStatus);
	}

	private void initSportStatus() {
		mSportStatus = DbHelper.getInstance().cSportStatusBeanLongDBManager().load(1l);
		if (mSportStatus == null){
			//初始化运动状态
			mSportStatus = new CSportStatusBean(true,true,false,false,false,"每一公里播报","1公里","");
			DbHelper.getInstance().cSportStatusBeanLongDBManager().deleteAll();
			DbHelper.getInstance().cSportStatusBeanLongDBManager().insert(mSportStatus);
		}
	}

	public CSportStatusBean getSportStatus(){
		if(mSportStatus == null){
			initSportStatus();
		}
		return mSportStatus;
	}

	public void saveSportStatus(){
		DbHelper.getInstance().cSportStatusBeanLongDBManager().update(mSportStatus);
//		DbHelper.getInstance().cSportStatusBeanLongDBManager().deleteAll();
//		DbHelper.getInstance().cSportStatusBeanLongDBManager().insert(mSportStatus);
	}

	public File getUserFile(){
		return mUserFile;
	}

	public void setUserFile(File file){
		mUserFile = file;
	}

	public void setVoiceTip(Boolean voiceTip){
		mVoiceTip = voiceTip;
	}

	public Boolean getVoiceTip(){
		return mVoiceTip;
	}

	public void saveUserBean(LoginBean loginBean){
		final SpCache spCache = new SpCache(this, WooConfig.USER.USER_FILE);
		spCache.put(WooConfig.USER.LOGINBEAN,loginBean);
		mUserBean = loginBean.getData();
	}

	public LoginBean.UserBean getUserBean(){
        if (mUserBean!=null)
            return mUserBean;
		final SpCache spCache = new SpCache(this, WooConfig.USER.USER_FILE);
		LoginBean loginBean= (LoginBean)spCache.get(WooConfig.USER.LOGINBEAN);
		if (loginBean == null){

			WooSportApi.getInstance().startUpdateUser(this, new ApiCallback<LoginBean>() {
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
				public void onNext(LoginBean loginBean){
					spCache.put(WooConfig.USER.LOGINBEAN,loginBean);
                    mUserBean = loginBean.getData();
				}
			});
		}else{
            mUserBean = loginBean.getData();
			return mUserBean;
		}
		return null;
	}

	/**
	 * 注册screen状态广播接收器
	 */
	private void registerScreenReceiver() {

		ScreenReceiver screenReceiver = new ScreenReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		registerReceiver(screenReceiver, filter);
	}
}

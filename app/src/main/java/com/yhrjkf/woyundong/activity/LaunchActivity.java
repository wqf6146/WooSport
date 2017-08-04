package com.yhrjkf.woyundong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.db.CUserBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.utils.SystemBarTintManager;

public class LaunchActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
			finish();
			return;
		}
		setContentView(R.layout.activity_main);
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.transparency);
		doStart();
	}

	private void doStart() {

		final CUserBean cUserBean = DbHelper.getInstance().cUserBeanLongDBManager().load(1L);
		if (cUserBean ==null){
			Intent intent = new Intent();
			intent.setClass(LaunchActivity.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
		}else{
			WooSportApi.getInstance().startLogin(this, cUserBean.getMPhone(), cUserBean.getMPassword(),new ApiCallback<LoginBean>(){
						@Override
						public void onStart() {
							ViseLog.i("Start Login");
						}

						@Override
						public void onNext(LoginBean loginBean) {
							if (loginBean.getRecode().equals("200")) {
								App.newInstance().saveUserBean(loginBean);
								DbHelper.getInstance().cUserBeanLongDBManager().deleteAll();
								DbHelper.getInstance().cUserBeanLongDBManager().insert(new CUserBean(loginBean.getData().getPhone(),
										cUserBean.getMPassword(),
										loginBean.getData().getPassword()));
								Intent intent = new Intent();
								intent.setClass(LaunchActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							}else{
								Toast.makeText(LaunchActivity.this,
										"[errcode:"+loginBean.getRecode()+"]["+loginBean.getMsg()+"]",Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setClass(LaunchActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							}
						}

						@Override
						public void onError(ApiException e) {
							ViseLog.e(e);
							Toast.makeText(LaunchActivity.this,
									"网络错误,请检查网络是否连接~",Toast.LENGTH_LONG).show();
							Intent intent = new Intent();
							intent.setClass(LaunchActivity.this,
									MainActivity.class);
							startActivity(intent);
							finish();
						}

						@Override
						public void onCompleted() {
							ViseLog.i("Start Success");
						}
					});
		}
	}

}

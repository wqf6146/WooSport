package com.yhrjkf.woyundong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.vise.log.ViseLog;
import com.white.countdownbutton.CountDownButton;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.http.api.ApiSercice;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;

public class RegisterActivity extends BaseActivity implements OnClickListener {


	@Bind(R.id.tv_register_back)
	TextView tv_register_back;
	@Bind(R.id.tv_register_wancheng)
	TextView tv_register_wancheng;

	@Bind(R.id.ed_register_sjh)
	EditText ed_register_sjh;

	@Bind(R.id.ed_register_mm)
	EditText ed_register_mm;

	@Bind(R.id.ed_register_zcmm)
	EditText ed_register_zcmm;

	@Bind(R.id.ed_register_yzm)
	EditText ed_register_yzm;

	private String jsonString;
	private String errcode;
	private Toast mToast;
	String rellString = "9999";

	@Bind(R.id.bt_yzm)
	CountDownButton mBtnYzm;

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
		ed_register_sjh.setInputType(EditorInfo.TYPE_CLASS_PHONE);
	}

	@Override
	protected void bindEvent() {
		tv_register_back.setOnClickListener(this);
		tv_register_wancheng.setOnClickListener(this);
		mBtnYzm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击按钮要执行的操作,同时开始倒计时

				mHud = KProgressHUD.create(RegisterActivity.this)
						.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
						.setCancellable(true);
				mHud.show();


				String strPhone = ed_register_sjh.getText().toString();

				if (CommonUtils.checkMobile(strPhone)){
					HttpUtils httpUtilss = new HttpUtils();
					httpUtilss.send(HttpMethod.GET, ApiSercice.NOTE + "?type=1&phone="
									+ strPhone,
							new RequestCallBack<String>() {

								@Override
								public void onFailure(HttpException arg0, String arg1) {
									// TODO Auto-generated method stub
//									ViseLog.e(arg0);
//									ViseLog.e("arg1");
//									showToast(arg1);
									mHud.dismiss();
									showTip(R.drawable.warning_red,arg1);
									mBtnYzm.removeCountDown();
								}

								@Override
								public void onSuccess(ResponseInfo<String> responseInfo) {
									// TODO Auto-generated method stub
									mHud.dismiss();
									Log.i("result", responseInfo.result);
									try {
										JSONObject jsonObject = new JSONObject(
												responseInfo.result);
										String code = jsonObject.getString("flag");
										if (code.equals("200") || code.equals("sucss") ){
											rellString = jsonObject.getString("message");
//											showToast("验证码获取成功!");
//											new CookieBar.Builder(RegisterActivity.this)
//													.setIcon(R.drawable.warning_blue)
//													.setBackgroundColor(R.color.white)
//													.setMessageColor(R.color.grayfont)
//													.setMessage("验证码获取成功!")
//													.show();
											showTip(R.drawable.warning_blue,"验证码获取成功");
										}else{
//											new CookieBar.Builder(RegisterActivity.this)
//													.setIcon(R.drawable.warning_blue)
//													.setBackgroundColor(R.color.white)
//													.setMessageColor(R.color.grayfont)
//													.setMessage(jsonObject.getString("message"))
//													.show();
											showTip(R.drawable.warning_red,jsonObject.getString("message"));
//											showToast(jsonObject.getString("message"));
											rellString="9999";
											mBtnYzm.removeCountDown();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
				}else{
					mHud.dismiss();
//					showToast("只接受格式正确的联通号码注册");
//					new CookieBar.Builder(RegisterActivity.this)
//							.setIcon(R.drawable.warning_blue)
//							.setBackgroundColor(R.color.white)
//							.setMessageColor(R.color.grayfont)
//							.setMessage("只接受格式正确的联通号码注册")
//							.show();
					showTip(R.drawable.warning_red,"只接受格式正确的联通号码注册");
					mHander.postDelayed(new Runnable() {
						@Override
						public void run() {
							mBtnYzm.removeCountDown();
						}
					},300);
				}

			}
		});
	}

	Handler mHander = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			mBtnYzm.removeCountDown();
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setStatusBarColor(R.color.redbar);
		super.onCreate(savedInstanceState);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();
			return;
		}
		setContentView(R.layout.activity_register);
//		tv_register_back = (TextView) findViewById(R.id.tv_register_back);
//		tv_register_wancheng = (TextView) findViewById(R.id.tv_register_wancheng);
//		ed_register_sjh = (EditText) findViewById(R.id.ed_register_sjh);
//		ed_register_mm = (EditText) findViewById(R.id.ed_register_mm);
//		ed_register_zcmm = (EditText) findViewById(R.id.ed_register_zcmm);

//		ed_register_yzm = (EditText) findViewById(R.id.ed_register_yzm);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_register_back:
			finish();
			break;

		case R.id.tv_register_wancheng:
			String yzm = ed_register_yzm.getText().toString();
			String mm = ed_register_mm.getText().toString();
			String zcmm = ed_register_zcmm.getText().toString();
			String sjh = ed_register_sjh.getText().toString();
			if ((TextUtils.isEmpty(sjh))){
//				new CookieBar.Builder(RegisterActivity.this)
//						.setIcon(R.drawable.warning_blue)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("请输入新注册的手机号")
//						.show();
				showTip(R.drawable.warning_blue,"请输入新注册的手机号");
				return;
			}
			if (TextUtils.isEmpty(mm) || TextUtils.isEmpty(zcmm)){
//				new CookieBar.Builder(RegisterActivity.this)
//						.setIcon(R.drawable.warning_blue)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("请输入密码")
//						.show();
				showTip(R.drawable.warning_blue,"请输入密码");
				return;
			}
			if (!mm.equals(zcmm)){
//				new CookieBar.Builder(RegisterActivity.this)
//						.setIcon(R.drawable.warning_blue)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("两次密码输入的不一样")
//						.show();
				showTip(R.drawable.warning_blue,"两次密码输入的不一样");
				return;
			}
			if (TextUtils.isEmpty(yzm)) {
//				showToast("请输入验证码");
//				new CookieBar.Builder(RegisterActivity.this)
//						.setIcon(R.drawable.warning_blue)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("请输入验证码")
//						.show();
				showTip(R.drawable.warning_blue,"请输入验证码");
				return;
			}
			if (yzm.equals(rellString)) {
				startRegister();
			} else {
//					showToast("验证码输入有误");
//				new CookieBar.Builder(RegisterActivity.this)
//						.setIcon(R.drawable.warning_blue)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("验证码输入有误")
//						.show();
				showTip(R.drawable.warning_blue,"验证码输入有误");
			}
			break;

		default:
			break;
		}

	}

	public void startRegister(){
		mHud = KProgressHUD.create(this)
				.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
				.setCancellable(true);
		mHud.show();
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams("UTF-8");
		params.addBodyParameter("phone", ed_register_sjh
				.getText().toString());
		params.addBodyParameter("password", ed_register_mm
				.getText().toString());
		httpUtils.send(HttpMethod.POST, ApiSercice.REGISTER,
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(
							HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
//						ViseLog.e(arg0);
//						ViseLog.e("arg1");
//						showToast(arg1);
						showTip(R.drawable.warning_red,arg1);
						mHud.dismiss();
					}

					@Override
					public void onSuccess(
							ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						mHud.dismiss();
						jsonString = responseInfo.result;
						Log.i("result", jsonString);
						try {
							JSONObject jsonObject = new JSONObject(
									jsonString);
							errcode = jsonObject.get(
									"Recode").toString();
							Log.i("errcode", errcode);
							if (errcode.equals("200")) {
								showToast("注册成功");
								finish();
							} else if (errcode
									.equals("400")) {
//													showToast(errcode+":"+jsonObject.getString("Msg"));
//								new CookieBar.Builder(RegisterActivity.this)
//										.setIcon(R.drawable.warning_blue)
//										.setBackgroundColor(R.color.white)
//										.setMessageColor(R.color.grayfont)
//										.setMessage(errcode+":"+jsonObject.getString("Msg"))
//										.show();
								showTip(R.drawable.warning_red,errcode+":"+jsonObject.getString("Msg"));
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch
							// block
							e.printStackTrace();
						}

					}
		});
	}

	public void showToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), text,
					Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
}


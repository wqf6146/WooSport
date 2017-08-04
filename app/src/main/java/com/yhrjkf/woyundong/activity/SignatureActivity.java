package com.yhrjkf.woyundong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import java.util.HashMap;

public class SignatureActivity extends BaseActivity {
	private ImageView ll_back;
	private EditText ed_nickname;

	private TextView tv_my_cunchu;

	KProgressHUD mHud;
	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {
		ll_back = (ImageView) findViewById(R.id.as_img_back);
		tv_my_cunchu = (TextView) findViewById(R.id.as_btn_save);
		ed_nickname = (EditText) findViewById(R.id.ed_nickname);
	}

	@Override
	protected void bindEvent() {
		ll_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		tv_my_cunchu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String signStr = ed_nickname.getText().toString();
				if (TextUtils.isEmpty(signStr)){
//					showToast("请输入签名");
//					new CookieBar.Builder(SignatureActivity.this)
//							.setIcon(R.drawable.warning_blue)
//							.setBackgroundColor(R.color.white)
//							.setMessageColor(R.color.grayfont)
//							.setMessage("请输入签名")
//							.show();
					showTip(R.drawable.warning_blue,"请输入签名");
					return;
				}
				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
//					showToast("用户标示意外丢失,请重试~");
					showTip(R.drawable.warning_red,"用户标示意外丢失,请重试~");
					return;
				}
				HashMap hashMap = new HashMap();
				hashMap.put("uid",String.valueOf(userBean.getId()));
//				hashMap.put("realname",String.valueOf(userBean.getRealname()));
//				hashMap.put("email",userBean.getEmail());
//				hashMap.put("sex",String.valueOf(userBean.getSex()));
//				hashMap.put("height",String.valueOf(userBean.getHeight()));
//				hashMap.put("weight",String.valueOf(userBean.getWeight()));
				hashMap.put("motto",signStr);
//				hashMap.put("com_level",String.valueOf(userBean.getCom_level()));
//				hashMap.put("employee_num",String.valueOf(userBean.getEmployee_num()));

				mHud = KProgressHUD.create(SignatureActivity.this)
						.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
						.setCancellable(true);
				mHud.show();
				WooSportApi.getInstance().startPostUserMeg(mContext, hashMap, new ApiCallback<LoginBean>() {
					@Override
					public void onStart() {}

					@Override
					public void onNext(LoginBean loginBean) {
						mHud.dismiss();
						if (loginBean.getRecode().equals("200")){
							App.newInstance().saveUserBean(loginBean);
							showToast("保存成功");
							finish();
						}else{
//							showToast(loginBean.getMsg());
							showTip(R.drawable.warning_red,loginBean.getMsg());
						}

					}

					@Override
					public void onError(ApiException e) {
						ViseLog.e(e);
						mHud.dismiss();
//                        showToast("网络错误,请重试~");
//						new CookieBar.Builder(SignatureActivity.this)
//								.setIcon(R.drawable.warning_blue)
//								.setBackgroundColor(R.color.white)
//								.setMessageColor(R.color.grayfont)
//								.setMessage("网络错误,请重试")
//								.show();
						showTip(R.drawable.warning_blue,"网络错误,请重试");
					}

					@Override
					public void onCompleted() {}
				});

//
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();
			return;
		}
		setContentView(R.layout.activity_signature);
	}

}

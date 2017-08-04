package com.yhrjkf.woyundong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.view.CookieBar;

import java.util.HashMap;

public class EmailActivity extends BaseActivity {
	private ImageView ll_back;
	private EditText ed_nickname;
	private TextView tv_my_cunchu;
	@Override
	protected void initData() {}

	@Override
	protected void initView() {
		ll_back = (ImageView) findViewById(R.id.ae_img_back);
		tv_my_cunchu = (TextView) findViewById(R.id.ae_btn_save);
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
				String nickname = ed_nickname.getText().toString();
				if (!CommonUtils.emailValidation(nickname)){
//					showToast("请输入正确的邮箱格式!");
//					new CookieBar.Builder(EmailActivity.this)
//							.setIcon(R.drawable.warning_blue)
//							.setBackgroundColor(R.color.white)
//							.setMessageColor(R.color.grayfont)
//							.setMessage("请输入正确的邮箱格式!")
//							.show();
					showTip(R.drawable.warning_blue,"请输入正确的邮箱格式");
					return;
				}


				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
					showToast("用户标示意外丢失,请重试~");
					return;
				}
				HashMap hashMap = new HashMap();
				hashMap.put("uid",String.valueOf(userBean.getId()));
				hashMap.put("email",nickname);

				mHud = KProgressHUD.create(EmailActivity.this)
						.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
						.setCancellable(true);
				mHud.show();
				WooSportApi.getInstance().startPostUserMeg(mContext, hashMap, new ApiCallback<LoginBean>() {
					@Override
					public void onStart() {
					}

					@Override
					public void onNext(LoginBean loginBean) {
						mHud.dismiss();
						if (loginBean.getRecode().equals("200")){
							App.newInstance().saveUserBean(loginBean);
							showToast("保存成功！");
							finish();
						}else{
//							showToast(loginBean.getMsg());
//							new CookieBar.Builder(EmailActivity.this)
//									.setIcon(R.drawable.warning_red)
//									.setBackgroundColor(R.color.white)
//									.setMessageColor(R.color.grayfont)
//									.setMessage(loginBean.getMsg())
//									.show();
							showTip(R.drawable.warning_red,loginBean.getMsg());
						}
					}

					@Override
					public void onError(ApiException e) {
						ViseLog.e(e);
						mHud.dismiss();
//                        showToast("网络错误,请重试~");
//						new CookieBar.Builder(EmailActivity.this)
//								.setIcon(R.drawable.warning_red)
//								.setBackgroundColor(R.color.white)
//								.setMessageColor(R.color.grayfont)
//								.setMessage("网络错误,请重试")
//								.show();
						showTip(R.drawable.warning_red,"网络错误,请重试");
					}

					@Override
					public void onCompleted() {
						mHud.dismiss();
					}
				});

			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
	}


}

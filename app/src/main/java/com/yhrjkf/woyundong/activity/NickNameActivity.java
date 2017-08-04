package com.yhrjkf.woyundong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NickNameActivity extends BaseActivity {
	private ImageView ll_back;
	private EditText ed_nickname;
	private TextView tv_my_cunchu;

	@Override
	protected void initView() {
		ll_back = (ImageView) findViewById(R.id.an_img_back);
		tv_my_cunchu = (TextView) findViewById(R.id.an_btn_save);
		ed_nickname = (EditText) findViewById(R.id.ed_nickname);
	}

	@Override
	protected void initData() {

	}
///-=--------------
	public  int getWordCount(String s)
	{
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}
	public boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
	///-=--------------
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
				if (TextUtils.isEmpty(nickname)){
//					showToast("请输入昵称");
//					new CookieBar.Builder(NickNameActivity.this)
//							.setIcon(R.drawable.warning_blue)
//							.setBackgroundColor(R.color.white)
//							.setLayoutGravity(Gravity.BOTTOM)
//							.setMessageColor(R.color.grayfont)
//							.setMessage("请输入昵称")
//							.show();
					showTip(R.drawable.warning_blue,"请输入昵称");
					return;
				}

				if ( (isNumeric(nickname) && getWordCount(nickname) > 11) ||
						getWordCount(nickname) > 16){
					showTip(R.drawable.warning_blue,"您输入的名称太长了");
					return;
				}

				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
					showToast("用户标示意外丢失,请重试~");
					return;
				}
				HashMap hashMap = new HashMap();
				hashMap.put("uid", String.valueOf(userBean.getId()));
				hashMap.put("realname", nickname);
				//hashMap.put("email", userBean.getEmail());
				//hashMap.put("sex", String.valueOf(userBean.getSex()));
				//hashMap.put("height", String.valueOf(userBean.getHeight()));
				//hashMap.put("weight", String.valueOf(userBean.getWeight()));
				//hashMap.put("motto", String.valueOf(userBean.getMotto()));
				//hashMap.put("com_level", String.valueOf(userBean.getCom_level()));
				//hashMap.put("employee_num", String.valueOf(userBean.getEmployee_num()));
				mHud = KProgressHUD.create(NickNameActivity.this)
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
						if (loginBean.getRecode().equals("200")) {
							App.newInstance().saveUserBean(loginBean);
							showToast("保存成功！");
							finish();
						}else{
//							showToast(loginBean.getMsg());
//							new CookieBar.Builder(NickNameActivity.this)
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
//						showToast("网络错误,请重试~");
//						new CookieBar.Builder(NickNameActivity.this)
//								.setIcon(R.drawable.warning_red)
//								.setBackgroundColor(R.color.white)
//								.setMessageColor(R.color.grayfont)
//								.setMessage("网络错误,请重试")
//								.show();
						showTip(R.drawable.warning_red,"网络错误,请重试");
						mHud.dismiss();
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
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();
			return;
		}
		setContentView(R.layout.activity_nickname);
	}

}

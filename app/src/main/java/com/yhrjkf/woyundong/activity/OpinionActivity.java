package com.yhrjkf.woyundong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.http.api.AppConfig;
import com.yhrjkf.woyundong.view.CookieBar;

public class OpinionActivity extends BaseActivity implements OnClickListener {
	private ImageView ll_set_back;
	private TextView tv_yjfk_tj;
	private EditText ed_myjy;
	private String jianYi;
	private String flag;

	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {
		ll_set_back = (ImageView) findViewById(R.id.aa_img_back);
		tv_yjfk_tj = (TextView) findViewById(R.id.tv_yjfk_tj);
		ed_myjy = (EditText) findViewById(R.id.ed_myjy);
	}

	@Override
	protected void bindEvent() {
		ll_set_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_yjfk_tj.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_opinion);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_yjfk_tj:
			jianYi = ed_myjy.getText().toString();
			if (jianYi == null || jianYi.equals("")) {
//				showToast("内容不能为空");
//				new CookieBar.Builder(OpinionActivity.this)
//						.setIcon(R.drawable.warning_blue)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("内容不能为空")
//						.show();
				showTip(R.drawable.warning_blue,"内容不能为空");
			} else {
				HttpUtils httpUtils = new HttpUtils();
				RequestParams params = new RequestParams("UTF-8");
				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
//					showToast("用户标示意外丢失,请重试~");
					showTip(R.drawable.warning_blue,"用户标示意外丢失,请重试~");
					return;
				}
				params.addBodyParameter("uid", String.valueOf(userBean.getId()));
				params.addBodyParameter("content", jianYi);
				httpUtils.send(HttpMethod.POST, AppConfig.OPINION, params,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								// TODO Auto-generated method stub
								Log.i("result", responseInfo.result);
								try {
									JSONObject jsonObject = new JSONObject(
											responseInfo.result);
									flag = jsonObject.getString("flag");
									if (flag.equals("scuss")) {
//										showToast("提交成功感谢您的建议");
										showTip(R.drawable.warning_blue,"提交成功感谢您的建议");
										finish();
									}

								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

			}

			break;

		default:
			break;
		}

	}


}

package com.yhrjkf.woyundong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.http.api.AppConfig;

public class ProductActivity extends BaseActivity {

	private ImageView ll_back;
	private TextView tv_neirong;

	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {

	}

	@Override
	protected void bindEvent() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		ll_back = (ImageView) findViewById(R.id.ap_img_back);
		tv_neirong = (TextView) findViewById(R.id.tv_neirong);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, AppConfig.CONTENT + "?type=2",
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						Log.i("result", responseInfo.result);

						try {
							JSONObject jsonObject = new JSONObject(
									responseInfo.result);
							String message = jsonObject.getString("message");
							tv_neirong.setText(message);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		ll_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}

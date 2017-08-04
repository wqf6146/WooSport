package com.yhrjkf.woyundong.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.http.api.AppConfig;

public class TechnologyActivity extends BaseActivity {

	private ImageView ll_set_back;
	private WebView web_technology;

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

		setContentView(R.layout.activtiy_technology);
		ll_set_back = (ImageView) findViewById(R.id.at_img_back);
		web_technology = (WebView) findViewById(R.id.web_technology);
		web_technology.loadUrl(AppConfig.TECHNOLOGY);
		WebSettings webSettings = web_technology.getSettings();
		// webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptEnabled(true);
		web_technology.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});

		ll_set_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
}

package com.yhrjkf.woyundong.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vise.log.ViseLog;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
//import com.yhrjkf.woyundong.http.Api;
//import com.yhrjkf.woyundong.http.api.ApiSercice;

import butterknife.Bind;
import im.delight.android.webview.AdvancedWebView;

public class ConsultFragment extends BaseFragment implements  AdvancedWebView.Listener {

	@Bind(R.id.fc_webview)
	AdvancedWebView mWebView;

	@Bind(R.id.fc_img_back)
	ImageView mImgBack;

	@Bind(R.id.fc_tv_title)
	TextView mTvTitle;

	String mUrl,mTitle;

	@Bind(R.id.fc_rl_topbar)
	RelativeLayout mTopBar;

	@Bind(R.id.fc_fl_error)
	FrameLayout mRlError;

	public static ConsultFragment getInstance() {
		ConsultFragment fragment = new ConsultFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}


	@Override
	public boolean onBackPressedSupport() {
		if (!mWebView.onBackPressed()) { return true; }
		return super.onBackPressedSupport();
	}


	@Override
	protected int getFragmentLayoutId() {
		return R.layout.fragment_consult;
	}

	@Override
	protected void initView(View contentView, Bundle savedInstanceState) {

	}

	@Override
	protected void initData() {
		mUrl = getArguments().getString("URL");
		mTitle = getArguments().getString("TITLE");
		if (TextUtils.isEmpty(mTitle))
			mTopBar.setVisibility(View.GONE);
		else
			mTvTitle.setText(mTitle);
		init();
	}

	private void init() {
		mWebView.setListener(getActivity(), this);
		mWebView.setGeolocationEnabled(false);
		mWebView.setMixedContentAllowed(true);
		mWebView.setCookiesEnabled(true);
		mWebView.setThirdPartyCookiesEnabled(true);
		mWebView.addHttpHeader("X-Requested-With", "");
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {


			}

		});
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
//				Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
			}

		});
		mWebView.loadUrl(mUrl);
	}

	public void setUrl(String url){
		if (mWebView==null)
			return;
		mWebView.loadUrl(url);
	}

	@Override
	public void onPageStarted(String url, Bitmap favicon) {
		if (url.equals(mUrl)) {
			mImgBack.setVisibility(View.INVISIBLE);
			return;
		}
		mImgBack.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPageFinished(String url) {
		if (mRlError.getVisibility() == View.VISIBLE)
			mRlError.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onPageError(int i, String s, String s1) {
		ViseLog.e(s);
		mRlError.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

	@Override
	public void onExternalPageRequest(String url) { }

	@Override
	protected void bindEvent() {
		mImgBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.onBackPressed();
			}
		});
		mRlError.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.loadUrl(mUrl);
			}
		});
	}
}

package com.yhrjkf.woyundong.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.activity.RegisterActivity;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;

public class LoginFragment extends BaseFragment implements OnClickListener {

	private TextView tv_login_denglu;
	private EditText ed_login_sjh, ed_login_mm, ed_login_yqm;

	@Bind(R.id.al_bg)
	LinearLayout mBgLayout;

	@Bind(R.id.al_tv_cantlogin)
	TextView mTvCantLogin;

	@Bind(R.id.al_tv_zhuce)
	TextView tv_login_zhuce;

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.frgment_login;
	}

	public static LoginFragment getInstance() {
		LoginFragment fragment = new LoginFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}

	@Override
	protected void initView(View contentView, Bundle savedInstanceState) {
		tv_login_denglu = (TextView) contentView.findViewById(R.id.tv_login_denglu);
		ed_login_sjh = (EditText) contentView.findViewById(R.id.ed_login_sjh);
		ed_login_mm = (EditText) contentView.findViewById(R.id.ed_login_mm);
		ed_login_yqm = (EditText) contentView.findViewById(R.id.ed_login_yqm);
	}

	@Override
	protected void bindEvent() {
		tv_login_zhuce.setOnClickListener(this);
		tv_login_denglu.setOnClickListener(this);
		mBgLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).
						hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});

		mTvCantLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				start(FindPdFragment.getInstance());
			}
		});
	}


	@Override
	protected void initData() {
		ed_login_sjh.setInputType(EditorInfo.TYPE_CLASS_PHONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.al_tv_zhuce:
			Intent intent = new Intent();
			intent.setClass(getActivity(), RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_login_denglu:
			doLogin();
		default:
			break;
		}
	}

	private void doLogin(){
		final String sphone = ed_login_sjh.getText().toString();
		final String spasswd = ed_login_mm.getText().toString();
		final String skey = ed_login_yqm.getText().toString();
		if (sphone.isEmpty()){
			showTip(R.drawable.warning_blue,"请输入手机号");
			return;
		}
		if (spasswd.isEmpty()){
			showTip(R.drawable.warning_blue,"请输入密码");
			return;
		}
		if (skey.isEmpty()){
			showTip(R.drawable.warning_blue,"请输入邀请码");
			return;
		}

		WooSportApi.getInstance().startLogin(_mActivity, sphone, spasswd, skey);
	}
}

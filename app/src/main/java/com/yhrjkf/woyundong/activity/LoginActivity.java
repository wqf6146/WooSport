package com.yhrjkf.woyundong.activity;

import android.os.Bundle;

import com.vise.xsnow.manager.AppManager;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.fragment.LoginFragment;

public class LoginActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setStatusBarColor(R.color.redbar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loadRootFragment(R.id.al_container, LoginFragment.getInstance());
	}

	@Override
	protected void bindEvent() {

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initView() {

	}

	@Override
	public void onBackPressedSupport() {
		super.onBackPressedSupport();
		AppManager.getInstance().appExit(this);
	}
}

package com.yhrjkf.woyundong.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;

import butterknife.Bind;

public class RuleFragment extends BaseFragment {

	@Bind(R.id.fr_img_back)
	ImageView mImgBack;

	@Override
	protected void initView(View contentView, Bundle savedInstanceState) {

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void bindEvent() {
		mImgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop();
			}
		});
	}

	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.fragment_rule;
	}

	public static RuleFragment getInstance() {
		RuleFragment fragment = new RuleFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}
}

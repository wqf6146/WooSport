package com.yhrjkf.woyundong.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.suke.widget.SwitchButton;
import com.vise.xsnow.event.BusFactory;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.activity.AboutUsActivity;
import com.yhrjkf.woyundong.activity.OpinionActivity;
import com.yhrjkf.woyundong.activity.ProductActivity;
import com.yhrjkf.woyundong.activity.TechnologyActivity;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.db.CSportStatusBean;
import com.yhrjkf.woyundong.event.JumpFragmentEvent;
import com.yhrjkf.woyundong.fragment.VoiceReportFragment;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;

public class SetFragment extends BaseFragment implements OnClickListener {

	private RelativeLayout rl_set_yjfk, rl_set_cpjs, rl_set_gywm, rl_set_jszc;
	private TextView tv_set_tbydsj, tv_set_bb;

	@Bind(R.id.as_img_back)
	ImageView mImgBack;

	@Bind(R.id.ll_set_yyts)
	RelativeLayout mRlYyBp;

	@Bind(R.id.as_tv_yybb)
	TextView mTvVoiceReport;

	@Bind(R.id.fv_btn_autupause)
	SwitchButton mBtnAutoPause;

	@Bind(R.id.tv_set_resetpwd)
	TextView mTvResetPwd;

	public static SetFragment getInstance() {
		SetFragment fragment = new SetFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}

	@Override
	protected void initData() {

		PackageManager manager;
		PackageInfo info = null;
		manager = mContext.getPackageManager();
		try {
			info = manager.getPackageInfo(mContext.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_set_bb.setText("V" + info.versionName);

		updateUi();
	}

	@Override
	protected void initView(View contentView, Bundle savedInstanceState) {
		rl_set_yjfk = (RelativeLayout) contentView.findViewById(R.id.rl_set_yjfk);

		tv_set_tbydsj = (TextView) contentView.findViewById(R.id.tv_set_tbydsj);
		rl_set_cpjs = (RelativeLayout) contentView.findViewById(R.id.rl_set_cpjs);
		rl_set_gywm = (RelativeLayout) contentView.findViewById(R.id.rl_set_gywm);
		rl_set_jszc = (RelativeLayout) contentView.findViewById(R.id.rl_set_jszc);

		tv_set_bb = (TextView) contentView.findViewById(R.id.tv_set_bb);
	}

	private void updateUi(){
		CSportStatusBean cSportStatusBean = App.newInstance().getSportStatus();
		if (cSportStatusBean.getBVoiceSports()){
			mTvVoiceReport.setText(cSportStatusBean.getMStrReportStatus());
		}else{
			mTvVoiceReport.setText("关");
		}
		mBtnAutoPause.setChecked(cSportStatusBean.getBAutoPause());
	}

	@Override
	protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
		super.onFragmentResult(requestCode, resultCode, data);
		if (requestCode == 1){
			updateUi();
		}
	}

	@Override
	protected void bindEvent() {
//		mSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(SwitchButton switchButton, boolean b) {
//				App.newInstance().setVoiceTip(b);
//			}
//		});
		mRlYyBp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startForResult(VoiceReportFragment.getInstance(),1);
			}
		});
		rl_set_yjfk.setOnClickListener(this);
		mImgBack.setOnClickListener(this);
		tv_set_tbydsj.setOnClickListener(this);
		rl_set_cpjs.setOnClickListener(this);
		rl_set_gywm.setOnClickListener(this);
		rl_set_jszc.setOnClickListener(this);
		mBtnAutoPause.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SwitchButton switchButton, boolean b) {
				App.newInstance().getSportStatus().setBAutoPause(b);
				App.newInstance().saveSportStatus();
			}
		});
		mTvResetPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				start(UpdatePwdFragment.getInstance());
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		CSportStatusBean cSportStatusBean = App.newInstance().getSportStatus();
		if (cSportStatusBean.getBVoiceSports()){
			mTvVoiceReport.setText(cSportStatusBean.getMStrReportStatus());
		}else{
			mTvVoiceReport.setText("关");
		}
		mBtnAutoPause.setChecked(cSportStatusBean.getBAutoPause());
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.activity_set;
	}

	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_set_cpjs:
			Intent intent1 = new Intent();
			intent1.setClass(mContext, ProductActivity.class);
			startActivity(intent1);

			break;
		case R.id.rl_set_gywm:
			Intent intent2 = new Intent();
			intent2.setClass(mContext, AboutUsActivity.class);
			startActivity(intent2);
			break;
		case R.id.as_img_back:
			pop();
			break;
		case R.id.tv_set_tbydsj:
			mHud = KProgressHUD.create(mContext)
					.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
					.setCancellable(true);
			mHud.show();
			WooSportApi.getInstance().startUpdateUser(mContext, new ApiCallback<LoginBean>() {
				@Override
				public void onCompleted() {
					mHud.dismiss();
				}

				@Override
				public void onError(ApiException e) {
//					new CookieBar.Builder(_mActivity)
//							.setIcon(R.drawable.warning_red)
//							.setBackgroundColor(R.color.white)
//							.setMessageColor(R.color.grayfont)
//							.setMessage("网络错误~请重试")
//							.show();
					showTip(R.drawable.warning_red,"网络错误,请重试");
					mHud.dismiss();
				}

				@Override
				public void onNext(LoginBean loginBean) {
					mHud.dismiss();
					App.newInstance().saveUserBean(loginBean);
//					SpCache sp = new SpCache(mContext, WooConfig.USER.USER_FILE);
//					sp.put(WooConfig.USER.LOGINBEAN,loginBean);
//					showToast("数据更新成功");
//					new CookieBar.Builder(_mActivity)
//							.setIcon(R.drawable.warning_blue)
//							.setBackgroundColor(R.color.white)
//							.setMessageColor(R.color.grayfont)
//							.setMessage("数据更新成功")
//							.show();
					showTip(R.drawable.warning_blue,"数据更新成功");
				}

				@Override
				public void onStart() {

				}
			});
			break;

		case R.id.rl_set_yjfk:
			Intent intent = new Intent();
			intent.setClass(mContext, OpinionActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_set_jszc:
			Intent intent3 = new Intent();
			intent3.setClass(mContext, TechnologyActivity.class);
			startActivity(intent3);
			break;

		default:
			break;
		}

	}
}

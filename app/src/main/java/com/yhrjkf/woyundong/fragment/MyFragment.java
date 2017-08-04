package com.yhrjkf.woyundong.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.vise.xsnow.event.BusFactory;
import com.vise.xsnow.manager.AppManager;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.activity.LoginActivity;
import com.yhrjkf.woyundong.activity.NewRecordActivity;
import com.yhrjkf.woyundong.activity.PersonalActivity;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.LoginBean.UserBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.event.JumpForResultFragmentEvent;
import com.yhrjkf.woyundong.event.JumpFragmentEvent;

import java.io.File;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyFragment extends BaseFragment implements OnClickListener {

	@Bind(R.id.fm_tv_jifen)
	TextView mTvJifen;

	@Bind(R.id.fm_tv_level)
	TextView mTvLevel;

	@Bind(R.id.fm_userimg)
	ImageView mImgUserImg;

	@Bind(R.id.fm_username)
	TextView mTvUserName;


	@Bind(R.id.ll_myf_ls)
	RelativeLayout ll_myf_ls;


	@Bind(R.id.ll_myf_jl)
	RelativeLayout ll_myf_jl;

	@Bind(R.id.ll_myf_jz)
	RelativeLayout ll_myf_jz;

	@Bind(R.id.ll_myf_sz)
	RelativeLayout ll_myf_sz;

	@Bind(R.id.fm_tv_edit)
	TextView mTvEdit;

	@Bind(R.id.fm_btn_compile)
	ImageView mBtnCompile;

	@Bind(R.id.fm_btn_logout)
	Button mBtnLogout;

	public static MyFragment getInstance() {
		MyFragment fragment = new MyFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}

	@Override
	public void onLazyInitView(@Nullable Bundle savedInstanceState) {
		super.onLazyInitView(savedInstanceState);
        LoginBean.UserBean userBean = App.newInstance().getUserBean();
        if (userBean == null) {
            showToast("用户标示意外丢失,请重试~");
            return;
        }
		Glide.with(_mActivity).load(userBean.getAvatar()).asBitmap().centerCrop()
					.placeholder(R.drawable.df).into(new BitmapImageViewTarget(mImgUserImg) {
				@Override
				protected void setResource(Bitmap resource) {
					RoundedBitmapDrawable circularBitmapDrawable =
							RoundedBitmapDrawableFactory.create(_mActivity.getResources(), resource);
					circularBitmapDrawable.setCircular(true);
					mImgUserImg.setImageDrawable(circularBitmapDrawable);
				}
			});
	}

	@Override
	protected void initData() {
//		updateUi();
	}

	@Override
	protected void initView(View contentView,Bundle saveInstance) {
		setOnResumeRegisterBus(true);

	}

	public void updateUi(){
        LoginBean.UserBean userBean = App.newInstance().getUserBean();
        if (userBean == null) {
            showToast("用户标示意外丢失");
            return;
        }

		mTvUserName.setText(userBean.getRealname());
		mTvLevel.setText("LV" + userBean.getLevel());
		mTvJifen.setText(String.valueOf(userBean.getPoint()));

//		File imgFile = App.newInstance().getUserFile();
//		if (App.newInstance().getUserFile() != null){
//			Glide.with(this).load(imgFile).asBitmap().into(new BitmapImageViewTarget(mImgUserImg) {
//				@Override
//				protected void setResource(Bitmap resource) {
//					RoundedBitmapDrawable circularBitmapDrawable =
//							RoundedBitmapDrawableFactory.create(_mActivity.getResources(), resource);
//					circularBitmapDrawable.setCircular(true);
//					mImgUserImg.setImageDrawable(circularBitmapDrawable);
//				}
//			});
//		}
		if (App.newInstance().getUserFile()!=null){
			Glide.with(_mActivity).load(App.newInstance().getUserFile()).asBitmap().centerCrop()
					.placeholder(R.drawable.df).into(new BitmapImageViewTarget(mImgUserImg) {
				@Override
				protected void setResource(Bitmap resource) {
					RoundedBitmapDrawable circularBitmapDrawable =
							RoundedBitmapDrawableFactory.create(_mActivity.getResources(), resource);
					circularBitmapDrawable.setCircular(true);
					mImgUserImg.setImageDrawable(circularBitmapDrawable);
				}
			});
			App.newInstance().setUserFile(null);
		}else{
			Glide.with(_mActivity).load(userBean.getAvatar()).asBitmap().centerCrop()
					.placeholder(R.drawable.df).into(new BitmapImageViewTarget(mImgUserImg) {
				@Override
				protected void setResource(Bitmap resource) {
					RoundedBitmapDrawable circularBitmapDrawable =
							RoundedBitmapDrawableFactory.create(_mActivity.getResources(), resource);
					circularBitmapDrawable.setCircular(true);
					mImgUserImg.setImageDrawable(circularBitmapDrawable);
				}
			});
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		updateUi();
	}

	@Override
	protected void bindEvent() {
		mTvEdit.setOnClickListener(this);
		mBtnCompile.setOnClickListener(this);
		ll_myf_ls.setOnClickListener(this);
		ll_myf_jl.setOnClickListener(this);
		ll_myf_jz.setOnClickListener(this);
		ll_myf_sz.setOnClickListener(this);
		mImgUserImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(_mActivity,PersonalActivity.class);
				startActivity(intent);
			}
		});
		mBtnLogout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleLogout();
			}
		});
	}

	private void handleLogout(){
		new SweetAlertDialog(_mActivity,SweetAlertDialog.WARNING_TYPE)
				.setConfirmText("确定退出")
				.setTitleText("是否退出当前账号?")
				.setContentText("退出后邀请码将变更")
				.setCancelText("取消")
				.setCancelClickListener(null)
				.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(SweetAlertDialog sweetAlertDialog) {
						DbHelper.getInstance().cUserBeanLongDBManager().deleteAll();
						Intent intent = new Intent();
						intent.setClass(_mActivity, LoginActivity.class);
						_mActivity.startActivity(intent);
						sweetAlertDialog.dismiss();
					}
				})
				.show();
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.fragment_my;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.fm_btn_compile:
				Intent intent = new Intent();
				intent.setClass(_mActivity,PersonalActivity.class);
				startActivity(intent);

//				((SupportFragment)getParentFragment()).startForResult(PersonalActivity.getInstance(),1);
				break;
			case R.id.fm_tv_edit:
				Intent intent1 = new Intent();
				intent1.setClass(_mActivity,PersonalActivity.class);
				startActivity(intent1);
//				((SupportFragment)getParentFragment()).startForResult(PersonalActivity.getInstance(),1);
				break;
			case R.id.ll_myf_ls:
				BusFactory.getBus().post(new JumpFragmentEvent(NewHistoryFragment.getInstance()));
//				((SupportFragment)getParentFragment()).start(NewHistoryFragment.getInstance());
				break;
			case R.id.ll_myf_jl:
				Intent intent3 = new Intent();
				intent3.setClass(_mActivity, NewRecordActivity.class);
				startActivity(intent3);

				break;
			case R.id.ll_myf_jz:
//				Intent intent1 = new Intent();
//				intent1.setClass(_mActivity, NewMedalFragment.class);
//				startActivity(intent1);
				BusFactory.getBus().post(new JumpFragmentEvent(NewMedalFragment.getInstance()));
				break;
			case R.id.ll_myf_sz:
				BusFactory.getBus().post(new JumpForResultFragmentEvent(SetFragment.getInstance()));
				break;

			default:
				break;
		}
	}
}

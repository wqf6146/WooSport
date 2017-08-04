package com.yhrjkf.woyundong.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.white.countdownbutton.CountDownButton;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.AuthCodeBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/22.
 */

public class FindPwAuthFragment extends BaseFragment {

    @Bind(R.id.ff_edit_phone)
    EditText mEditPhone;

    @Bind(R.id.ff_tv_next)
    TextView mTvNext;

    @Bind(R.id.ff_tv_authstr)
    TextView mTvAuthStr;

    @Bind(R.id.ff_btn_authcode)
    CountDownButton mBtnCountDown;

    @Bind(R.id.fhn_img_back)
    ImageView mBtnBack;

    String mPhone;
    String mAuthCode;
    String mUid;

    @Override
    protected void initData() {
        mTvAuthStr.setText(String.format(getResources().getString(R.string.authstr),mPhone));
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        mPhone = getArguments().getString("phone");
        mAuthCode = getArguments().getString("authcode");
        mUid = getArguments().getString("uid");
    }

    @Override
    public boolean onBackPressedSupport() {
        popTo(LoginFragment.class,false);
        return true;
    }

    @Override
    protected void bindEvent() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTo(LoginFragment.class,false);
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEditPhone.getText().toString();
                if (str.equals(mAuthCode)){
                    ResetPwdFragment fragment = ResetPwdFragment.getInstance();
                    fragment.getArguments().putString("uid",mUid);
                    start(fragment);
                }else{
//                    showToast("验证码错误!");
//                    new CookieBar.Builder(_mActivity)
//                            .setIcon(R.drawable.warning_blue)
//                            .setBackgroundColor(R.color.white)
//                            .setMessageColor(R.color.grayfont)
//                            .setMessage("验证码错误")
//                            .show();
                    showTip(R.drawable.warning_red,"验证码错误");
                }
            }
        });

        mBtnCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHud = KProgressHUD.create(mContext)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true);
                mHud.show();
                WooSportApi.getInstance().startGetAuthCode(mContext, "2", mPhone, new ApiCallback<AuthCodeBean>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        ViseLog.e(e);
                        mHud.dismiss();
//                        showToast("网络错误,请重试~");
//                        new CookieBar.Builder(_mActivity)
//                                .setIcon(R.drawable.warning_blue)
//                                .setBackgroundColor(R.color.white)
//                                .setMessageColor(R.color.grayfont)
//                                .setMessage("网络错误,请重试")
//                                .show();
                        showTip(R.drawable.warning_red,"网络错误,请重试");
                    }

                    @Override
                    public void onCompleted() {
                        mHud.dismiss();
                    }

                    @Override
                    public void onNext(AuthCodeBean authCodeBean) {
                        mHud.dismiss();
                        if (authCodeBean.getFlag().equals("200") || authCodeBean.getFlag().equals("sucss")){
//                            start(FindPwAuthFragment.getInstance());
                            mAuthCode = authCodeBean.getMessage();
                            mUid = authCodeBean.getData();
//                            showToast("验证码已重新发送,注意查收!");
//                            new CookieBar.Builder(_mActivity)
//                                    .setIcon(R.drawable.warning_blue)
//                                    .setBackgroundColor(R.color.white)
//                                    .setMessageColor(R.color.grayfont)
//                                    .setMessage("验证码已重新发送,注意查收!")
//                                    .show();
                            showTip(R.drawable.warning_red,"验证码已重新发送,注意查收");
                        }else{
//                            showToast(authCodeBean.getMessage());
//                            new CookieBar.Builder(_mActivity)
//                                    .setIcon(R.drawable.warning_blue)
//                                    .setBackgroundColor(R.color.white)
//                                    .setMessageColor(R.color.grayfont)
//                                    .setMessage(authCodeBean.getMessage())
//                                    .show();
                            showTip(R.drawable.warning_red,authCodeBean.getMessage());
                        }
                    }
                });
            }
        });

        mBtnCountDown.startCountDown();

        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = mEditPhone.getText().toString();
                if (TextUtils.isEmpty(str)){
                    mTvNext.setClickable(true);
                    mTvNext.setBackgroundResource(R.drawable.textview_back_gray);
                    mTvNext.setTextColor(getResources().getColor(R.color.graybtnfont));
                }else{
                    mTvNext.setClickable(true);
                    mTvNext.setBackgroundResource(R.drawable.textview_back_red);
                    mTvNext.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    public static FindPwAuthFragment getInstance() {
        FindPwAuthFragment fragment = new FindPwAuthFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_findpwauth;
    }
}

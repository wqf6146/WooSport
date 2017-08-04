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
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.ResetPwdResBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/22.
 */

public class ResetPwdFragment extends BaseFragment {

    @Bind(R.id.fp_btn_commitpwd)
    TextView mBtnCommit;

    @Bind(R.id.fp_edit_pwd)
    EditText mEditPwd;

    @Bind(R.id.fp_edit_pwd2)
    EditText mEditPwd2;

    @Bind(R.id.al_img_back)
    ImageView mImgBack;

    String mUid;

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        mUid = getArguments().getString("uid");
    }

    @Override
    protected void initData() {

    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = mEditPwd.getText().toString();
            String str1 = mEditPwd2.getText().toString();
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str1)){
                mBtnCommit.setClickable(false);
                mBtnCommit.setBackgroundResource(R.drawable.textview_back_gray);
                mBtnCommit.setTextColor(getResources().getColor(R.color.graybtnfont));
            }else{
                mBtnCommit.setClickable(true);
                mBtnCommit.setBackgroundResource(R.drawable.textview_back_red);
                mBtnCommit.setTextColor(getResources().getColor(R.color.white));
            }
        }
    };
    @Override
    public boolean onBackPressedSupport() {
        popTo(LoginFragment.class,false);
        return true;
    }
    @Override
    protected void bindEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTo(LoginFragment.class,false);
            }
        });
        mEditPwd.addTextChangedListener(mTextWatcher);
        mEditPwd2.addTextChangedListener(mTextWatcher);
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEditPwd.getText().toString();
                String str2 = mEditPwd2.getText().toString();
                if (! str.equals(str2)){
//                    showToast("密码输入不一致,请重新输入!");
//                    new CookieBar.Builder(_mActivity)
//                            .setIcon(R.drawable.warning_blue)
//                            .setBackgroundColor(R.color.white)
//                            .setMessageColor(R.color.grayfont)
//                            .setMessage("密码输入不一致,请重新输入")
//                            .show();
                    showTip(R.drawable.warning_red,"密码输入不一致,请重新输入");
                    return;
                }
                mHud = KProgressHUD.create(mContext)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true);
                mHud.show();
                WooSportApi.getInstance().startResetPwd(mContext, mUid, str2, new ApiCallback<ResetPwdResBean>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        ViseLog.e(e);
//                        showToast("网络异常,请重试~");
//                        new CookieBar.Builder(_mActivity)
//                                .setIcon(R.drawable.warning_red)
//                                .setBackgroundColor(R.color.white)
//                                .setMessageColor(R.color.grayfont)
//                                .setMessage("网络异常,请重试")
//                                .show();
                        showTip(R.drawable.warning_red,"网络错误,请重试");
                        mHud.dismiss();
                    }

                    @Override
                    public void onCompleted() {
                        mHud.dismiss();
                    }

                    @Override
                    public void onNext(ResetPwdResBean resetPwdResBean) {
                        mHud.dismiss();
                        if (resetPwdResBean.getRecode().equals("200") && resetPwdResBean.getData().equals("success")){
                            showToast("修改成功!");
                            popTo(LoginFragment.class,false);
                        }else{
//                            showToast(resetPwdResBean.getMsg());
//                            new CookieBar.Builder(_mActivity)
//                                    .setIcon(R.drawable.warning_blue)
//                                    .setBackgroundColor(R.color.white)
//                                    .setMessageColor(R.color.grayfont)
//                                    .setMessage(resetPwdResBean.getMsg())
//                                    .show();
                            showTip(R.drawable.warning_red,resetPwdResBean.getMsg());
                        }
                    }
                });
            }
        });
    }

    public static ResetPwdFragment getInstance() {
        ResetPwdFragment fragment = new ResetPwdFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_pwd;
    }
}

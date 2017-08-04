package com.yhrjkf.woyundong.fragment;

import android.content.Intent;
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
import com.vise.xsnow.manager.AppManager;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.activity.LoginActivity;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.ResetPwdResBean;
import com.yhrjkf.woyundong.bean.db.CUserBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/22.
 */

public class UpdatePwdFragment extends BaseFragment {

    @Bind(R.id.fp_btn_commitpwd)
    TextView mBtnCommit;

    @Bind(R.id.fu_edit_newpwd)
    EditText mEditNewPwd;

    @Bind(R.id.fu_edit_newpwd2)
    EditText mEditNewPwd2;

    @Bind(R.id.fu_edit_oldpwd)
    EditText mEditOldPwd;

    @Bind(R.id.al_img_back)
    ImageView mImgBack;


    public static UpdatePwdFragment getInstance() {
        UpdatePwdFragment fragment = new UpdatePwdFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
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
            String newpwd = mEditNewPwd.getText().toString();
            String newpwd2 = mEditNewPwd2.getText().toString();
            String oldpwd = mEditOldPwd.getText().toString();
            if (TextUtils.isEmpty(newpwd) || TextUtils.isEmpty(newpwd2) || TextUtils.isEmpty(oldpwd)){
                mBtnCommit.setClickable(false);
                mBtnCommit.setBackgroundResource(R.drawable.textview_back_gray);
                mBtnCommit.setTextColor(getResources().getColor(R.color.graybtnfont));
            }else{
                mBtnCommit.setClickable(true);
                mBtnCommit.setBackgroundResource(R.drawable.blue_button_background);
                mBtnCommit.setTextColor(getResources().getColor(R.color.white));
            }
        }
    };
    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }
    @Override
    protected void bindEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mEditNewPwd.addTextChangedListener(mTextWatcher);
        mEditNewPwd2.addTextChangedListener(mTextWatcher);
        mEditOldPwd.addTextChangedListener(mTextWatcher);
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String npwd = mEditNewPwd.getText().toString();
                final String npwd2 = mEditNewPwd2.getText().toString();
                String oldpwd = mEditOldPwd.getText().toString();
                if (TextUtils.isEmpty(npwd) || TextUtils.isEmpty(npwd2) ||
                        TextUtils.isEmpty(oldpwd)){
//                    showToast("请补全信息");
//                    new CookieBar.Builder(_mActivity)
//                            .setIcon(R.drawable.warning_blue)
//                            .setBackgroundColor(R.color.white)
//                            .setMessageColor(R.color.grayfont)
//                            .setMessage("请补全信息")
//                            .show();
                    showTip(R.drawable.warning_blue,"请补全信息");
                    return;
                }
                if (! npwd.equals(npwd2)){
//                    showToast("密码输入不一致,请重新输入!");
//                    new CookieBar.Builder(_mActivity)
//                            .setIcon(R.drawable.warning_blue)
//                            .setBackgroundColor(R.color.white)
//                            .setMessageColor(R.color.grayfont)
//                            .setMessage("密码输入不一致,请重新输入")
//                            .show();
                    showTip(R.drawable.warning_blue,"密码输入不一致,请重新输入");
                    return;
                }
                mHud = KProgressHUD.create(mContext)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true);
                mHud.show();
                LoginBean.UserBean userBean = App.newInstance().getUserBean();
                if (userBean == null) {
                    showToast("用户标示意外丢失,请重试~");
                    return;
                }
                WooSportApi.getInstance().startUpdatePwd(mContext, String.valueOf(userBean.getId()),
                        oldpwd,npwd2, new ApiCallback<ResetPwdResBean>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        ViseLog.e(e);
////                        showToast("网络错误,请重试!");
//                        new CookieBar.Builder(_mActivity)
//                                .setIcon(R.drawable.warning_red)
//                                .setBackgroundColor(R.color.white)
//                                .setMessageColor(R.color.grayfont)
//                                .setMessage("网络错误,请重试")
//                                .show();
                        showTip(R.drawable.warning_blue,"网络错误,请重试");
                        mHud.dismiss();
                    }

                    @Override
                    public void onCompleted() {
                        mHud.dismiss();
                    }

                    @Override
                    public void onNext(ResetPwdResBean resetPwdResBean) {

                        if (resetPwdResBean.getRecode().equals("200") && resetPwdResBean.getData().equals("success")){
                            LoginBean.UserBean userBean = App.newInstance().getUserBean();
                            if (userBean == null) {
                                showToast("用户标示意外丢失,请重试~");
                                return;
                            }
                            WooSportApi.getInstance().startLogin(mContext, userBean.getPhone(), npwd2, new ApiCallback<LoginBean>() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onError(ApiException e) {
                                    mHud.dismiss();
//                                    showToast("网络错误,请重试~");
//                                    new CookieBar.Builder(_mActivity)
//                                            .setIcon(R.drawable.warning_red)
//                                            .setBackgroundColor(R.color.white)
//                                            .setMessageColor(R.color.grayfont)
//                                            .setMessage("网络错误,请重试")
//                                            .show();
                                    showTip(R.drawable.warning_red,"网络错误,请重试");
                                    ViseLog.e(e);
                                }

                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onNext(LoginBean loginBean) {
                                    mHud.dismiss();
                                    DbHelper.getInstance().cUserBeanLongDBManager().deleteAll();
                                    DbHelper.getInstance().cUserBeanLongDBManager().insert(new CUserBean(loginBean.getData().getPhone(),
                                            npwd2,loginBean.getData().getPassword()));
                                    showToast("修改成功!");
                                    pop();
                                }
                            });

//                            Intent intent = new Intent();
//                            intent.setClass(getActivity(), LoginActivity.class);
//                            startActivity(intent);
//                            getActivity().finish();
                        }else{
                            mHud.dismiss();
//                            showToast(resetPwdResBean.getMsg());
//                            new CookieBar.Builder(_mActivity)
//                                    .setIcon(R.drawable.warning_red)
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


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_updatepwd;
    }
}

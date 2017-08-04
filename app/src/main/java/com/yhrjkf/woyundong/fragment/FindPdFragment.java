package com.yhrjkf.woyundong.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.yhrjkf.woyundong.activity.SignatureActivity;
import com.yhrjkf.woyundong.bean.AuthCodeBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/22.
 */

public class FindPdFragment extends BaseFragment {

    @Bind(R.id.ff_tv_next)
    TextView mTvNext;

    @Bind(R.id.ff_edit_phone)
    EditText mEditText;

    @Bind(R.id.fhn_img_back)
    ImageView mImgBack;


    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

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
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strPhone = mEditText.getText().toString();
                if (TextUtils.isEmpty(strPhone)){
//                    showToast("请输入注册过的手机号!");
//                    new CookieBar.Builder(_mActivity)
//                            .setIcon(R.drawable.warning_blue)
//                            .setBackgroundColor(R.color.white)
//                            .setMessageColor(R.color.grayfont)
//                            .setMessage("请输入注册过的手机号")
//                            .show();
                    showTip(R.drawable.warning_red,"请输入注册过的手机号");
                    return;
                }
                if (CommonUtils.checkMobile(strPhone)){
                    mHud = KProgressHUD.create(mContext)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true);
                    mHud.show();
                    WooSportApi.getInstance().startGetAuthCode(mContext, "2", strPhone, new ApiCallback<AuthCodeBean>() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onError(ApiException e) {
                            ViseLog.e(e);
//                            showToast("网络错误,请重试~");
//                            new CookieBar.Builder(_mActivity)
//                                    .setIcon(R.drawable.warning_blue)
//                                    .setBackgroundColor(R.color.white)
//                                    .setMessageColor(R.color.grayfont)
//                                    .setMessage("网络错误,请重试")
//                                    .show();
                            showTip(R.drawable.warning_red,"网络错误,请重试");
                            mHud.dismiss();
                        }

                        @Override
                        public void onCompleted() {
                            mHud.dismiss();
                        }

                        @Override
                        public void onNext(AuthCodeBean authCodeBean) {
                            mHud.dismiss();
                            if (authCodeBean.getFlag().equals("200") || authCodeBean.getFlag().equals("sucss")){
                                FindPwAuthFragment findPwAuthFragment = FindPwAuthFragment.getInstance();
                                findPwAuthFragment.getArguments().putString("phone",strPhone);
                                findPwAuthFragment.getArguments().putString("authcode",authCodeBean.getMessage());
                                findPwAuthFragment.getArguments().putString("uid",authCodeBean.getData());
                                start(findPwAuthFragment);
                            }else{
//                                showToast(authCodeBean.getMessage());
//                                new CookieBar.Builder(_mActivity)
//                                        .setIcon(R.drawable.warning_blue)
//                                        .setBackgroundColor(R.color.white)
//                                        .setMessageColor(R.color.grayfont)
//                                        .setMessage(authCodeBean.getMessage())
//                                        .show();
                                showTip(R.drawable.warning_red,authCodeBean.getMessage());
                            }
                        }
                    });
                }else{
//                    showToast("只接受格式正确的联通号码注册");
//                    new CookieBar.Builder(_mActivity)
//                            .setIcon(R.drawable.warning_blue)
//                            .setBackgroundColor(R.color.white)
//                            .setMessageColor(R.color.grayfont)
//                            .setMessage("只接受格式正确的联通号码注册")
//                            .show();
                    showTip(R.drawable.warning_red,"只接受格式正确的联通号码");
                }
            }
        });
    }

    public static FindPdFragment getInstance() {
        FindPdFragment fragment = new FindPdFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_findpd;
    }
}

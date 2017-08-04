package com.yhrjkf.woyundong;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.xsnow.event.BusFactory;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * Created by Administrator on 2017/2/28.
 */

public abstract class BaseFragment extends SupportFragment {
    protected View mRootView;
    protected Context mContext;
    protected Resources mResources;
    protected LayoutInflater mInflater;
    private boolean isOnResumeRegisterBus = false;
    private boolean isOnStartRegisterBus = false;
    Toast mToast;
    protected AlertView mAlertView;
    protected KProgressHUD mHud;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.mResources = mContext.getResources();
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getFragmentLayoutId(), container, false);
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view,savedInstanceState);
        bindEvent();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isOnResumeRegisterBus) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isOnStartRegisterBus) {
            BusFactory.getBus().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isOnResumeRegisterBus) {
            BusFactory.getBus().unregister(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isOnStartRegisterBus) {
            BusFactory.getBus().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected boolean isOnResumeRegisterBus() {
        return isOnResumeRegisterBus;
    }

    protected BaseFragment setOnResumeRegisterBus(boolean onResumeRegisterBus) {
        isOnResumeRegisterBus = onResumeRegisterBus;
        return this;
    }

    protected boolean isOnStartRegisterBus() {
        return isOnStartRegisterBus;
    }

    protected BaseFragment setOnStartRegisterBus(boolean onStartRegisterBus) {
        isOnStartRegisterBus = onStartRegisterBus;
        return this;
    }

    /**
     * 初始化子View
     */
    protected abstract void initView(View contentView,Bundle savedInstanceState);

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     * 布局的LayoutID
     *
     * @return
     */
    abstract protected int getFragmentLayoutId();

    public void showTip(@DrawableRes int icon, String str){
        if (Build.VERSION.SDK_INT  < KITKAT ){
            new CookieBar.Builder(_mActivity)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
            return;
        }
        if (CommonUtils.areNotificationsEnabled(_mActivity)){
            showToast(str);
        }else{
            new CookieBar.Builder(_mActivity)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
        }
    }

    public void showToast(String string){
        mToast = Toast.makeText(mContext, string,Toast.LENGTH_SHORT);
        mToast.show();
    }
    public <T> boolean checkNotNull(T reference) {
        if (reference == null) {
            return false;
        }
        return true;
    }
}

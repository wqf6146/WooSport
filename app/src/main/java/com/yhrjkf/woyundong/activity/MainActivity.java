package com.yhrjkf.woyundong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.vise.xsnow.manager.AppManager;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseActivity;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.fragment.JianBuXingFragment;
import com.yhrjkf.woyundong.fragment.MainFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/2/27.
 */

public class MainActivity extends BaseActivity {

    Boolean mPrepareExit = false;
    private final int BACK = 1;
    private final int EXIT = -1;
    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == BACK){
                mPrepareExit = false;
            }
            return false;
        }
    });

    private MainFragment mMainFragment;

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void bindEvent() {
    }

    @Override
    public void onBackPressedSupport() {
//        moveTaskToBack(false);
        if (mPrepareExit){
            onReceiverDeadSigned();
            AppManager.getInstance().appExit(this);
        }else{
            mPrepareExit = true;
            showToast("再按一次退出沃运动",2000);
            mHandler.sendEmptyMessageDelayed(BACK,2000l);
        }
    }

    @Override
    protected void onReceiverDeadSigned() {
        super.onReceiverDeadSigned();
        if (mMainFragment != null){
            JianBuXingFragment fragment = ((JianBuXingFragment)mMainFragment.getFragment(MainFragment.THIRD));
            if (fragment != null){
                fragment.onDestroy();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }
        setContentView(R.layout.activity_base);
        if (savedInstanceState==null){
            mMainFragment = MainFragment.getInstance();
            replaceLoadRootFragment(R.id.ab_container, mMainFragment,false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}

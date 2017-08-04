package com.yhrjkf.woyundong;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vise.xsnow.event.BusFactory;
import com.yhrjkf.woyundong.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/2/28.
 */

public abstract class BaseToolbarFragment extends SupportFragment {
    protected View mRootView;

    @Bind(R.id.vt_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.vt_title)
    TextView mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getFragmentLayoutId(),container,false);
        ButterKnife.bind(this,mRootView);

        initView();

        return mRootView;
    }

    private void initView(){
        if (mToolbar!=null) {
            mToolbar.setTitle("");
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);


            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop();
                }
            });
        }
    }

    protected void setToolBarVisible(int visible){
        mToolbar.setVisibility(visible);
    }

    protected void setToolbarTitle(String title){
        if (mTitle==null)
            mTitle = (TextView) mRootView.findViewById(R.id.vt_title);
        if (mTitle!=null)
            mTitle.setText(title);
    }

    protected void setNavigationIcon(@DrawableRes int id){
        if (mToolbar==null)
            mToolbar = (Toolbar) mRootView.findViewById(R.id.vt_toolbar);
        if (mToolbar!=null)
            mToolbar.setNavigationIcon(id);
    }

    abstract protected int getFragmentLayoutId();

    private boolean isOnResumeRegisterBus = false;
    private boolean isOnStartRegisterBus = false;
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

    protected BaseToolbarFragment setOnResumeRegisterBus(boolean onResumeRegisterBus) {
        isOnResumeRegisterBus = onResumeRegisterBus;
        return this;
    }

    protected boolean isOnStartRegisterBus() {
        return isOnStartRegisterBus;
    }

    protected BaseToolbarFragment setOnStartRegisterBus(boolean onStartRegisterBus) {
        isOnStartRegisterBus = onStartRegisterBus;
        return this;
    }
    public <T> boolean checkNotNull(T reference) {
        if (reference == null) {
            return true;
        }
        return false;
    }

}

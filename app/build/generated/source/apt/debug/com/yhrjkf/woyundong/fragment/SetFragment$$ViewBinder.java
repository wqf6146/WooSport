// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SetFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.SetFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624204, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624204, "field 'mImgBack'");
    view = finder.findRequiredView(source, 2131624205, "field 'mRlYyBp'");
    target.mRlYyBp = finder.castView(view, 2131624205, "field 'mRlYyBp'");
    view = finder.findRequiredView(source, 2131624207, "field 'mTvVoiceReport'");
    target.mTvVoiceReport = finder.castView(view, 2131624207, "field 'mTvVoiceReport'");
    view = finder.findRequiredView(source, 2131624210, "field 'mBtnAutoPause'");
    target.mBtnAutoPause = finder.castView(view, 2131624210, "field 'mBtnAutoPause'");
    view = finder.findRequiredView(source, 2131624212, "field 'mTvResetPwd'");
    target.mTvResetPwd = finder.castView(view, 2131624212, "field 'mTvResetPwd'");
  }

  @Override public void unbind(T target) {
    target.mImgBack = null;
    target.mRlYyBp = null;
    target.mTvVoiceReport = null;
    target.mBtnAutoPause = null;
    target.mTvResetPwd = null;
  }
}

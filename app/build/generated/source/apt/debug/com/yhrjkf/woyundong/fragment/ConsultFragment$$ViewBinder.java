// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ConsultFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.ConsultFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624266, "field 'mWebView'");
    target.mWebView = finder.castView(view, 2131624266, "field 'mWebView'");
    view = finder.findRequiredView(source, 2131624264, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624264, "field 'mImgBack'");
    view = finder.findRequiredView(source, 2131624265, "field 'mTvTitle'");
    target.mTvTitle = finder.castView(view, 2131624265, "field 'mTvTitle'");
    view = finder.findRequiredView(source, 2131624263, "field 'mTopBar'");
    target.mTopBar = finder.castView(view, 2131624263, "field 'mTopBar'");
    view = finder.findRequiredView(source, 2131624267, "field 'mRlError'");
    target.mRlError = finder.castView(view, 2131624267, "field 'mRlError'");
  }

  @Override public void unbind(T target) {
    target.mWebView = null;
    target.mImgBack = null;
    target.mTvTitle = null;
    target.mTopBar = null;
    target.mRlError = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.LoginFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624392, "field 'mBgLayout'");
    target.mBgLayout = finder.castView(view, 2131624392, "field 'mBgLayout'");
    view = finder.findRequiredView(source, 2131624397, "field 'mTvCantLogin'");
    target.mTvCantLogin = finder.castView(view, 2131624397, "field 'mTvCantLogin'");
    view = finder.findRequiredView(source, 2131624398, "field 'tv_login_zhuce'");
    target.tv_login_zhuce = finder.castView(view, 2131624398, "field 'tv_login_zhuce'");
  }

  @Override public void unbind(T target) {
    target.mBgLayout = null;
    target.mTvCantLogin = null;
    target.tv_login_zhuce = null;
  }
}

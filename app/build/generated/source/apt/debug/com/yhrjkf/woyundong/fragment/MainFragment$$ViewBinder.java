// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.MainFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624106, "field 'mBottomBar'");
    target.mBottomBar = finder.castView(view, 2131624106, "field 'mBottomBar'");
  }

  @Override public void unbind(T target) {
    target.mBottomBar = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FindPdFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.FindPdFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624273, "field 'mTvNext'");
    target.mTvNext = finder.castView(view, 2131624273, "field 'mTvNext'");
    view = finder.findRequiredView(source, 2131624272, "field 'mEditText'");
    target.mEditText = finder.castView(view, 2131624272, "field 'mEditText'");
    view = finder.findRequiredView(source, 2131624270, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624270, "field 'mImgBack'");
  }

  @Override public void unbind(T target) {
    target.mTvNext = null;
    target.mEditText = null;
    target.mImgBack = null;
  }
}

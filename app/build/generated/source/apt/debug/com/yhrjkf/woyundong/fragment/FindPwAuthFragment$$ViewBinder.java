// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FindPwAuthFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.FindPwAuthFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624272, "field 'mEditPhone'");
    target.mEditPhone = finder.castView(view, 2131624272, "field 'mEditPhone'");
    view = finder.findRequiredView(source, 2131624273, "field 'mTvNext'");
    target.mTvNext = finder.castView(view, 2131624273, "field 'mTvNext'");
    view = finder.findRequiredView(source, 2131624274, "field 'mTvAuthStr'");
    target.mTvAuthStr = finder.castView(view, 2131624274, "field 'mTvAuthStr'");
    view = finder.findRequiredView(source, 2131624275, "field 'mBtnCountDown'");
    target.mBtnCountDown = finder.castView(view, 2131624275, "field 'mBtnCountDown'");
    view = finder.findRequiredView(source, 2131624270, "field 'mBtnBack'");
    target.mBtnBack = finder.castView(view, 2131624270, "field 'mBtnBack'");
  }

  @Override public void unbind(T target) {
    target.mEditPhone = null;
    target.mTvNext = null;
    target.mTvAuthStr = null;
    target.mBtnCountDown = null;
    target.mBtnBack = null;
  }
}

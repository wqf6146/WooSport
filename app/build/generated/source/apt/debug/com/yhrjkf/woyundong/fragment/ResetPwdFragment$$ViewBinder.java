// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ResetPwdFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.ResetPwdFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624353, "field 'mBtnCommit'");
    target.mBtnCommit = finder.castView(view, 2131624353, "field 'mBtnCommit'");
    view = finder.findRequiredView(source, 2131624351, "field 'mEditPwd'");
    target.mEditPwd = finder.castView(view, 2131624351, "field 'mEditPwd'");
    view = finder.findRequiredView(source, 2131624352, "field 'mEditPwd2'");
    target.mEditPwd2 = finder.castView(view, 2131624352, "field 'mEditPwd2'");
    view = finder.findRequiredView(source, 2131624350, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624350, "field 'mImgBack'");
  }

  @Override public void unbind(T target) {
    target.mBtnCommit = null;
    target.mEditPwd = null;
    target.mEditPwd2 = null;
    target.mImgBack = null;
  }
}

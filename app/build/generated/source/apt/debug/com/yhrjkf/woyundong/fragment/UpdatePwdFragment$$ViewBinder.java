// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UpdatePwdFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.UpdatePwdFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624353, "field 'mBtnCommit'");
    target.mBtnCommit = finder.castView(view, 2131624353, "field 'mBtnCommit'");
    view = finder.findRequiredView(source, 2131624377, "field 'mEditNewPwd'");
    target.mEditNewPwd = finder.castView(view, 2131624377, "field 'mEditNewPwd'");
    view = finder.findRequiredView(source, 2131624378, "field 'mEditNewPwd2'");
    target.mEditNewPwd2 = finder.castView(view, 2131624378, "field 'mEditNewPwd2'");
    view = finder.findRequiredView(source, 2131624376, "field 'mEditOldPwd'");
    target.mEditOldPwd = finder.castView(view, 2131624376, "field 'mEditOldPwd'");
    view = finder.findRequiredView(source, 2131624350, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624350, "field 'mImgBack'");
  }

  @Override public void unbind(T target) {
    target.mBtnCommit = null;
    target.mEditNewPwd = null;
    target.mEditNewPwd2 = null;
    target.mEditOldPwd = null;
    target.mImgBack = null;
  }
}

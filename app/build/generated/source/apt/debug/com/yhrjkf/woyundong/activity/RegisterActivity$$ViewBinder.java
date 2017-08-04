// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends com.yhrjkf.woyundong.activity.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624203, "field 'tv_register_back'");
    target.tv_register_back = finder.castView(view, 2131624203, "field 'tv_register_back'");
    view = finder.findRequiredView(source, 2131624202, "field 'tv_register_wancheng'");
    target.tv_register_wancheng = finder.castView(view, 2131624202, "field 'tv_register_wancheng'");
    view = finder.findRequiredView(source, 2131624197, "field 'ed_register_sjh'");
    target.ed_register_sjh = finder.castView(view, 2131624197, "field 'ed_register_sjh'");
    view = finder.findRequiredView(source, 2131624198, "field 'ed_register_mm'");
    target.ed_register_mm = finder.castView(view, 2131624198, "field 'ed_register_mm'");
    view = finder.findRequiredView(source, 2131624199, "field 'ed_register_zcmm'");
    target.ed_register_zcmm = finder.castView(view, 2131624199, "field 'ed_register_zcmm'");
    view = finder.findRequiredView(source, 2131624200, "field 'ed_register_yzm'");
    target.ed_register_yzm = finder.castView(view, 2131624200, "field 'ed_register_yzm'");
    view = finder.findRequiredView(source, 2131624201, "field 'mBtnYzm'");
    target.mBtnYzm = finder.castView(view, 2131624201, "field 'mBtnYzm'");
  }

  @Override public void unbind(T target) {
    target.tv_register_back = null;
    target.tv_register_wancheng = null;
    target.ed_register_sjh = null;
    target.ed_register_mm = null;
    target.ed_register_zcmm = null;
    target.ed_register_yzm = null;
    target.mBtnYzm = null;
  }
}

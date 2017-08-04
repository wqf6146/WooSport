// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HistoryMapActivity$$ViewBinder<T extends com.yhrjkf.woyundong.activity.HistoryMapActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624104, "field 'mMapView'");
    target.mMapView = finder.castView(view, 2131624104, "field 'mMapView'");
    view = finder.findRequiredView(source, 2131624102, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624102, "field 'mImgBack'");
  }

  @Override public void unbind(T target) {
    target.mMapView = null;
    target.mImgBack = null;
  }
}

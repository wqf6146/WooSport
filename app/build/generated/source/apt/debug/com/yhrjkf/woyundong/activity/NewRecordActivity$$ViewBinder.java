// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NewRecordActivity$$ViewBinder<T extends com.yhrjkf.woyundong.activity.NewRecordActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624174, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624174, "field 'mImgBack'");
    view = finder.findRequiredView(source, 2131624179, "field 'mTvTimeTime'");
    target.mTvTimeTime = finder.castView(view, 2131624179, "field 'mTvTimeTime'");
    view = finder.findRequiredView(source, 2131624180, "field 'mTvTimeDate'");
    target.mTvTimeDate = finder.castView(view, 2131624180, "field 'mTvTimeDate'");
    view = finder.findRequiredView(source, 2131624186, "field 'mTvDsDate'");
    target.mTvDsDate = finder.castView(view, 2131624186, "field 'mTvDsDate'");
    view = finder.findRequiredView(source, 2131624184, "field 'mTvDsDs'");
    target.mTvDsDs = finder.castView(view, 2131624184, "field 'mTvDsDs'");
    view = finder.findRequiredView(source, 2131624190, "field 'mTvFireCalorie'");
    target.mTvFireCalorie = finder.castView(view, 2131624190, "field 'mTvFireCalorie'");
    view = finder.findRequiredView(source, 2131624191, "field 'mTvFireDate'");
    target.mTvFireDate = finder.castView(view, 2131624191, "field 'mTvFireDate'");
    view = finder.findRequiredView(source, 2131624196, "field 'mTvSpeedDate'");
    target.mTvSpeedDate = finder.castView(view, 2131624196, "field 'mTvSpeedDate'");
    view = finder.findRequiredView(source, 2131624195, "field 'mTvSpeedDs'");
    target.mTvSpeedDs = finder.castView(view, 2131624195, "field 'mTvSpeedDs'");
  }

  @Override public void unbind(T target) {
    target.mImgBack = null;
    target.mTvTimeTime = null;
    target.mTvTimeDate = null;
    target.mTvDsDate = null;
    target.mTvDsDs = null;
    target.mTvFireCalorie = null;
    target.mTvFireDate = null;
    target.mTvSpeedDate = null;
    target.mTvSpeedDs = null;
  }
}

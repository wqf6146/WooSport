// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NewHistoryFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.NewHistoryFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624270, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624270, "field 'mImgBack'");
    view = finder.findRequiredView(source, 2131624277, "field 'mCalendarView'");
    target.mCalendarView = finder.castView(view, 2131624277, "field 'mCalendarView'");
    view = finder.findRequiredView(source, 2131624279, "field 'mTvSportTimes'");
    target.mTvSportTimes = finder.castView(view, 2131624279, "field 'mTvSportTimes'");
    view = finder.findRequiredView(source, 2131624280, "field 'mTvSportHour'");
    target.mTvSportHour = finder.castView(view, 2131624280, "field 'mTvSportHour'");
    view = finder.findRequiredView(source, 2131624281, "field 'mTvSportDistance'");
    target.mTvSportDistance = finder.castView(view, 2131624281, "field 'mTvSportDistance'");
    view = finder.findRequiredView(source, 2131624282, "field 'mTvSportCarloris'");
    target.mTvSportCarloris = finder.castView(view, 2131624282, "field 'mTvSportCarloris'");
    view = finder.findRequiredView(source, 2131624284, "field 'mSportChart'");
    target.mSportChart = finder.castView(view, 2131624284, "field 'mSportChart'");
    view = finder.findRequiredView(source, 2131624276, "field 'mTvMonth'");
    target.mTvMonth = finder.castView(view, 2131624276, "field 'mTvMonth'");
    view = finder.findRequiredView(source, 2131624283, "field 'mChartTitle'");
    target.mChartTitle = finder.castView(view, 2131624283, "field 'mChartTitle'");
    view = finder.findRequiredView(source, 2131624278, "field 'mBgTip'");
    target.mBgTip = finder.castView(view, 2131624278, "field 'mBgTip'");
  }

  @Override public void unbind(T target) {
    target.mImgBack = null;
    target.mCalendarView = null;
    target.mTvSportTimes = null;
    target.mTvSportHour = null;
    target.mTvSportDistance = null;
    target.mTvSportCarloris = null;
    target.mSportChart = null;
    target.mTvMonth = null;
    target.mChartTitle = null;
    target.mBgTip = null;
  }
}

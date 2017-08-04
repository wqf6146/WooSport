// Generated code from Butter Knife. Do not modify!
package com.yhrjkf.woyundong.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HistoryDetailFragment$$ViewBinder<T extends com.yhrjkf.woyundong.fragment.HistoryDetailFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624285, "field 'mImgBack'");
    target.mImgBack = finder.castView(view, 2131624285, "field 'mImgBack'");
    view = finder.findRequiredView(source, 2131624287, "field 'mTvDate'");
    target.mTvDate = finder.castView(view, 2131624287, "field 'mTvDate'");
    view = finder.findRequiredView(source, 2131624288, "field 'mListView'");
    target.mListView = finder.castView(view, 2131624288, "field 'mListView'");
  }

  @Override public void unbind(T target) {
    target.mImgBack = null;
    target.mTvDate = null;
    target.mListView = null;
  }
}

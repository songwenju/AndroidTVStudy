package com.songwenju.androidtvstudy.ui.presenter;

import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;

/**
 * songwenju on 17-4-13 : 17 : 46.
 * 邮箱：songwenju@outlook.com
 */

public class CustomFullWidthDetailsOverviewRowPresenter extends FullWidthDetailsOverviewRowPresenter {

    public CustomFullWidthDetailsOverviewRowPresenter(Presenter presenter) {
        super(presenter);
    }
    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        this.setState((ViewHolder) holder, FullWidthDetailsOverviewRowPresenter.STATE_SMALL);
    }
}

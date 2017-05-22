package com.songwenju.androidtvstudy;


import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

/**
 * songwenju on 17-4-13 : 17 : 47.
 * 邮箱：songwenju@outlook.com
 */

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;

        if (movie != null) {
            viewHolder.getTitle().setText(movie.getTitle());
            viewHolder.getSubtitle().setText(movie.getStudio());
            viewHolder.getBody().setText(movie.getDescription());
        }

    }
}

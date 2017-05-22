package com.songwenju.androidtvstudy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;

import com.songwenju.androidtools.util.LogUtil;
import com.songwenju.androidtvstudy.util.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URI;

/**
 * songwenju on 17-3-29 : 10 : 46.
 * 邮箱：songwenju@outlook.com
 */

public class CardPresenter extends Presenter {
    private static Context mContext;
    private static int CARD_WIDTH = 313;
    private static int CARD_HEIGHT = 176;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        LogUtil.i(this, "CardPresenter.onCreateViewHolder.");
        mContext = parent.getContext();

        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setCardType(BaseCardView.CARD_TYPE_INFO_UNDER_WITH_EXTRA);
        cardView.setInfoVisibility(BaseCardView.CARD_REGION_VISIBLE_SELECTED);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(mContext.getResources().getColor(R.color.fastlane_background));
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        LogUtil.i(this, "CardPresenter.onBindViewHolder.");
        Movie movie = (Movie) item;
        ((ViewHolder) viewHolder).setMovie(movie);

        ((ViewHolder) viewHolder).mCardView.setTitleText(movie.getTitle());
        ((ViewHolder) viewHolder).mCardView.setContentText(movie.getStudio());
        ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
//        ((ViewHolder) viewHolder).mCardView.setMainImage(((ViewHolder) viewHolder).getDefaultCardView());
        ((ViewHolder) viewHolder).updateCardImage(movie.getCardImageURI());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        LogUtil.i(this, "CardPresenter.onUnbindViewHolder.");
    }

    @Override
    public void onViewAttachedToWindow(Presenter.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

     static class ViewHolder extends Presenter.ViewHolder {
        private Movie mMovie;
        private ImageCardView mCardView;
        private Drawable mDefaultCardView;
        private PicassoImageCardViewTarget mImageCardViewTarget;

        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView, mContext);
            mDefaultCardView = mContext.getResources().getDrawable(R.drawable.movie);

        }


        public Movie getMovie() {
            return mMovie;
        }

        public void setMovie(Movie movie) {
            mMovie = movie;
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        public Drawable getDefaultCardView() {
            return mDefaultCardView;
        }

        protected void updateCardImage(URI uri) {
            Picasso.with(mContext)
                    .load(uri.toString())
                    .resize(Utils.convertDpToPixel(mContext, CARD_WIDTH),
                            Utils.convertDpToPixel(mContext, CARD_HEIGHT))
                    .error(mDefaultCardView)
                    .into(mImageCardViewTarget);
        }
    }

    public static class PicassoImageCardViewTarget implements Target {
        private ImageCardView mImageCardView;
        private Context mContext;

        public PicassoImageCardViewTarget(ImageCardView imageCardView, Context context) {
            mImageCardView = imageCardView;
            mContext = context;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            mImageCardView.setMainImage(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            // Do nothing, default_background manager has its own transitions
        }
    }
}

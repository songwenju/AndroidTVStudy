package com.songwenju.androidtvstudy.ui.background;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;

import com.songwenju.androidtvstudy.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * songwenju on 17-4-10 : 10 : 15.
 * 邮箱：songwenju@outlook.com
 */

public class PicassoBackgroundManager {
    public static final int DEFAULT_BACKGROUND_RES_ID = R.drawable.default_background;

    public static final int BACKGROUND_DELAY = 500;
    private static Drawable mDefaultBackground;
    private Activity mActivity;
    private BackgroundManager mBackgroundManager;

    //Handler attached with main thread
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private DisplayMetrics mMetrics;
    private URI mBackgroundURI;
    private PicassoBackgroundManagerTarget mBackgroundManagerTarget;
    Timer mBackgroundTimer;   // null when no UpdateBackgroundTask is running.

    public PicassoBackgroundManager(Activity activity) {
        mActivity = activity;
        mDefaultBackground = activity.getDrawable(DEFAULT_BACKGROUND_RES_ID);
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        mBackgroundManagerTarget = new PicassoBackgroundManagerTarget(mBackgroundManager);
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(mMetrics);
    }

    private void startBackgroundTimer() {
        if (mBackgroundTimer != null) {
            mBackgroundTimer.cancel();
        }

        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_DELAY);
    }

    /**
     * update backgroud to default
     */
    public void updateBackgroundWithDelay() {
        mBackgroundURI = null;
        startBackgroundTimer();
    }


    public void updateBackgroundWithDelay(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
            updateBackground(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void updateBackgroundWithDelay(URI uri) {
       mBackgroundURI = uri;
        startBackgroundTimer();
    }

    public void updateBackground(URI uri) {
        Picasso.with(mActivity)
                .load(uri.toString())
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .centerCrop()
                .error(mDefaultBackground)
                .into(mBackgroundManagerTarget);
    }



    /**
     * Copied from AOSP sample code.
     * Inner class
     * Picasso target for updating default_background images
     */
    public class PicassoBackgroundManagerTarget implements Target {
        BackgroundManager mBackgroundManager;

        public PicassoBackgroundManagerTarget(BackgroundManager backgroundManager) {
            this.mBackgroundManager = backgroundManager;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            this.mBackgroundManager.setBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            this.mBackgroundManager.setDrawable(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            // Do nothing, default_background manager has its own transitions
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            PicassoBackgroundManagerTarget that = (PicassoBackgroundManagerTarget) o;

            if (!mBackgroundManager.equals(that.mBackgroundManager))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mBackgroundManager.hashCode();
        }
    }


    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI);
                    }
                }
            });
        }
    }
}
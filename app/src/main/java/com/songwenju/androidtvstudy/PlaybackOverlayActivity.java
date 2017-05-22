package com.songwenju.androidtvstudy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.VideoView;

import com.songwenju.androidtools.util.LogUtil;

import java.util.ArrayList;

public class PlaybackOverlayActivity extends FragmentActivity {

    private VideoView mVideoView;
    private ArrayList<Movie> mItems = new ArrayList<Movie>();
    private PlaybackController mPlaybackController;

    private Movie mSelectedMovie;
    private int mCurrentItem;

    public PlaybackController getPlaybackController() {
        return mPlaybackController;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.i(this,"PlaybackOverlayActivity.onCreate.");
        super.onCreate(savedInstanceState);

        /* NOTE: setMediaController (in createMediaSession) must be executed
         * BEFORE inflating Fragment!
         */
        mPlaybackController = new PlaybackController(this);

        mItems = MovieProvider.getMovieItems();
        mSelectedMovie = (Movie) getIntent().getSerializableExtra(DetailsActivity.MOVIE);
        //mSelectedMovie = (Movie) getIntent().getExtras().getSerializable(DetailsActivity.MOVIE);
        mCurrentItem = (int) mSelectedMovie.getId() - 1;
        mPlaybackController.setCurrentItem(mCurrentItem);

        setContentView(R.layout.activity_playback_overlay);
        mVideoView = (VideoView) findViewById(R.id.video_View);
        mPlaybackController.setVideoView(mVideoView);
        mPlaybackController.setMovie(mSelectedMovie); // it must after video view setting
        loadViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlaybackController.finishPlayback();
    }

    private void loadViews() {
        mVideoView = (VideoView) findViewById(R.id.video_View);
        mVideoView.setFocusable(false);
        mVideoView.setFocusableInTouchMode(false);

        mPlaybackController.setVideoPath(mSelectedMovie.getVideoUrl());

    }

    @Override
    public void onPause() {
        super.onPause();
        if (!requestVisibleBehind(true)) {
            // Try to play behind launcher, but if it fails, stop playback.
            mPlaybackController.playPause(false);
        }
    }


}

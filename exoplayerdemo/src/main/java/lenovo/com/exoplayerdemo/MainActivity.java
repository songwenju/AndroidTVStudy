package lenovo.com.exoplayerdemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity {
    Button btn, btn2;
    SimpleExoPlayerView view;
    SimpleExoPlayer mExoPlayer;
    Uri mp4VideoUri = Uri.parse("http://cord.tvxio.com/v1_0/I2/frk/api/live/m3u8/6/e2c1ca0f-d8b7-4d0e-99b7-169f4ae8f8ab/medium/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove titlebar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Remove notificationbar
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        view = (SimpleExoPlayerView) findViewById(R.id.view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creteAPlayer();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
    }

    private void playVideo() {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, Util.getUserAgent(MainActivity.this, "snow"), bandwidthMeter);
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(mp4VideoUri, dataSourceFactory, extractorsFactory, null, null);        // Prepare the player with the source.
        mExoPlayer.prepare(videoSource);
        mExoPlayer.setPlayWhenReady(true);
    }



    private void creteAPlayer() {
        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the player
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        view.setPlayer(player);
        ArrayList a = new ArrayList();
        Iterator i = a.iterator();
    }
}
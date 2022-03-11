package com.rezwan502.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity {

    // initialize variable
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btFullScreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign variable
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        btFullScreen = findViewById(R.id.bt_fullscreen);

        // make activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // video url
        Uri videoUrl = Uri.parse("https://i.imgur.com/7bMqysJ.mp4");

        // Initialize load control
        LoadControl loadControl = new DefaultLoadControl();
        // Initialize Band width meter
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Initialize track selector
        TrackSelector trackSelector = new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );
        // Initialize simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(MainActivity.this, trackSelector, loadControl);
        // Initialize data source factory
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
        // Initialize extractor factory
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // Initialize media source
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);
        // set player
        playerView.setPlayer(simpleExoPlayer);
        //keep screen on
        playerView.setKeepScreenOn(true);
        // prepare media
        simpleExoPlayer.prepare(mediaSource);
        // play video when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                // check condition
                if(playbackState == Player.STATE_BUFFERING){
                    // when buffering
                    // show progress bar
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    // when ready
                    // hide progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check condition
                if(flag){
                    // when flag is true
                    // set enter full screen image
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    // set portrait orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                }else{
                    // when flag is false
                    // set exit full screen
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    flag = true;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop video when ready
        simpleExoPlayer.setPlayWhenReady(false);
        // GetPlayback state
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // stop video when ready
        simpleExoPlayer.setPlayWhenReady(true);
        // GetPlayback state
        simpleExoPlayer.getPlaybackState();
    }
}
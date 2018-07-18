package com.anvesh.saranamayyappa.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.utils.Config;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView playerView;
    String videoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        videoId = getIntent().getStringExtra("VIDEO_URL");

        playerView = (YouTubePlayerView) findViewById(R.id.player_view);

        // initializes the YouTube player view
        playerView.initialize(Config.API_KEY, this);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
// shows dialog if user interaction may fix the error
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 0).show();
        } else {
            // displays the error occured during the initialization<br />
           /* String error = String.format(
                    getString(R.string.error_string), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();*/
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            // prepares the player to play the video and loads its thumbnail in player<br />
            youTubePlayer.loadVideo(videoId/*Config.VIDEO_CODE*/);
            // sets player style which in return impacts the level of control<br />
            // user has over the player<br />
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

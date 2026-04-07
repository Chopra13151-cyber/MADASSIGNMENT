package com.example.mad_assg_2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaPlayer mediaPlayer;
    private TextView tvStatus;
    private boolean isVideoActive = false;

    // Sample Video URL (Ensure internet permission in Manifest)
    private String videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        tvStatus = findViewById(R.id.tvStatus);

        // a) Open Audio from local (Place a file named 'music.mp3' in res/raw)
        findViewById(R.id.btnOpenAudio).setOnClickListener(v -> {
            stopAll();
            isVideoActive = false;
            // 'music' refers to res/raw/music.mp3
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            tvStatus.setText("Status: Audio Loaded");
        });

        // b) Open Video from URL
        findViewById(R.id.btnOpenURL).setOnClickListener(v -> {
            stopAll();
            isVideoActive = true;
            videoView.setVideoPath(videoUrl);
            tvStatus.setText("Status: Video Buffering...");
            videoView.setOnPreparedListener(mp -> tvStatus.setText("Status: Video Ready"));
        });

        // Play Button
        findViewById(R.id.btnPlay).setOnClickListener(v -> {
            if (isVideoActive) videoView.start();
            else if (mediaPlayer != null) mediaPlayer.start();
            tvStatus.setText("Status: Playing");
        });

        // Pause Button
        findViewById(R.id.btnPause).setOnClickListener(v -> {
            if (isVideoActive) videoView.pause();
            else if (mediaPlayer != null) mediaPlayer.pause();
            tvStatus.setText("Status: Paused");
        });

        // Stop Button
        findViewById(R.id.btnStop).setOnClickListener(v -> stopAll());

        // Restart Button
        findViewById(R.id.btnRestart).setOnClickListener(v -> {
            if (isVideoActive) {
                videoView.resume();
                videoView.start();
            } else if (mediaPlayer != null) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
            tvStatus.setText("Status: Restarted");
        });
    }

    private void stopAll() {
        if (videoView.isPlaying()) videoView.stopPlayback();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        tvStatus.setText("Status: Stopped");
    }
}
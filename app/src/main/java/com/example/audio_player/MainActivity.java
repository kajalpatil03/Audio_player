package com.example.audio_player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button play, pause, stop;
    MediaPlayer mediaPlayer;
    View waveView;  // Reference to the wave view
    Animation waveAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        waveView = findViewById(R.id.wave_view);  // Find wave view by its ID
        waveAnimation = AnimationUtils.loadAnimation(this, R.anim.wave_animation);  // Load animation

        // Initialize MediaPlayer with your audio file
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    waveView.setVisibility(View.VISIBLE);  // Show wave view
                    waveView.startAnimation(waveAnimation);  // Start animation
                    Toast.makeText(MainActivity.this, "Audio is playing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    waveView.clearAnimation();  // Stop animation
                    waveView.setVisibility(View.GONE);  // Hide wave view
                    Toast.makeText(MainActivity.this, "Audio is paused", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.audio);  // Prepare again after stopping
                    waveView.clearAnimation();  // Stop animation
                    waveView.setVisibility(View.GONE);  // Hide wave view
                    Toast.makeText(MainActivity.this, "Music stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}

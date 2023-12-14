package com.example.drawingtest;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Options extends AppCompatActivity {

    private AudioManager audioManager;
    private MediaPlayer Music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        SeekBar volumeSlider = findViewById(R.id.volumeSlider);
        Button websiteButton = findViewById(R.id.websiteButton);
        TextView volumeLabel = findViewById(R.id.volumeLabel);


        TextView sfxLabel = findViewById(R.id.sfxLabel);
        SeekBar sfxSlider = findViewById(R.id.sfxSlider);


        Music = MediaPlayer.create(Options.this, R.raw.settin);
        Music.setLooping(true);
        Music.start();







        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Set the maximum volume of the system
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumeSlider.setMax(maxVolume);

        // Set the initial progress of the volume slider
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeSlider.setProgress(currentVolume);

        // Set a listener for the volume slider
        volumeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Adjust the system volume when the user changes the slider
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Handle when the user starts touching the volume slider
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Handle when the user stops touching the volume slider
            }
        });



        // Set a listener for the website button
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a website link
                openWebsite("https://github.com/Trephlosion");
            }
        });
    }

    // Function to open a website link
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources when the activity is destroyed
        if (Music != null) {
            Music.release();
            Music = null;
        }
    }

}

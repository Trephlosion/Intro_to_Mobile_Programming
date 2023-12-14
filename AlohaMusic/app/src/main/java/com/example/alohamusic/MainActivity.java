package com.example.alohamusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int playing;

    MediaPlayer mpDrums;

    MediaPlayer mpUkulele;

    Button buttonDrums;
    Button buttonUkulele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

       buttonDrums = findViewById(R.id.btDrums);
       buttonUkulele = findViewById(R.id.btUkulele);

       buttonDrums.setOnClickListener(Drums);
       buttonUkulele.setOnClickListener(Ukulele);

       mpDrums = new MediaPlayer();
       mpDrums = MediaPlayer.create(this, R.raw.drums);

       mpUkulele = new MediaPlayer();
       mpUkulele = MediaPlayer.create(this, R.raw.ukulele);

        playing = 0;

    }

    Button.OnClickListener Drums = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(playing){
                case 0:
                    mpDrums.start();
                    playing = 1;
                    buttonDrums.setText("Pause Drums Song");
                    buttonUkulele.setVisibility(View.INVISIBLE);
                    break;

                case 1:
                    mpDrums.pause();
                    playing = 0;
                    buttonDrums.setText("Play Drums Song");
                    buttonUkulele.setVisibility(View.VISIBLE);
                    break;


            }

        }
    };

    Button.OnClickListener Ukulele = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(playing){
                case 0:
                    mpUkulele.start();
                    playing = 1;
                    buttonUkulele.setText("Pause Ukulele Song");
                    buttonDrums.setVisibility(View.INVISIBLE);
                    break;

                case 1:
                    mpUkulele.pause();
                    playing = 0;
                    buttonUkulele.setText("Play Ukulele Song");
                    buttonDrums.setVisibility(View.VISIBLE);
                    break;


            }


        }
    };


}

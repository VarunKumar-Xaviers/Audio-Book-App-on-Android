package com.varuncollegeproject.audiobook;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Book1Chapter1audio extends AppCompatActivity {
    ImageButton Playaudio;
    ImageButton Pauseaudio;
    ImageButton ListChapters;
    MediaPlayer mp;
    SeekBar Audioprog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book1_chapter1audio);

        //        Link Java and XML
        Playaudio = findViewById(R.id.playaudio);
        Pauseaudio = findViewById(R.id.pauseaudio);
        ListChapters = findViewById(R.id.ListChapters);
        Audioprog = findViewById(R.id.audioprog);


        Playaudio.setOnClickListener(v -> {
            Vibrate();
            mp.start();

//          SeekBar Code
            Audioprog.setMax(mp.getDuration());

            AutoMoveSeekBar();
        });
        Pauseaudio.setOnClickListener(v -> {

            Vibrate();
            if (mp != null) {
                mp.pause();
            }
        });
        ListChapters.setOnClickListener(v -> {
            Vibrate();
            playlist();
        });
    }

    public void CreateAudio() {
        //Play Audio
        if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.dc_title);
        }
    }

    public void AutoMoveSeekBar() {
        //        Auto change SeekBar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Audioprog.setProgress(mp.getCurrentPosition());
            }
        }, 0, 10);

        Audioprog.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void Vibrate() {

        //Vibrate on click
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
//    Perform for API 26 and below
            vibrator.vibrate(200);
        }
    }

    public void playlist() {
        Intent MovebackIntent = new Intent(this, Book1Chapters.class);
        startActivity(MovebackIntent);
    }
    private void stopaudio() {
        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
            mp = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        CreateAudio();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopaudio();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CreateAudio();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopaudio();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CreateAudio();
    }
}
package com.vishnu.myapplication;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private VideoView avideoView;
    private Button abutton;
    private MediaController media;
    private MediaPlayer audio;
    private Button play;
    private Button pause;
    private SeekBar seek;
    private AudioManager man;
    private SeekBar s;
    private Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        avideoView=findViewById(R.id.videoView);
        s=findViewById(R.id.seekBar2);
        abutton=findViewById(R.id.button);
        play=findViewById(R.id.button2);
        pause=findViewById(R.id.button3);
        audio=MediaPlayer.create(this,R.raw.aud);
        seek=findViewById(R.id.seekBar);
        man=(AudioManager) getSystemService(AUDIO_SERVICE);
        int max=man.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int cur=man.getStreamVolume(AudioManager.STREAM_MUSIC);
        final int m=audio.getDuration();
        seek.setMax(max);
        seek.setProgress(cur);
        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audio.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                audio.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audio.start();
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    man.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.start();
                t=new Timer();
                t.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        s.setProgress(audio.getCurrentPosition());
                        s.setMax(m);
                    }
                },0,1000);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.pause();
                t.cancel();
            }
        });
        abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
                avideoView.setVideoURI(u);
                avideoView.start();
                avideoView.setMediaController(media);
                media.setAnchorView(avideoView);
            }
        });
        media=new MediaController(this);
    }
}

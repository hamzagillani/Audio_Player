package com.e.a.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
        MediaPlayer mediaPlayer;
        SeekBar seekBar;
   SeekBar control_seekbar;
        TextView vol_counter,startTime,durationTime;
        AudioManager audioManager;
        Button back,frw;
    private static int oTime =0, sTime =0, eTime =0, fTime = 5000, bTime = 5000;
    private Handler hdlr = new Handler();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekbae_id);
        vol_counter = findViewById(R.id.vouloe_value_id);
        startTime=findViewById(R.id.textStartTime_id);
        durationTime=findViewById(R.id.textSongTime_id);
        back=findViewById(R.id.back_id);
        frw=findViewById(R.id.frwd_id);


        mediaPlayer=MediaPlayer.create(this,R.raw.sounds_1);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final int maxVolumControol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolum = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar.setMax(maxVolumControol);
        seekBar.setProgress(curVolum);

       control_seekbar = findViewById(R.id.seekbar2_id);
        control_seekbar.setMax(mediaPlayer.getDuration());

/*
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                try{
                    startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
                            TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))) );
                    control_seekbar.setProgress(sTime);
                    hdlr.postDelayed(this, 100);
                    vol_counter.setText((mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100);

                }catch (Exception e){

                }



                control_seekbar.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,100);*/
       /* control_seekbar.setMax(mediaPlayer.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                control_seekbar.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,100);*/

        control_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Tag",Integer.toString(progress));
                mediaPlayer.seekTo(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Tag2",Integer.toString(progress));
                vol_counter.setText(Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        frw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime+fTime)<=eTime){
                    sTime=sTime+fTime;
                    mediaPlayer.seekTo(sTime);
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sTime+bTime)>0){

                    sTime=sTime-bTime;
                    mediaPlayer.seekTo(sTime);
                }

            }
        });


    }

    public void play(View view) {

      mediaPlayer.start();

      eTime=mediaPlayer.getDuration();
      sTime=mediaPlayer.getCurrentPosition();
      if (oTime==0){
          oTime=1;
      }
durationTime.setText(String.format("%d min,%d sec",
        TimeUnit.MILLISECONDS.toMinutes(eTime),TimeUnit.MILLISECONDS.toSeconds(eTime) -
        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(eTime))));

        startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
                TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(sTime))) );

        control_seekbar.setProgress(sTime);
        hdlr.postDelayed(UpdateSongTime,100);


    }

    public void pause(View view) {
        mediaPlayer.pause();
    }

    private Runnable UpdateSongTime=new Runnable() {
        @Override
        public void run() {
            sTime = mediaPlayer.getCurrentPosition();
            startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))) );
            control_seekbar.setProgress(sTime);
            hdlr.postDelayed(this, 100);

        }
    };
}

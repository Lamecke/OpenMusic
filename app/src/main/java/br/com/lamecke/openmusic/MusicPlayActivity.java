package br.com.lamecke.openmusic;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayActivity extends AppCompatActivity {

    TextView titleView;
    SeekBar seekBar;
    Button btn_pausePlay, btn_next,btn_prev;
    ArrayList<AudioClip> songsList;
    AudioClip currentClip;

    MediaPlayer mediaPlayer = MyMediaPlay.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);

        titleView = findViewById(R.id.song_title_id);
        titleView.setSelected(true);

        seekBar = findViewById(R.id.sekbar_time_id);

        btn_pausePlay= findViewById(R.id.button_play_pause_id);
        btn_next = findViewById(R.id.button_next_id);
        btn_prev = findViewById(R.id.button_prev_id);

        songsList = (ArrayList<AudioClip>) getIntent().getSerializableExtra("LIST");

        setResourcesWithMusic();

        MusicPlayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!= null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    if (mediaPlayer.isPlaying()){
                        btn_pausePlay.setBackground((getDrawable(R.drawable.baseline_pause_circle_24)));
                    }else {
                        btn_pausePlay.setBackground((getDrawable(R.drawable.baseline_play_circle_24)));
                    }


                }
                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress);
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
    private void setResourcesWithMusic(){
        currentClip = songsList.get(MyMediaPlay.currentIndex);
        titleView.setText(currentClip.getTitle());

        btn_pausePlay.setOnClickListener(b->playPause());
        btn_next.setOnClickListener(b->nextSong());
        btn_prev.setOnClickListener(b->prevSong());

        playMusic();


    }
    private void playMusic(){

        mediaPlayer.reset();
        seekBar.setProgress(0);
        try {
            mediaPlayer.setDataSource(currentClip.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();

            seekBar.setMax(mediaPlayer.getDuration());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    private void playPause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else {
            mediaPlayer.start();
        }

    }
    private void nextSong(){

        if (MyMediaPlay.currentIndex == songsList.size()-1)
            return;
        MyMediaPlay.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }
    private void prevSong(){
        if (MyMediaPlay.currentIndex == 0)
            return;
        MyMediaPlay.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }
}
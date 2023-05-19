package br.com.lamecke.openmusic;

import android.media.MediaPlayer;

public class MyMediaPlay {
    static MediaPlayer instance;
    public static MediaPlayer getInstance(){
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static int currentIndex = -1;

}

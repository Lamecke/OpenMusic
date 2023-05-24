package br.com.lamecke.openmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    TextView textView;
    ArrayList<AudioClip> songList = new ArrayList<>();

    private static final int READ_MEDIA_AUDIO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.Recycler_Music);
        textView = findViewById(R.id.NoSongs_Text);

        checkPermission();

        String[] projection ={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" !=0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);

        while (cursor.moveToNext()){
            AudioClip songData = new AudioClip(cursor.getString(1),cursor.getString(0),cursor.getString(2));

            if(new File(songData.getPath()).exists())
                songList.add(songData);
        }

        if (songList.size() == 0){

            textView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new Music_Adpter(songList,getApplicationContext()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this,"Request Permitted",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"Request Missed",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_MEDIA_AUDIO }, READ_MEDIA_AUDIO);
        }
    }
}

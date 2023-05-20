package br.com.lamecke.openmusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Music_Adpter extends RecyclerView.Adapter<Music_Adpter.ViewHold> {

    ArrayList<AudioClip> songsList;
    Context context;

    public Music_Adpter(ArrayList<AudioClip> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.music_item,parent,false);
        return new Music_Adpter.ViewHold(v);


    }

    @Override
    public void onBindViewHolder(Music_Adpter.ViewHold viewHold,final int position) {
        AudioClip songData = songsList.get(position);
        viewHold.textView_Music_Title.setText(songData.getTitle());
        viewHold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMediaPlay.getInstance().reset();
                MyMediaPlay.currentIndex = position;

                Intent intent = new Intent(context, MusicPlayActivity.class);
                intent.putExtra("LIST",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder{
        TextView textView_Music_Title;
        ImageView imageView_Icon_Item;
        public ViewHold(View itemView) {
            super(itemView);

            textView_Music_Title = itemView.findViewById(R.id.text_view_title_id);
            imageView_Icon_Item = itemView.findViewById(R.id.image_icon_item_id);
        }
    }
}

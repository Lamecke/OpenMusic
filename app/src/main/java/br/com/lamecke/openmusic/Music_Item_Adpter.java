package br.com.lamecke.openmusic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Music_Item_Adpter  extends RecyclerView.Adapter<Music_Item_Adpter.ViewHold> {

    ArrayList<AudioClip> songsList;
    Context context;

    public Music_Item_Adpter(ArrayList<AudioClip> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.music_item,parent,false);
        return new Music_Item_Adpter.ViewHold(v);
    }

    @Override
    public void onBindViewHolder( Music_Item_Adpter.ViewHold  holder, int position) {
        AudioClip songData = songsList.get(position);
        holder.textView_Music_Title.setText(songData.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

package com.tuan.apmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.tuan.apmusic.Model.PlayListSong;
import com.tuan.apmusic.Model.Song;
import com.tuan.apmusic.R;

public class PlaySongAdapter extends RecyclerView.Adapter<PlaySongAdapter.ViewHolder> {

    View view;
    Context context;
    ArrayList<Song> songArrayList;

    public PlaySongAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.part_play_songs,parent, false);
        return new PlaySongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);

        if (song == null) {
            return;
        }
        holder.textNameSong.setText(song.getNameSong());
        holder.textIndex.setText(position + 1 + "");
        holder.textNameArticle.setText(song.getArtists());
    }

    @Override
    public int getItemCount() {
        return songArrayList != null ? songArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textIndex, textNameSong, textNameArticle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIndex = itemView.findViewById(R.id.textPlaySongIndex);
            textNameSong = itemView.findViewById(R.id.textPlaySongName);
            textNameArticle = itemView.findViewById(R.id.txtNameArticleOneSong);
        }
    }
}


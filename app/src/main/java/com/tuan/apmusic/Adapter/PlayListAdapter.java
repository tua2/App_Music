package com.tuan.apmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.tuan.apmusic.Activity.ListSongActivity;
import com.tuan.apmusic.Model.PlayListSong;
import com.tuan.apmusic.R;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {
    View view;
    Context context;
    ArrayList<PlayListSong> arrayListPlayList;

    public PlayListAdapter(Context context, ArrayList<PlayListSong> arrayListPlayList) {
        this.context = context;
        this.arrayListPlayList = arrayListPlayList;
    }

    @NonNull
    @Override
    public PlayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.part_playlist,parent, false);
        return new PlayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListAdapter.ViewHolder holder, int position) {
        PlayListSong playList = arrayListPlayList.get(position);
        if (playList == null) {
            return;
        }
        holder.textPlayList.setText(playList.getName());
        Picasso.get().load(playList.getImage()).into(holder.imagePlayList);

        view.setOnClickListener(view -> {
            Intent intent = new Intent(context, ListSongActivity.class);
            intent.putExtra("playlist", arrayListPlayList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayListPlayList != null ? arrayListPlayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textPlayList;
        ImageView imagePlayList;
        public ViewHolder(@NonNull View view) {
            super(view);
            imagePlayList = view.findViewById(R.id.imageViewPlayList);
            textPlayList = view.findViewById(R.id.textViewPlayList);
        }
    }
}
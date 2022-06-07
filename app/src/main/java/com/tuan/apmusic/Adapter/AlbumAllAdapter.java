package com.tuan.apmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.tuan.apmusic.Activity.ListSongActivity;
import com.tuan.apmusic.Model.Charts;
import com.tuan.apmusic.Model.Singer;
import com.tuan.apmusic.R;

public class AlbumAllAdapter extends RecyclerView.Adapter<AlbumAllAdapter.ViewHolder> {

    View view;
    Context context;
    ArrayList<Singer> singerArrayList;
    Singer singer;

    public AlbumAllAdapter(Context context , ArrayList<Singer> chartsArrayList) {
        this.context = context;
        this.singerArrayList = chartsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.part_album_fragment,parent, false);
        return new AlbumAllAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        singer = singerArrayList.get(position);
        if ( singer == null) {
            return;
        }
        holder.textTitleAllList.setText(singer.getName());
        holder.textArticleAllList.setText(singer.getArtistsName());
        Glide.with(context).load(singer.getThumbnail()).into(holder.imageAllList);

        view.setOnClickListener((View view) -> {
            Intent intent = new Intent(context, ListSongActivity.class);
            intent.putExtra("listSongOfSinger", singerArrayList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return singerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAllList;
        TextView textTitleAllList, textArticleAllList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAllList = itemView.findViewById(R.id.imageViewAllList);
            textTitleAllList = itemView.findViewById(R.id.textTitleAllList);
            textArticleAllList = itemView.findViewById(R.id.textArticleAllList);
        }
    }
}

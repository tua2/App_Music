package com.tuan.apmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.tuan.apmusic.Activity.PlaySongActivity;
import com.tuan.apmusic.Model.Song;
import com.tuan.apmusic.R;

public class SearchSongsAdapter extends RecyclerView.Adapter<SearchSongsAdapter.ViewHolder> {

    View view;
    Context context;
    ArrayList<Song> songArrayList;
    Song song;

    public SearchSongsAdapter(Context context, ArrayList<Song> songArrayList){
        this.context = context;
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.part_search,parent, false);
        return new SearchSongsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        song = songArrayList.get(position);
        if ( song == null) {
            return;
        }
        holder.textArticleSearch.setText(song.getArtists());
        holder.textViewTitleSearch.setText(song.getNameSong());
        Picasso.get().load(song.getThumbnail()).into(holder.imageViewSearch);
    }

    @Override
    public int getItemCount() {
        return songArrayList != null ? songArrayList.size() : 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSearch;
        TextView textViewTitleSearch, textArticleSearch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewSearch = itemView.findViewById(R.id.imageSearch);
            textViewTitleSearch = itemView.findViewById(R.id.textViewTitleSearch);
            textArticleSearch = itemView.findViewById(R.id.textViewArticleSearch);

            itemView.setOnClickListener((View view) -> {
                Intent intent = new Intent(context, PlaySongActivity.class);
                intent.putExtra("getSong", (Parcelable) songArrayList.get(getPosition()));
                context.startActivity(intent);
            });
        }
    }

}


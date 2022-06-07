package com.tuan.apmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tuan.apmusic.Activity.PlaySongActivity;
import com.tuan.apmusic.Model.MostLikedSongs;
import com.tuan.apmusic.Model.Song;
import com.tuan.apmusic.R;
import com.tuan.apmusic.Service.APIService;
import com.tuan.apmusic.Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostLikedSongAdapter extends RecyclerView.Adapter<MostLikedSongAdapter.ViewHolder> implements OnLikeListener,OnAnimationEndListener  {

    View view;
    Context context;
    ArrayList<Song> mostLikedSongsArrayList;
    Song mostLikedSongs;

    public MostLikedSongAdapter(Context context, ArrayList<Song> mostLikedSongsArrayList){
        this.context = context;
        this.mostLikedSongsArrayList = mostLikedSongsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.part_most_liked_song,parent, false);
        return new MostLikedSongAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mostLikedSongs = mostLikedSongsArrayList.get(position);
        if ( mostLikedSongs == null) {
            return;
        }

        holder.likeButton.setOnLikeListener(this);
        holder.likeButton.setOnAnimationEndListener(this);

        holder.textArticle.setText(mostLikedSongs.getArtists());
        holder.textViewTitleMostLikedSong.setText(mostLikedSongs.getNameSong());
        Picasso.get().load(mostLikedSongs.getThumbnail()).into(holder.imageViewMostLikedSong);
    }

    @Override
    public int getItemCount() {
        return mostLikedSongsArrayList != null ? mostLikedSongsArrayList.size() : 0;
    }

    @Override
    public void onAnimationEnd(LikeButton likeButton) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LikeButton likeButton;
        ImageView imageViewMostLikedSong;
        TextView textViewTitleMostLikedSong, textArticle, textLike;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageViewMostLikedSong = itemView.findViewById(R.id.imageMostLikedSong);
            textViewTitleMostLikedSong = itemView.findViewById(R.id.textViewTitleMostLiked);
            textArticle = itemView.findViewById(R.id.textViewArticle);
            likeButton = itemView.findViewById(R.id.btn_like);

            itemView.setOnClickListener((View view) -> {
                Intent intent = new Intent(context, PlaySongActivity.class);
                intent.putExtra("getSongLike", (Parcelable) mostLikedSongsArrayList.get(getLayoutPosition()));

                context.startActivity(intent);
            });
        }
    }

    @Override
    public void liked(LikeButton likeButton) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.UpdateLikeForSong(mostLikedSongsArrayList.get(likeButton.getVerticalScrollbarPosition()).getId(), "1");
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (result.equals("Update like success")) {
                    Toast.makeText(context, "You liked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    @Override
    public void unLiked(LikeButton likeButton) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.UpdateLikeForSong(mostLikedSongsArrayList.get(likeButton.getVerticalScrollbarPosition()).getId(), "-1");
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (result.equals("Update like success")) {
                    Toast.makeText(context, "You unliked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}

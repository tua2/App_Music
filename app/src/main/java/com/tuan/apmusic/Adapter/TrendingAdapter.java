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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import com.tuan.apmusic.Activity.ListSongActivity;
import com.tuan.apmusic.Activity.PlaySongActivity;
import com.tuan.apmusic.Model.Song;
import com.tuan.apmusic.Model.Trending;
import com.tuan.apmusic.Model.User;
import com.tuan.apmusic.R;

public class TrendingAdapter extends PagerAdapter {
    Context context;
    ArrayList<Trending> trendingArrayList;

    ImageView imageViewTrending;



    public TrendingAdapter(Context context, ArrayList<Trending> trendingsArrayList) {
        this.context = context;
        this.trendingArrayList = trendingsArrayList;
    }

    @Override
    public int getCount() {
        return trendingArrayList != null ? trendingArrayList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.part_trending, null);

        imageViewTrending = view.findViewById(R.id.imageViewTrending);

//       get data from list
        Picasso.get().load(trendingArrayList.get(position).getImage()).into(imageViewTrending);
        container.addView(view);

        view.setOnClickListener((View v) -> {
            Intent intent = new Intent(context, PlaySongActivity.class);
            intent.putExtra("songTrending", (Serializable) trendingArrayList.get(position).getSongId());
            context.startActivity(intent);
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

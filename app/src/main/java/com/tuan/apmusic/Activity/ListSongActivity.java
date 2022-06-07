package com.tuan.apmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import com.tuan.apmusic.Adapter.ListSongAdapter;
import com.tuan.apmusic.Model.Charts;
import com.tuan.apmusic.Model.PlayListSong;
import com.tuan.apmusic.Model.Singer;
import com.tuan.apmusic.Model.Song;
import com.tuan.apmusic.R;


public class ListSongActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewListSong;
    FloatingActionButton floatingActionButton;
    ImageView imageViewListSong;
    PlayListSong playListSong;

    ListSongAdapter listSongAdapter;
    Singer singerArrayList;
    Charts chartsArrayList;
    ArrayList<Song> songArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

//        check network
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getLayout();

        PutDataIntent();
        init();


        if(playListSong != null) {
            if( !playListSong.getName().equals("")) {
                setDataInView(playListSong.getName(), playListSong.getThumbnail());
                getDataInTopic();
            }
        } else if (singerArrayList != null) {
            if( !singerArrayList.getName().equals("")) {
                setDataInView(singerArrayList.getName(), singerArrayList.getThumbnail());
                getDataInTopic();
            }
        } else if (chartsArrayList != null) {
            if( !chartsArrayList.getName().equals("")) {
                setDataInView(chartsArrayList.getName(), chartsArrayList.getThumbnail());
                getDataInTopic();
            }
        }


    }
    private void getLayout() {

        toolbar = findViewById(R.id.ToolbarList);
        coordinatorLayout = findViewById(R.id.coordinatorLayoutListSong);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        recyclerViewListSong = findViewById(R.id.recycleViewListSong);
        floatingActionButton = findViewById(R.id.floatingActionListSong);
        imageViewListSong = findViewById(R.id.imageViewListSong);

    }

    private void init() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener((View view) -> {
            finish();
            songArrayList.clear();
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        floatingActionButton.setEnabled(false);

    }


    public void setDataInView(String name, String image){
        collapsingToolbarLayout.setTitle(name);
        Picasso.get().load(image).into(imageViewListSong);
    }


    public void getDataInTopic(){

        if (playListSong != null) {
            songArrayList = (ArrayList<Song>) playListSong.getSongs();
            listSongAdapter = new ListSongAdapter(ListSongActivity.this, songArrayList);

        } else if (singerArrayList != null) {
            songArrayList = (ArrayList<Song>) singerArrayList.getSongs();
            listSongAdapter = new ListSongAdapter(ListSongActivity.this, songArrayList);

        } else if (chartsArrayList != null) {
            songArrayList = (ArrayList<Song>) chartsArrayList.getSongs();
            listSongAdapter = new ListSongAdapter(ListSongActivity.this, songArrayList);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListSongActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewListSong.setLayoutManager(linearLayoutManager);
//                set adapter for recycle view
        recyclerViewListSong.setAdapter(listSongAdapter);

        setEvenClickFloating();
    }

    private void PutDataIntent(){

        Intent intent = getIntent();

        if (intent != null){
            if (intent.hasExtra("playlist")) {
                playListSong = (PlayListSong) intent.getSerializableExtra("playlist");

            } else if (intent.hasExtra("listSongOfSinger")) {
                singerArrayList = (Singer) intent.getSerializableExtra("listSongOfSinger");

            } else if (intent.hasExtra("listSongOfCategories")) {
                chartsArrayList = (Charts) intent.getSerializableExtra("listSongOfCategories");
            }
        }
    }

    private void setEvenClickFloating(){

        floatingActionButton.setEnabled(true);

        floatingActionButton.setOnClickListener((View view) -> {

            Intent intent = new Intent(ListSongActivity.this, PlaySongActivity.class);
            intent.putExtra("getAllSingOfSong", songArrayList);
            startActivity(intent);

        });
    }
}

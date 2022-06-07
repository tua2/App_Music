package com.tuan.apmusic.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuan.apmusic.Activity.PlaySongActivity;
import com.tuan.apmusic.Adapter.PlaySongAdapter;
import com.tuan.apmusic.R;

public class Fragment_Play_List_Songs extends Fragment {

    View view;
    RecyclerView recyclerViewPlaySong;
    PlaySongAdapter playSongAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_list_songs,container, false);

        recyclerViewPlaySong = view.findViewById(R.id.recyclerViewPlaySong);

        if (PlaySongActivity.songArrayListSong.size() > 0) {

            playSongAdapter = new PlaySongAdapter(getActivity(), PlaySongActivity.songArrayListSong);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewPlaySong.setLayoutManager(linearLayoutManager);

            recyclerViewPlaySong.setAdapter(playSongAdapter);
        }
        return view;
    }
}

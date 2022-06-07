package com.tuan.apmusic.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.tuan.apmusic.Activity.MainActivity;
import com.tuan.apmusic.Adapter.PlayListAdapter;
import com.tuan.apmusic.Model.PlayListSong;
import com.tuan.apmusic.R;
import com.tuan.apmusic.Service.APIService;
import com.tuan.apmusic.Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_PlayList extends Fragment {


    PlayListAdapter playListAdapter;
    ArrayList<PlayListSong> playLists;

    View view;
    TextView textTitlePlayList;
    public static RecyclerView recyclerViewPlayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
//       get layout
        recyclerViewPlayList = view.findViewById(R.id.recyclerViewPlayList);
        textTitlePlayList = view.findViewById(R.id.textTitlePlayList);

        GetDataPlayList();

        return view;
    }

    public void GetDataPlayList(){
//        get service
        DataService dataService = APIService.getService();

//        get list data through the service
        Call<List<PlayListSong>> callback = dataService.GetDataSongPlayList();
        callback.enqueue(new Callback<List<PlayListSong>>() {

            //            get data response
            @Override
            public void onResponse(Call<List<PlayListSong>> call, Response<List<PlayListSong>> response) {
                playLists = (ArrayList<PlayListSong>) response.body();

                playListAdapter = new PlayListAdapter(getActivity() ,playLists);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewPlayList.setLayoutManager(linearLayoutManager);

                recyclerViewPlayList.setAdapter(playListAdapter);

                textTitlePlayList.setText("Recommended");
            }

            @Override
            public void onFailure(Call<List<PlayListSong>> call, Throwable t) {

            }
        });
    }
}


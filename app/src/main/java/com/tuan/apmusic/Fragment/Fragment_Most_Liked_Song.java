package com.tuan.apmusic.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.tuan.apmusic.Activity.MainActivity;
import com.tuan.apmusic.Adapter.MostLikedSongAdapter;
import com.tuan.apmusic.Model.Song;
import com.tuan.apmusic.R;
import com.tuan.apmusic.Service.APIService;
import com.tuan.apmusic.Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Most_Liked_Song extends Fragment{

    View view;
    public static RecyclerView recyclerViewLikeSong;
    TextView titleMostLikeSong;
    MostLikedSongAdapter mostLikedSongAdapter;
    ArrayList<Song> mostLikedSongs;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_most_liked_song, container, false);

        recyclerViewLikeSong = view.findViewById(R.id.recyclerViewMostLikedSong);
        titleMostLikeSong = view.findViewById(R.id.textTitleMostLikedSong);
        GetDataMostLikeSong();

        return view;
    }

    public void GetDataMostLikeSong(){
        DataService dataService = APIService.getService();

        final Dialog dialog1  = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.part_dialog);
        Window window = dialog1.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog1.setCancelable(false);
        dialog1.show();

        Call<List<Song>> callback = dataService.GetDataMostLikedSongsCurrent();

        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(@NonNull Call<List<Song>> call, @NonNull Response<List<Song>> response) {

                mostLikedSongs = (ArrayList<Song>) response.body();

                dialog1.dismiss();

                mostLikedSongAdapter = new MostLikedSongAdapter(getActivity(), mostLikedSongs);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewLikeSong.setLayoutManager(linearLayoutManager);

//                set adapter for recycle view
                recyclerViewLikeSong.setAdapter(mostLikedSongAdapter);

                titleMostLikeSong.setText("Most Like Song");
            }

            @Override
            public void onFailure(@NonNull Call<List<Song>> call, @NonNull Throwable t) {

            }
        });
    }

}

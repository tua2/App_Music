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
import com.tuan.apmusic.Adapter.SingerAdapter;
import com.tuan.apmusic.Model.Singer;
import com.tuan.apmusic.R;
import com.tuan.apmusic.Service.APIService;
import com.tuan.apmusic.Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Album_Singer extends Fragment {
    View view;
    SingerAdapter singerAdapter;
    RecyclerView recyclerViewSinger;
    TextView textTitleSinger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerViewSinger = view.findViewById(R.id.recyclerViewSinger);
        textTitleSinger = view.findViewById(R.id.textTitleAlbum);
        GetDataSinger();
        return  view;
    }


    public void GetDataSinger(){
        DataService dataservice = APIService.getService();
        Call<List<Singer>> callback = dataservice.GetDataSinger();
        callback.enqueue(new Callback<List<Singer>>() {
            @Override
            public void onResponse(Call<List<Singer>> call, Response<List<Singer>> response) {

                ArrayList<Singer> singers = (ArrayList<Singer>) response.body();

                singerAdapter = new SingerAdapter(getActivity(), singers);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewSinger.setLayoutManager(linearLayoutManager);

//                set adapter for recycle view
                recyclerViewSinger.setAdapter(singerAdapter);

                textTitleSinger.setText("Popular Albums");
            }

            @Override
            public void onFailure(Call<List<Singer>> call, Throwable t) {

            }
        });
    }


}


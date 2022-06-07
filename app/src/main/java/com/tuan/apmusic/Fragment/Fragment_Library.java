package com.tuan.apmusic.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.tuan.apmusic.Adapter.AlbumAllAdapter;
import com.tuan.apmusic.Adapter.SingerAdapter;
import com.tuan.apmusic.Model.Singer;
import com.tuan.apmusic.R;
import com.tuan.apmusic.Service.APIService;
import com.tuan.apmusic.Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Library extends Fragment {
    View view;
    AlbumAllAdapter albumAllAdapter;
    RecyclerView recyclerViewAlbumAll;
    TextView textTitleAlbumAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerViewAlbumAll = view.findViewById(R.id.recyclerViewAlbumAll);
        textTitleAlbumAll = view.findViewById(R.id.textTitleAlbumAll);
        GetDataAlbumAll();

        return  view;
    }

    public void GetDataAlbumAll(){
        ProgressDialog dialog = ProgressDialog.show(
                getActivity(),"","Please wait...",true
        );

        DataService dataservice = APIService.getService();
        Call<List<Singer>> callback = dataservice.GetDataSinger();
        callback.enqueue(new Callback<List<Singer>>() {
            @Override
            public void onResponse(Call<List<Singer>> call, Response<List<Singer>> response) {

                ArrayList<Singer> singers = (ArrayList<Singer>) response.body();

                albumAllAdapter = new AlbumAllAdapter(getActivity(), singers);
                recyclerViewAlbumAll.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//                set adapter for recycle view
                recyclerViewAlbumAll.setAdapter(albumAllAdapter);

                textTitleAlbumAll.setText("Album");

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Singer>> call, Throwable t) {

            }
        });
    }
}
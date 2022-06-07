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
import com.tuan.apmusic.Adapter.ChartsAdapter;
import com.tuan.apmusic.Model.Charts;
import com.tuan.apmusic.R;
import com.tuan.apmusic.Service.APIService;
import com.tuan.apmusic.Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Charts extends Fragment {

    View view;
    public static RecyclerView recyclerViewCharts;
    TextView textTitleCharts;
    ChartsAdapter chartsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_charts, container, false);
        recyclerViewCharts = view.findViewById(R.id.recyclerViewCharts);
        textTitleCharts = view.findViewById(R.id.textTitleCharts);

        GetDataCharts();
        return view;
    }

    public void GetDataCharts(){
        DataService dataService = APIService.getService();
        Call<List<Charts>> callback = dataService.GetDataCharts();

        callback.enqueue(new Callback<List<Charts>>() {
            @Override
            public void onResponse(Call<List<Charts>> call, Response<List<Charts>> response) {

                ArrayList<Charts> charts = (ArrayList<Charts>) response.body();
                chartsAdapter = new ChartsAdapter(getActivity(), charts);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewCharts.setLayoutManager(linearLayoutManager);

//                set adapter for recycle view
                recyclerViewCharts.setAdapter(chartsAdapter);

                textTitleCharts.setText("Charts");
            }

            @Override
            public void onFailure(Call<List<Charts>> call, Throwable t) {

            }
        });
    }
}

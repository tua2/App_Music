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
import com.tuan.apmusic.Adapter.TopicAdapter;
import com.tuan.apmusic.Model.Topic;
import com.tuan.apmusic.R;
import com.tuan.apmusic.Service.APIService;
import com.tuan.apmusic.Service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Topic extends Fragment {
    View view;
    public static RecyclerView recyclerViewTopic;
    TextView textTitleTopic;

    TopicAdapter topicAdapter;
    ArrayList<Topic> topics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic, container,false);

        recyclerViewTopic = view.findViewById(R.id.recyclerViewTopic);
        textTitleTopic = view.findViewById(R.id.textTitleTopic);
        getDataTopic();
        return  view;
    }


    public void getDataTopic(){
        DataService dataService = APIService.getService();
        Call<List<Topic>> callback = dataService.GetDataTopic();

        callback.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                topics = (ArrayList<Topic>) response.body();

                topicAdapter = new TopicAdapter(getActivity() ,topics);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewTopic.setLayoutManager(linearLayoutManager);
                recyclerViewTopic.setAdapter(topicAdapter);
                textTitleTopic.setText("Fresh New Music");
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {

            }
        });
    }
}
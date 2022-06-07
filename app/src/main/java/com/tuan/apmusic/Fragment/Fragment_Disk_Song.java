package com.tuan.apmusic.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import com.tuan.apmusic.R;

public class Fragment_Disk_Song extends Fragment {

    View view;
    public CircleImageView circleImageViewPlaySong;
    //    public ObjectAnimator objectAnimator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disk_song, container,false);

        circleImageViewPlaySong = view.findViewById(R.id.imageCirclePlaySong);
        startAnimation();
        return view;
    }

    public void startAnimation() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                circleImageViewPlaySong.animate().rotationBy(360).withEndAction(this).setDuration(20000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
        circleImageViewPlaySong.animate().rotationBy(360).withEndAction(runnable).setDuration(20000)
                .setInterpolator(new LinearInterpolator()).start();
    }

    public void ImageSong(String image) {
        Picasso.get().load(image).into(circleImageViewPlaySong);
    }

}

package com.tuan.apmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import com.tuan.apmusic.Adapter.ViewPagerPlaySong;
import com.tuan.apmusic.Fragment.Fragment_Disk_Song;
import com.tuan.apmusic.Fragment.Fragment_Play_List_Songs;
import com.tuan.apmusic.Model.Song;
import com.tuan.apmusic.R;

public class PlaySongActivity extends AppCompatActivity {

    Toolbar toolbarPlaySong;
    TextView textTimeSong, textTimeTotalSong , textViewNameSong, textViewNameArticle ;
    SeekBar seekBarTimeSong;
    ImageButton imgPlay, imgShuffle, imgNext, imgPrevious, imgRepeat;
    ViewPager2 viewPagerPlaySong;

    Fragment_Disk_Song fragment_disk_song;
    Fragment_Play_List_Songs fragment_play_list_songs;

    public static ArrayList<Song> songArrayListSong = new ArrayList<>();
    public static ViewPagerPlaySong addFragmentSong;

    //    play some music
    MediaPlayer mediaPlayer;

    int position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean nextSong = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

//        check network
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initLayout();

        fragment_play_list_songs = new Fragment_Play_List_Songs();
        fragment_disk_song = new Fragment_Disk_Song();

        getDataFromIntent();
        initDataInView();
        evenClick();
    }

    public void evenClick() {

        Handler handler = new Handler();

//        create delay when click event
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(addFragmentSong.createFragment(0) != null) {
                    if(songArrayListSong.size() > 0) {
                        fragment_disk_song.ImageSong(songArrayListSong.get(0).getThumbnail());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 300);


        eventSeekBarWithPlaySong();

//        event for song
        evenClickPlaySong();
        evenClickRepeatSong();
        evenClickRandomSong();
        eventClickPreviousSong();
        eventClickNextSong();

    }

    private void initLayout() {

        toolbarPlaySong = findViewById(R.id.toolBarPlaySong);
        textTimeSong = findViewById(R.id.textViewRunTime);
        textTimeTotalSong = findViewById(R.id.textViewTimeTotal);
        seekBarTimeSong = findViewById(R.id.seekBarTime);
        imgPlay = findViewById(R.id.imageButtonPlayOfPause);
        imgNext = findViewById(R.id.imageButtonNextSong);
        imgPrevious = findViewById(R.id.imageButtonPrevious);
        imgShuffle = findViewById(R.id.imageButtonShuffle);
        imgRepeat = findViewById(R.id.imageButtonLoop);
        viewPagerPlaySong = findViewById(R.id.viewPagerDiskSong);
        textViewNameSong = findViewById(R.id.textViewNameSong);
        textViewNameArticle = findViewById(R.id.textViewNameArticle);

    }

    private void initDataInView(){

        setSupportActionBar(toolbarPlaySong);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbarPlaySong.setNavigationOnClickListener((View view) -> {
            finish();
            mediaPlayer.stop();
            songArrayListSong.clear();
        });

        toolbarPlaySong.setTitleTextColor(Color.WHITE);

        addFragmentSong = new ViewPagerPlaySong(this);

//        add 2 fragment in viewPaper
        addFragmentSong.addFragment(fragment_disk_song);
        addFragmentSong.addFragment(fragment_play_list_songs);
        viewPagerPlaySong.setAdapter(addFragmentSong);

        fragment_disk_song = (Fragment_Disk_Song) addFragmentSong.createFragment(0);

//        play first song
        if(songArrayListSong.size() > 0) {

            Objects.requireNonNull(getSupportActionBar()).setTitle(songArrayListSong.get(0).getNameSong());
            textViewNameSong.setText(songArrayListSong.get(0).getTitle());
            textViewNameArticle.setText(songArrayListSong.get(0).getArtists());
            new playMusic().execute(songArrayListSong.get(0).getLink());
            imgPlay.setImageResource(R.drawable.pause);

        }
    }


    private void getDataFromIntent(){
        Intent intent = getIntent();

//        Delete old data to avoid overlapping
        songArrayListSong.clear();

        if (intent != null) {
            if(intent.hasExtra("getSong")) {
                Song song = intent.getParcelableExtra("getSong");
                songArrayListSong.add(song);

            } else if (intent.hasExtra("getSongLike")) {
                Song mostLikedSongs = (Song) intent.getParcelableExtra("getSongLike");
                songArrayListSong.add(mostLikedSongs);

            } else if (intent.hasExtra("getAllSingOfSong")){
                songArrayListSong = intent.getParcelableArrayListExtra("getAllSingOfSong");

            } else if (intent.hasExtra("songTrending")) {
                Song trending = intent.getParcelableExtra("songTrending");
                songArrayListSong.add(trending);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    public class playMusic extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String song) {
            super.onPostExecute(song);

            try {
                mediaPlayer = new MediaPlayer();

                mediaPlayer.setAudioAttributes (
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build());

                mediaPlayer.setOnCompletionListener(mediaPlayer -> {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                });

                mediaPlayer.setDataSource(song);

//                used to play song music
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();

//            update total time of song
            TimeSong();
            UpdateTimePlaySong();
        }

    }

    private void TimeSong() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss",Locale.getDefault());
        textTimeTotalSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));

//        drag seekbar -> update time for seekbar
        seekBarTimeSong.setMax(mediaPlayer.getDuration());
    }

    private void UpdateTimePlaySong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null) {
                    seekBarTimeSong.setProgress(mediaPlayer.getCurrentPosition());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
                    textTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);

//                    complement song
                    mediaPlayer.setOnCompletionListener(mediaPlayer -> {

//                        next song
                        nextSong = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        },300);

//        switch song
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(nextSong == true) {

//                    fragment_disk_song.objectAnimator.start();

                    checkNextTime();

//                    delete handler when get song
                    handler1.removeCallbacks(this);

                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }



    private void evenClickPlaySong(){

        imgPlay.setOnClickListener((View view) -> {

//            check media is running
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                imgPlay.setImageResource(R.drawable.play_button);
                fragment_disk_song.circleImageViewPlaySong.animate().cancel();
            } else  {
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.pause);
                fragment_disk_song.startAnimation();
            }
        });

    }

    private void evenClickRepeatSong(){

        //        click repeat
        imgRepeat.setOnClickListener((View view) -> {

            if (repeat == false) {
                if (checkRandom == true) {
                    checkRandom = false;
                    imgRepeat.setImageResource(R.drawable.repeat_violet);
                    imgShuffle.setImageResource(R.drawable.shuffle);
                }
                imgRepeat.setImageResource(R.drawable.repeat_violet);
                repeat = true;
            } else {
                imgRepeat.setImageResource(R.drawable.repeat);
                repeat = false;
            }
        });
    }

    private void evenClickRandomSong(){
//        click random song
        imgShuffle.setOnClickListener((View view) -> {
            if (checkRandom == false) {
                if (repeat == true) {
                    repeat = false;
                    imgShuffle.setImageResource(R.drawable.shuffle_violet);
                    imgRepeat.setImageResource(R.drawable.repeat);
                }
                imgShuffle.setImageResource(R.drawable.shuffle_violet);
                checkRandom = true;
            } else {
                imgShuffle.setImageResource(R.drawable.shuffle);
                checkRandom = false;
            }
        });
    }

    private void eventSeekBarWithPlaySong(){
        //        seek bar time
        seekBarTimeSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void eventClickNextSong() {

        imgNext.setOnClickListener((View view) -> {

            fragment_disk_song.startAnimation();

            if (songArrayListSong.size() > 0 ) {

                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();

                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                checkNextTime();
            }
        });

    }

    private void eventClickPreviousSong() {

        imgPrevious.setOnClickListener((View view) -> {

            fragment_disk_song.startAnimation();

            if (songArrayListSong.size() > 0 ) {

                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                if (position < (songArrayListSong.size())) {
                    imgPlay.setImageResource(R.drawable.pause);
                    position--;
                    if (position < 0) {
                        position = songArrayListSong.size() - 1;
                    } else if (repeat == true) {
                        if (position == 0) {
                            position = songArrayListSong.size();
                        }
                        position += 1;
                    } else if (checkRandom == true) {
                        Random random = new Random();
                        int index = random.nextInt(songArrayListSong.size());

                        if(index == position) {
                            position = index - 1;
                        }
                        position = index;
                    }
                }

                new playMusic().execute(songArrayListSong.get(position).getLink());
                fragment_disk_song.ImageSong(songArrayListSong.get(position).getThumbnail());

                textViewNameSong.setText(songArrayListSong.get(position).getTitle());
                textViewNameArticle.setText(songArrayListSong.get(position).getArtists());

                Objects.requireNonNull(getSupportActionBar()).setTitle(songArrayListSong.get(position).getNameSong());

                UpdateTimePlaySong();

                makeButtonDelay();

            }
        });
    }

    public void checkNextTime() {

        if (position < (songArrayListSong.size())) {
            imgPlay.setImageResource(R.drawable.pause);
            position++;
            if (position > (songArrayListSong.size() - 1)) {
                position = 0;

            } else if (repeat == true) {
                if (position == 0) {
                    position = songArrayListSong.size();
                }
                position -= 1;
            } else if (checkRandom == true) {
                Random random = new Random();
                int index = random.nextInt(songArrayListSong.size());

                if(index == position) {
                    position = index - 1;
                }
                position = index;
            }

            new playMusic().execute(songArrayListSong.get(position).getLink());
            fragment_disk_song.ImageSong(songArrayListSong.get(position).getThumbnail());
            textViewNameSong.setText(songArrayListSong.get(position).getTitle());
            textViewNameArticle.setText(songArrayListSong.get(position).getArtists());
            Objects.requireNonNull(getSupportActionBar()).setTitle(songArrayListSong.get(position).getNameSong());
        }
        makeButtonDelay();
    }

    public void makeButtonDelay() {

        imgPrevious.setClickable(false);
        imgNext.setClickable(false);
        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            imgPrevious.setClickable(true);
            imgNext.setClickable(true);
        },3000);
        nextSong = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

}

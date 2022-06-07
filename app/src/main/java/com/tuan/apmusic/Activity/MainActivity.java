package com.tuan.apmusic.Activity;

import com.tuan.apmusic.Fragment.Fragment_Home_Page;
import com.tuan.apmusic.Fragment.Fragment_Library;
import com.tuan.apmusic.Fragment.Fragment_Search;

import com.tuan.apmusic.R;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        check network
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



//        call layout
        bottomNavigation = findViewById(R.id.bottom_navigation);

//        run fragment home first when show UI
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager2, new Fragment_Home_Page()).commit();

//        get event when click navigation
        bottomNavigation.setOnItemSelectedListener(onItemSelectedListener);

    }

    @SuppressLint("NonConstantResourceId")
    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener = (item -> {
        int id = item.getItemId();
        Fragment selectedFragment =  null;
        switch (id) {
            case R.id.home_page:
                selectedFragment = new Fragment_Home_Page();
                break;
            case R.id.search:
                selectedFragment = new Fragment_Search();
                break;
            case R.id.library:
                selectedFragment = new Fragment_Library();
                break;
        }
        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager2, selectedFragment).commit();
        return true;
    });

}

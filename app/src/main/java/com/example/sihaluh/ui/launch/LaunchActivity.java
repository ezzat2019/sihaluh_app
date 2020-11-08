package com.example.sihaluh.ui.launch;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.sihaluh.R;
import com.example.sihaluh.utils.receiver.MyReceiver;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sihaluh.ui.launch.adapter.SectionsPagerAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LaunchActivity extends AppCompatActivity {
    // var
    private MyReceiver myReceiver=new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);




    }



    @Override
    protected void onStart() {
        super.onStart();
        MyReceiver.startReceiver(LaunchActivity.this,myReceiver);
    }



    @Override
    protected void onStop() {
        super.onStop();
        MyReceiver.stopReceiver(LaunchActivity.this,myReceiver);
    }


}
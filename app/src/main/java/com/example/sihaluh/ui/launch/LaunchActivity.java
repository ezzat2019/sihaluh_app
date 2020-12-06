package com.example.sihaluh.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.sihaluh.R;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.launch.adapter.SectionsPagerAdapter;
import com.example.sihaluh.utils.receiver.MyReceiver;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LaunchActivity extends AppCompatActivity {
    // var
    private final MyReceiver myReceiver = new MyReceiver();
    private PrefViewModel prefViewModel;
    public static View start_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        start_progress=findViewById(R.id.start_progress);
       LaunchActivity.start_progress.setVisibility(View.GONE);




    }




    @Override
    protected void onStart() {
        super.onStart();

        if (prefViewModel.getPhone().equals("") || FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        finish();

    }


}
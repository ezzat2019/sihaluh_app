package com.example.sihaluh.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import com.example.sihaluh.R;
import com.example.sihaluh.ui.launch.LaunchActivity;
import com.example.sihaluh.utils.receiver.MyReceiver;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startSplash();
    }



    private void startSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(), LaunchActivity.class);
                startActivity(intent);

                finish();
            }
        }, 2000);
    }
}
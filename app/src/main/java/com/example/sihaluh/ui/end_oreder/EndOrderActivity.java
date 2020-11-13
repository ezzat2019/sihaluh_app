package com.example.sihaluh.ui.end_oreder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sihaluh.R;

public class EndOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        setContentView(R.layout.activity_end_order);

    }
}
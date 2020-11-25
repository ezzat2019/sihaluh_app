package com.example.sihaluh.ui.full_image;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullMessageImageActivity extends AppCompatActivity {
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_message_image);
        if (getIntent().hasExtra("img"))
        {
            photoView=findViewById(R.id.img_message_full);
            Glide.with(getApplicationContext())
                    .load(getIntent().getStringExtra("img"))
                    .into(photoView);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "invaild image1", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

    }
}
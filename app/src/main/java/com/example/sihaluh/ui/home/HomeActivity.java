package com.example.sihaluh.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sihaluh.R;
import com.example.sihaluh.ui.launch.LaunchActivity;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    // ui
    private BottomNavigationView navView;
    public static TextView txt_name_bar;
    private ImageView img_signup;

    // var
    private PrefViewModel prefViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navView = findViewById(R.id.nav_view);
        txt_name_bar = findViewById(R.id.txt_home);
        img_signup = findViewById(R.id.img_signup);

        registerForContextMenu(img_signup);
        img_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openContextMenu(v);
            }
        });



        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_post_job, R.id.navigation_notifications, R.id.navigation_chat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.signout_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signup) {
            FirebaseAuth.getInstance().signOut();
            prefViewModel.putPhone("");
            startActivity(new Intent(getApplicationContext(), LaunchActivity.class));
            finish();
            return true;

        } else if (item.getItemId() == R.id.connect_ezzat) {
            Uri uri = Uri.parse("smsto:" + "01144614303");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, "connet to ezzat"));
            return true;

        } else {
            return super.onContextItemSelected(item);

        }

    }
}
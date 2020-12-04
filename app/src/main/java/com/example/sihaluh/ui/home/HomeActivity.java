package com.example.sihaluh.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.TokkenModel;
import com.example.sihaluh.ui.home.fagement.cart.viewmodel.MyCartViewModel;
import com.example.sihaluh.ui.launch.LaunchActivity;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {
    public static TextView txt_name_bar;
    // ui
    private BottomNavigationView navView;
    private ImageView img_signup, img_home_cart;
    private CardView card_non_empty_home;

    // var
    private PrefViewModel prefViewModel;
    private Boolean gotocart = false;
    private NavController navController;
    private MyCartViewModel myCartViewModel;
    private MeowBottomNavigation meowBottomNavigation;

    // firebase
    private final DatabaseReference ref_tokkens = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_TOKKENS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        updateTokken();

        if (getIntent().hasExtra(AllFinal.INTENT_GOTO_CART)) {
            gotocart = getIntent().getBooleanExtra(AllFinal.INTENT_GOTO_CART, false);

        }


        registerForContextMenu(img_signup);
        img_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openContextMenu(v);
            }
        });


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//

        meowBottomNavigation = findViewById(R.id.meow);
//        NavigationUI.setupWithNavController(meowBottomNavigation, navController);
        observeMyCart();

        MeowBottomNavigation.Model model = new MeowBottomNavigation.Model(1, R.drawable.ic_home_black_24dp);
        meowBottomNavigation.add(model);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_shopping_cart_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_add_shopping_cart_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_notifications_black_24dp));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_chat_24));


        actions();
        if (gotocart) {
            navController.navigate(R.id.navigation_cart);

            meowBottomNavigation.show(2, true);
        } else {
            navController.navigate(R.id.navigation_home);

            meowBottomNavigation.show(1, true);


        }

    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        finish();
    }

    private void updateTokken() {
        TokkenModel tokkenModel = new TokkenModel(FirebaseInstanceId.getInstance().getToken());
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            ref_tokkens.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(tokkenModel);
        } else {
            Toast.makeText(getApplicationContext(), "error when update tokken", Toast.LENGTH_SHORT).show();
        }

    }

    private void observeMyCart() {
        if (myCartViewModel.getMyCartProducts(prefViewModel.getPhone()) != null) {
            myCartViewModel.getMyCartProducts(prefViewModel.getPhone())
                    .observe(this, new Observer<CartItemModel>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onChanged(CartItemModel cartItemModel) {
                            if (cartItemModel != null) {
                                if (cartItemModel.productModelList.size() > 0) {
                                    showCard(true);
//                                    navView.getOrCreateBadge(R.id.navigation_cart).setBackgroundColor(getColor(
//                                            android.R.color.holo_orange_dark
//                                            ));
//                                    navView.getOrCreateBadge(R.id.navigation_cart).setNumber(cartItemModel.productModelList.size());
                                    meowBottomNavigation.setCount(2, cartItemModel.productModelList.size() + "");

                                } else {
                                    showCard(false);
//                                    navView.removeBadge(R.id.navigation_cart);
                                    meowBottomNavigation.clearCount(2);

                                }
                            }


                        }
                    });
        }

    }

    private void actions() {
        img_home_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.navigation_cart);
                meowBottomNavigation.show(2, true);
            }
        });

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                switch (item.getId()) {
                    case 1:
                        navController.navigate(R.id.navigation_home);

                        break;
                    case 2:
                        navController.navigate(R.id.navigation_cart);

                        break;
                    case 3:
                        navController.navigate(R.id.navigation_post_job);

                        break;
                    case 4:
                        navController.navigate(R.id.navigation_notifications);

                        break;
                    case 5:
                        navController.navigate(R.id.navigation_chat);

                        break;
                    default:
                        navController.navigate(R.id.navigation_home);


                }
            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });


    }

    private void init() {
        // navView = findViewById(R.id.nav_view);

        txt_name_bar = findViewById(R.id.txt_home);
        img_signup = findViewById(R.id.img_signup);
        img_home_cart = findViewById(R.id.img_home_cart);
        card_non_empty_home = findViewById(R.id.card_non_empty_home);
        myCartViewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        showCard(false);
    }

    private void showCard(Boolean b) {
        if (b) {
            card_non_empty_home.setVisibility(View.VISIBLE);
        } else {
            card_non_empty_home.setVisibility(View.GONE);
        }
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
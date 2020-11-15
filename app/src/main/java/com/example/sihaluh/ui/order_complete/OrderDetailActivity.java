package com.example.sihaluh.ui.order_complete;

import android.Manifest;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.AdressUserModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.data.model.RegisterModel;
import com.example.sihaluh.ui.end_oreder.EndOrderActivity;
import com.example.sihaluh.ui.order_complete.fragment.MapsFragment;
import com.example.sihaluh.ui.order_complete.viewmodel.OrderDetailViewModel;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class OrderDetailActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {
    public static OrderDetailViewModel viewModel;
    private CardView card_map;
    // ui
    private EditText ed_loction_oreder_adress;
    private TextInputEditText ed_name_oreder_adress, ed_phone_oreder_adress;
    private Button btn_get_loction, btn_next;
    private ImageView img_order_detail_back;

    // var
    private MapsFragment mapFragment;
    private PrefViewModel prefViewModel;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_DATABASE_LOGIN);
    private final DatabaseReference reference_adress = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_DATABASE_ADRESS_USER);
    public static String phone;
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    private AdressUserModel adressUserModel;
    private Double total;

    public void showCard(Boolean b) {
        if (b) {
            card_map.setAlpha(0.0f);
            card_map.setVisibility(View.VISIBLE);
            card_map.animate().alpha(1.0f).setDuration(500);
            Log.d("zzzzzzzzzzzz", "onAnimationCancel: 4");

        } else {
            card_map.animate().alpha(0).setDuration(500);
            card_map.setVisibility(View.GONE);


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        if (getIntent().hasExtra(AllFinal.INTENT_TOTAL)) {
            total = getIntent().getDoubleExtra(AllFinal.INTENT_TOTAL, 0.0);
            productModelArrayList=getIntent().getParcelableArrayListExtra(AllFinal.ALL_ITEM_USER_BUY);
            mapFragment = new MapsFragment();
            init();

            actions();

        } else {
            Toast.makeText(this, "error try again later", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

    }


    private void actions() {

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditText()) {
                    ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    if (manager.getActiveNetworkInfo() != null) {
                        if (adressUserModel != null) {
                            adressUserModel.setName(ed_name_oreder_adress.getText().toString());
                            reference_adress.child(phone)
                                    .setValue(adressUserModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(getApplicationContext(), EndOrderActivity.class);

                                    intent.putExtra(AllFinal.INTENT_ADRESS, adressUserModel);
                                    intent.putExtra(AllFinal.INTENT_TOTAL, total);
                                    intent.putExtra(AllFinal.ALL_ITEM_USER_BUY,productModelArrayList);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(OrderDetailActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(OrderDetailActivity.this, "error try get your loction again!", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(OrderDetailActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btn_get_loction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    if (manager.getActiveNetworkInfo() != null) {
                        getSupportFragmentManager().beginTransaction().replace(
                                R.id.fragment, mapFragment
                        ).commit();
                        showCard(true);
                    } else {
                        Toast.makeText(OrderDetailActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    showCard(false);
                    requestLoctionPermission();
                }


            }
        });

        img_order_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private Boolean checkEditText() {

        if (ed_name_oreder_adress.getText().toString().isEmpty()) {

            ed_name_oreder_adress.setError("enter you name first!");
            ed_name_oreder_adress.setFocusable(true);
            return false;
        }
        if (ed_phone_oreder_adress.getText().toString().isEmpty()) {

            ed_phone_oreder_adress.setError("enter your phone number first!");
            ed_phone_oreder_adress.setFocusable(true);
            return false;
        }
        if (ed_phone_oreder_adress.getText().toString().length() < 11) {

            ed_phone_oreder_adress.setError("enter correct phone number !");
            ed_phone_oreder_adress.setFocusable(true);
            return false;
        }

        if (ed_loction_oreder_adress.getText().toString().isEmpty()) {
            Toast.makeText(this, "selcet your loction first", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void requestLoctionPermission() {
        EasyPermissions.requestPermissions(this, "access loction", 2, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void init() {
        ed_loction_oreder_adress = findViewById(R.id.ed_loction_oreder_adress);
        ed_name_oreder_adress = findViewById(R.id.ed_name_oreder_adress);
        ed_phone_oreder_adress = findViewById(R.id.ed_phone_oreder_adress);

        btn_get_loction = findViewById(R.id.btn_get_loction);
        btn_next = findViewById(R.id.btn_save);

        img_order_detail_back = findViewById(R.id.img_order_detail_back);
        viewModelObserving();


        card_map = findViewById(R.id.card_map);

        card_map.setVisibility(View.GONE);
    }



    private void viewModelObserving() {
        viewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        viewModel.getLiveLoctionName().observe(this
                , new Observer<AdressUserModel>() {
                    @Override
                    public void onChanged(AdressUserModel s) {
                        if (s != null && !s.getLoction_name().isEmpty()) {
                            adressUserModel = s;
                            ed_loction_oreder_adress.setText(s.getLoction_name());

                        }


                    }
                });
        phone = prefViewModel.getPhone();

        getDataOfUser();
    }

    private void getDataOfUser() {
        reference.child(phone)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            RegisterModel registerModel = snapshot.getValue(RegisterModel.class);
                            ed_name_oreder_adress.setText(registerModel.getName());
                            ed_phone_oreder_adress.setText(registerModel.getPhone());
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (card_map.getVisibility() == View.GONE) {
            Log.d("zzzzz", "onBackPressed: 2");
            super.onBackPressed();
        } else {

            showCard(false);
            getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
            Log.d("zzzzz", "onBackPressed: 1");
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment, new MapsFragment()
        ).commit();
        showCard(true);

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {


    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }
}
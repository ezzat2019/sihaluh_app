package com.example.sihaluh.ui.end_oreder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.AdressUserModel;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.EndOrderModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.home.fagement.cart.viewmodel.MyCartViewModel;
import com.example.sihaluh.ui.launch.LaunchActivity;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EndOrderActivity extends AppCompatActivity {
    // ui
    private TextView txt_end_name, txt_end_phone, txt_end_loction, txt_end_delvery_totall, txt_end_subtotal, txt_end_shipping, txt_end_total;
    private Button btn_end_confirm;
    private RadioGroup radio_payment;
    private ProgressDialog progressDialog;

    // var
    private AdressUserModel adressUserModel;
    private Double total_price;
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref_end_order = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_ENDORDER);
    private DatabaseReference ref_collection = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_ORDER_COLLECTION);
    private DatabaseReference ref_start_order = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_STARORDER);
    private PrefViewModel prefViewModel;
    private String typr_payment = AllFinal.PAYMENT_CASH;
    private MyCartViewModel myCartViewModel;
    private CartItemModel cartItemModel;
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_order);

        if (getIntent().hasExtra(AllFinal.INTENT_ADRESS)) {
            adressUserModel = getIntent().getParcelableExtra(AllFinal.INTENT_ADRESS);
            total_price = getIntent().getDoubleExtra(AllFinal.INTENT_TOTAL, 0.0);
            productModelArrayList = getIntent().getParcelableArrayListExtra(AllFinal.ALL_ITEM_USER_BUY);

            init();

            fillData();

            actions();
        } else {
            Toast.makeText(this, "error try again later!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }


    }

    private void fillData() {
        txt_end_name.setText("Name : " + adressUserModel.getName());
        txt_end_phone.setText("Phone : " + adressUserModel.getUser_id());
        txt_end_loction.setText("Loction : " + adressUserModel.getLoction_name());

        txt_end_delvery_totall.setText("EGP " + total_price);

        txt_end_subtotal.setText("EGP " + total_price);

        Double d = total_price + 46.0;
        txt_end_total.setText("EGP " + d + " ");


    }

    private void actions() {

        btn_end_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                if (connectivityManager.getActiveNetworkInfo() != null) {
                    if (typr_payment.equals("")) {
                        Toast.makeText(EndOrderActivity.this, "Select payment method please !", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    progressDialog.show();

                    endOrder();

                } else {
                    Toast.makeText(EndOrderActivity.this, "check your inyternet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        radio_payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_cash) {
                    typr_payment = AllFinal.PAYMENT_CASH;

                } else {
                    Toast.makeText(EndOrderActivity.this, "coming soon ", Toast.LENGTH_SHORT).show();
                    typr_payment = AllFinal.PAYMENT_BY_CARD;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            group.check(R.id.radio_cash);
                        }
                    }, 1000);

                }
            }
        });

    }

    private void endOrder() {

        ref_start_order.child(prefViewModel.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                    String id_buyer = productModel.getOwner();
                    String id_end[] = id_buyer.split("@");
                    id_buyer = id_end[0];

                    EndOrderModel endOrderModel = new EndOrderModel(productModel.getId()
                            , adressUserModel, typr_payment, total_price.toString(), new Date(), productModel.getOwner());
                    ref_end_order.child(prefViewModel.getPhone())
                            .child(id_buyer).push()
                            .child(productModel.getId()).setValue(endOrderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("yyyyyyyyy", "onSuccess: ");


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EndOrderActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });


                }
                EndOrderModel orderModel = new EndOrderModel(prefViewModel.getPhone()
                        , adressUserModel, typr_payment, total_price.toString()
                        , new Date(), null);
                ref_collection.child(prefViewModel.getPhone())
                        .push().setValue(orderModel);

                long max = snapshot.getChildrenCount();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("yyyyyyyyy", "done: ");
                        myCartViewModel.deleteProductToCar(cartItemModel);
                        Intent intent = new Intent(getApplicationContext(), LaunchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }, (max + 1) * 500);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EndOrderActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }

    private void init() {
        txt_end_name = findViewById(R.id.txt_end_name);
        txt_end_phone = findViewById(R.id.txt_end_phone);
        txt_end_loction = findViewById(R.id.txt_end_loction);
        txt_end_delvery_totall = findViewById(R.id.txt_end_delvery_totall);
        txt_end_subtotal = findViewById(R.id.txt_end_subtotal);
        txt_end_shipping = findViewById(R.id.txt_end_shipping);
        txt_end_total = findViewById(R.id.txt_end_total);
        btn_end_confirm = findViewById(R.id.btn_end_confirm);
        radio_payment = findViewById(R.id.radio_payment);

        progressDialog = new ProgressDialog(EndOrderActivity.this);
        progressDialog.setMessage("Wait please!");
        progressDialog.setCancelable(false);

        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        myCartViewModel = new ViewModelProvider(this).get(MyCartViewModel.class);

        cartItemModel = new CartItemModel(prefViewModel.getPhone(), productModelArrayList);
    }
}
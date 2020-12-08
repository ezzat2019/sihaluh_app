package com.example.sihaluh.ui.end_oreder;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.AdressUserModel;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.DataNotifictionFragModel;
import com.example.sihaluh.data.model.EndOrderModel;
import com.example.sihaluh.data.model.MyResponse;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.data.model.SenderFragNotifictionModel;
import com.example.sihaluh.data.model.TokkenModel;
import com.example.sihaluh.ui.end_oreder.viewmodel.EndOrderViewModel;
import com.example.sihaluh.ui.home.fagement.cart.viewmodel.MyCartViewModel;
import com.example.sihaluh.ui.launch.LaunchActivity;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Response;

@AndroidEntryPoint
public class EndOrderActivity extends AppCompatActivity {

    // firebase
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference ref_end_order = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_ENDORDER);
    private final DatabaseReference ref_notification = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_Notifcation);
    private final DatabaseReference ref_collection = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_ORDER_COLLECTION);
    private final DatabaseReference ref_start_order = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_STARORDER);
    private final DatabaseReference ref_tokken = firebaseDatabase.getReference().child(AllFinal.FIREBASE_TOKKENS);

    // ui
    private TextView txt_end_name, txt_end_phone, txt_end_loction, txt_end_delvery_totall, txt_end_subtotal, txt_end_shipping, txt_end_total;
    private Button btn_end_confirm;
    private RadioGroup radio_payment;


    // var
    private AdressUserModel adressUserModel;
    private EndOrderViewModel endOrderViewModel;
    private Double total_price;
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    private final int i = 0;
    private PrefViewModel prefViewModel;
    private String typr_payment = AllFinal.PAYMENT_CASH;
    private MyCartViewModel myCartViewModel;
    private CartItemModel cartItemModel;
    private View end_order_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_order);

        if (getIntent().hasExtra(AllFinal.INTENT_ADRESS)) {
            adressUserModel = getIntent().getParcelableExtra(AllFinal.INTENT_ADRESS);
            total_price = getIntent().getDoubleExtra(AllFinal.INTENT_TOTAL, 0.0);
            productModelArrayList = getIntent().getParcelableArrayListExtra(AllFinal.ALL_ITEM_USER_BUY);

            init();

            observeResponse();
            fillData();

            actions();
        } else {
            Toast.makeText(this, "error try again later!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }


    }

    private void observeResponse() {
        if (endOrderViewModel!=null)
        {
            endOrderViewModel.getResponseMutableLiveData().observe(this, new Observer<Response<MyResponse>>() {
                @Override
                public void onChanged(Response<MyResponse> myResponseResponse) {
                    if (myResponseResponse.code()==200)
                    {
                        if (myResponseResponse.body().success!=1)
                        {
                            Toast.makeText(EndOrderActivity.this, "error in sending notification", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
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


                    end_order_progress.setVisibility(View.VISIBLE);

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
        end_order_progress = findViewById(R.id.end_order_progress);
        end_order_progress.setVisibility(View.GONE);


        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        myCartViewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        endOrderViewModel=new ViewModelProvider(this).get(EndOrderViewModel.class);

        cartItemModel = new CartItemModel(prefViewModel.getPhone(), productModelArrayList);
    }

    private void endOrder() {

        ref_start_order.child(prefViewModel.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                    String id_buyer = productModel.getOwner();
                    String[] id_end = id_buyer.split("@");
                    id_buyer = id_end[0];

                    EndOrderModel endOrderModel = new EndOrderModel(productModel.getId()
                            , adressUserModel, typr_payment, total_price.toString(), new Date(), productModel.getOwner());
                    ref_end_order.child(prefViewModel.getPhone())
                            .child(id_buyer).push()
                            .child(productModel.getId()).setValue(endOrderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("yyyyyyyyy", "onSuccess: ");

                            sendNotification(productModel.getImg(), productModel.getOwner()
                                    , prefViewModel.getPhone(), productModel.getName(), total_price.toString());


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EndOrderActivity.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                            end_order_progress.setVisibility(View.GONE);
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
                        end_order_progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }, (max + 1) * 500);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EndOrderActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                end_order_progress.setVisibility(View.GONE);

            }
        });


    }

    private void sendNotification(String img, String owner, String phone, String name, String total_price) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("img", img);
        map.put("from", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("to", owner);
        map.put("name_product", name);
        map.put("total_price", total_price);
        Date date = new Date();
        map.put("date", date);


        ref_notification.child(owner).push()
                .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                sendNotificationFCM(FirebaseAuth.getInstance().getCurrentUser().getUid(), owner, name, total_price);

            }
        });


    }

    private void sendNotificationFCM(String uid, String owner, String name, String total_price) {

        ref_tokken.child(owner).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TokkenModel tokkenModel=snapshot.getValue(TokkenModel.class);
                DataNotifictionFragModel model = new DataNotifictionFragModel(uid, owner, name, total_price);

                SenderFragNotifictionModel senderFragNotifictionModel=new SenderFragNotifictionModel(tokkenModel.getTokken()
                ,model);
                endOrderViewModel.setNotifiction(senderFragNotifictionModel);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
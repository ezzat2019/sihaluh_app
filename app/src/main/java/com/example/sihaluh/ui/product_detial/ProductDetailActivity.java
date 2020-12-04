package com.example.sihaluh.ui.product_detial;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.sihaluh.BuildConfig;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.data.model.RegisterModel;
import com.example.sihaluh.data.model.UserChatAddModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.cart.viewmodel.MyCartViewModel;
import com.example.sihaluh.ui.message_chat.MessageActivity;
import com.example.sihaluh.ui.product_detial.fragment.FullImageFragment;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailActivity extends AppCompatActivity {

    // firebase
    private final DatabaseReference ref_users = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_USER_CHAT);
    private final DatabaseReference ref_login = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_DATABASE_LOGIN);

    // ui
    private ImageView img_detail_item;
    private final ArrayList<RegisterModel> registerModelArrayList = new ArrayList<>();
    private TextView txt_detail_name, txt_detial_price;
    private ImageButton img_chat;
    private Button btn_detail_add_cart;
    private CardView card_non_empty;
    private ImageView img_detial_back, img_detail_cart, img_detail_share;

    // var
    private ProductModel productModel;
    private String final_price = "";
    private MyCartViewModel myCartViewModel;
    private String user_id = "";
    private PrefViewModel prefViewModel;
    private ArrayList<ProductModel> productModelArrayList;
    private CartItemModel cartItemModeltest;
    private FullImageFragment fullImageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_product_detail);

        if (getIntent().hasExtra(AllFinal.INTENT_PRODUCT_DETIAL)) {
            productModel = getIntent().getParcelableExtra(AllFinal.INTENT_PRODUCT_DETIAL);
            Log.d("ProductDetailActivity", "onCreate: " + productModel.toString());

            init();

            fillProductDetail();

            actions();
        } else {
            Toast.makeText(this, "not found try again!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }


    }

    private void getUserId() {
        user_id = prefViewModel.getPhone();
    }

    private void fillProductDetail() {
        Glide.with(getApplicationContext())
                .load(productModel.getImg())
                .into(img_detail_item);

        txt_detail_name.setText(productModel.getName());
        calculateSale();
    }

    private void calculateSale() {
        String price = productModel.getPrice();
        if (productModel.getSale().equals("1")) {
            String ss = String.format("%.2f", Float.parseFloat(price));
            final_price = ss;
            txt_detial_price.setText(ss + " SAR ");
        } else {
            String sale = productModel.getSale();
            Double sale_result = Double.parseDouble(price) / Double.parseDouble(sale);
            Double final_price2 = Double.parseDouble(price) - sale_result;

            String ss = String.format("%.2f", Float.parseFloat(final_price2.toString()));
            final_price = ss;
            txt_detial_price.setText(ss + " SAR ");
        }
    }

    private void actions() {

        img_detial_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_detail_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(AllFinal.INTENT_GOTO_CART, true);
                startActivity(intent);
            }
        });
        img_detail_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!productModel.getImg().isEmpty()) {
                    fullImageFragment = FullImageFragment.newInstance(productModel.getImg());
                    fullImageFragment.show(getSupportFragmentManager(), "im1");
                }

            }
        });

        img_detail_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my product " + productModel.getName() + " " + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                if (connectivityManager.getActiveNetworkInfo() != null) {
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(productModel.getOwner())) {
                        Toast.makeText(ProductDetailActivity.this, "not avaible!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(getApplicationContext(), MessageActivity.class).putExtra(AllFinal.REF_SELLER_ID, productModel));

                        addNewUser();


                    }
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_detail_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();

            }
        });


    }

    private void addNewUser() {
        ref_login.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserChatAddModel chatAddModel = new UserChatAddModel();

                    RegisterModel registerModel = snapshot.getValue(RegisterModel.class);
                    chatAddModel.setCurrent_user_id(registerModel.getId());
                    chatAddModel.setCurrent_user_img(registerModel.getImg_url());
                    chatAddModel.setCurrent_user_phone(registerModel.getPhone());
                    chatAddModel.setName(registerModel.getName());


                    ref_login.
                            child(productModel.getPhone())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        RegisterModel registerModel2 = snapshot.getValue(RegisterModel.class);
                                        registerModel2.setName(productModel.getName());
                                        registerModel2.setImg_url(productModel.getImg());
                                        ref_users.child(registerModel.getId())
                                                .child("users_added").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                if (snapshot.exists()) {
                                                    if (snapshot.hasChildren()) {
                                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                            RegisterModel r = dataSnapshot.getValue(RegisterModel.class);

                                                            registerModelArrayList.add(r);
                                                        }
                                                        registerModelArrayList.add(registerModel2);
                                                        chatAddModel.setUsers_added(registerModelArrayList);
                                                        ref_users.child(registerModel.getId())
                                                                .setValue(chatAddModel);

                                                    }
                                                } else {


                                                    registerModelArrayList.add(registerModel2);
                                                    chatAddModel.setUsers_added(registerModelArrayList);
                                                    ref_users.child(registerModel.getId())
                                                            .setValue(chatAddModel);


                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addtoCart() {


        if (productModelArrayList == null || productModelArrayList.isEmpty()) {
            productModelArrayList = new ArrayList<>();

        }


        for (ProductModel p : productModelArrayList) {

            if (p.getId().equals(productModel.getId())) {
                Toast.makeText(this, "this product already exsit!", Toast.LENGTH_SHORT).show();
                return;
            }


        }

        productModel.setPrice(final_price);
        productModelArrayList.add(productModel);
        CartItemModel cartItemModel = new CartItemModel(user_id, productModelArrayList);

        myCartViewModel.addProductToCar(cartItemModel);
        Toast.makeText(ProductDetailActivity.this, "Added to your cart", Toast.LENGTH_SHORT).show();


    }

    private void init() {
        img_detail_item = findViewById(R.id.img_detail_item);
        img_detial_back = findViewById(R.id.img_detial_back);
        img_detail_cart = findViewById(R.id.img_detail_cart);
        img_detail_share = findViewById(R.id.img_detail_share);

        txt_detail_name = findViewById(R.id.txt_detail_name);
        txt_detial_price = findViewById(R.id.txt_detial_price);

        img_chat = findViewById(R.id.img_chat);

        btn_detail_add_cart = findViewById(R.id.btn_detail_add_cart);

        card_non_empty = findViewById(R.id.card_non_empty);

        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);

        myCartViewModel = new ViewModelProvider(this).get(MyCartViewModel.class);

        getUserId();
        showCardView(false);
        getCartProducts();
    }

    private void showCardView(Boolean b) {
        if (b) {
            card_non_empty.setVisibility(View.VISIBLE);
        } else {
            card_non_empty.setVisibility(View.GONE);
        }
    }

    private void getCartProducts() {
        if (myCartViewModel.getMyCartProducts(user_id) != null) {
            myCartViewModel.getMyCartProducts(user_id).observe(this,
                    new Observer<CartItemModel>() {
                        @Override
                        public void onChanged(CartItemModel cartItemModel) {
                            if (cartItemModel != null) {
                                cartItemModeltest = cartItemModel;
                                productModelArrayList = (ArrayList<ProductModel>) cartItemModel.getProductModelList();
                                showCardView(productModelArrayList.size() != 0);
                                Log.d("nnnnnnnn", "onChanged: " + productModelArrayList.size());
                                Log.d("nnnnnnnn", "onChanged: " + productModelArrayList.toString());
                            }

                        }
                    });
        }

    }
}
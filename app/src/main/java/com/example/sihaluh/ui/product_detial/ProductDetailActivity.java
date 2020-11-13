package com.example.sihaluh.ui.product_detial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.sihaluh.BuildConfig;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.cart.viewmodel.MyCartViewModel;
import com.example.sihaluh.ui.product_detial.fragment.FullImageFragment;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.github.chrisbanes.photoview.PhotoView;


import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProductDetailActivity extends AppCompatActivity {
    // ui
    private ImageView img_detail_item;
    private ImageView  img_detial_back, img_detail_cart, img_detail_share;
    private TextView txt_detail_name, txt_detial_price;
    private ImageButton img_chat;
    private Button btn_detail_add_cart;
    private CardView card_non_empty;



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
                if (!productModel.getImg().isEmpty())
                {
                    fullImageFragment=FullImageFragment.newInstance(productModel.getImg());
                    fullImageFragment.show(getSupportFragmentManager(),"im1");
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
                Toast.makeText(ProductDetailActivity.this, "img_chat", Toast.LENGTH_SHORT).show();
            }
        });

        btn_detail_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();

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
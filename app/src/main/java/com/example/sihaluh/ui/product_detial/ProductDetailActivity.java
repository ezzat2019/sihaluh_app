package com.example.sihaluh.ui.product_detial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.utils.AllFinal;

public class ProductDetailActivity extends AppCompatActivity {
    // ui
    private ImageView img_detail_item,img_detial_back,img_detail_cart,img_detail_share;
    private TextView txt_detail_name,txt_detial_price;
    private ImageButton img_chat;
    private Button btn_detail_add_cart;


    // var
    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_product_detail);

        if (getIntent().hasExtra(AllFinal.INTENT_PRODUCT_DETIAL))
        {
            productModel=getIntent().getParcelableExtra(AllFinal.INTENT_PRODUCT_DETIAL);
            Log.d("ProductDetailActivity", "onCreate: "+productModel.toString());

            init();

            fillProductDetail();

            actions();
        }
        else
        {
            Toast.makeText(this, "not found try again!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }


    }

    private void fillProductDetail() {
        Glide.with(getApplicationContext())
                .load(productModel.getImg())
                .into(img_detail_item);

        txt_detail_name.setText(productModel.getName());
        calculateSale();
    }

    private void calculateSale() {
        String price=productModel.getPrice();
        if (productModel.getSale().equals("1"))
        {
            String ss=String.format("%.2f",Float.parseFloat(price));
            txt_detial_price.setText(ss+" SAR ");
        }
        else
        {
            String sale=productModel.getSale();
            Double sale_result=Double.parseDouble(price)/Double.parseDouble(sale);
            Double final_price=Double.parseDouble(price)-sale_result;
            String ss=String.format("%.2f",Float.parseFloat(final_price.toString()));
            txt_detial_price.setText(ss+" SAR ");
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
                Toast.makeText(ProductDetailActivity.this, "img_detail_cart", Toast.LENGTH_SHORT).show();
            }
        });

        img_detail_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetailActivity.this, "img_detail_share", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ProductDetailActivity.this, "btn_detail_add_cart", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void init() {
        img_detail_item=findViewById(R.id.img_detail_item);
        img_detial_back=findViewById(R.id.img_detial_back);
        img_detail_cart=findViewById(R.id.img_detail_cart);
        img_detail_share=findViewById(R.id.img_detail_share);

        txt_detail_name=findViewById(R.id.txt_detail_name);
        txt_detial_price=findViewById(R.id.txt_detial_price);

        img_chat=findViewById(R.id.img_chat);

        btn_detail_add_cart=findViewById(R.id.btn_detail_add_cart);



    }
}
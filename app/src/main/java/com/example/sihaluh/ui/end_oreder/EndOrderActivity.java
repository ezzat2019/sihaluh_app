package com.example.sihaluh.ui.end_oreder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.AdressUserModel;
import com.example.sihaluh.utils.AllFinal;

public class EndOrderActivity extends AppCompatActivity {
    // ui
    private TextView txt_end_name, txt_end_phone, txt_end_loction, txt_end_delvery_totall, txt_end_subtotal, txt_end_shipping, txt_end_total;
    private Button btn_end_confirm;
    private RadioGroup radio_payment;

    // var
    private AdressUserModel adressUserModel;
    private Double total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_end_order);

        if (getIntent().hasExtra(AllFinal.INTENT_ADRESS)) {
            adressUserModel = getIntent().getParcelableExtra(AllFinal.INTENT_ADRESS);
            total_price = getIntent().getDoubleExtra(AllFinal.INTENT_TOTAL, 0.0);

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

            }
        });

        radio_payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_cash) {
                    Toast.makeText(EndOrderActivity.this, "cash", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EndOrderActivity.this, "payment", Toast.LENGTH_SHORT).show();
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

    }
}
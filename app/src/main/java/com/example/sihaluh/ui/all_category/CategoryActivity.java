package com.example.sihaluh.ui.all_category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CategoriesModel;
import com.example.sihaluh.ui.all_category.adapter.CategoryAllRecAdapter;
import com.example.sihaluh.ui.category_items.CategoryItemsActivity;
import com.example.sihaluh.ui.product_detial.ProductDetailActivity;
import com.example.sihaluh.utils.AllFinal;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    // ui
    private RecyclerView rec_cat;
    private TextView txt_result;
    private ImageView img_cat_back;


    // var
    private CategoryAllRecAdapter categoryAllRecAdapter;
    private ArrayList<CategoriesModel> categoriesModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_category);



        if (getIntent().hasExtra(AllFinal.INTENT_ALLCATEGORY)) {
            categoriesModelList = getIntent().getParcelableArrayListExtra(AllFinal.INTENT_ALLCATEGORY);
            if (categoriesModelList.isEmpty()) {
                Toast.makeText(this, "no found any agtegort try again later !", Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                init();

                actions();

                fillRecycle();
            }
        } else {
            Toast.makeText(this, "no found any agtegort try again later !", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

    }

    private void actions() {
        categoryAllRecAdapter.setOnItemCatClick(new CategoryAllRecAdapter.OnItemCatAllListener() {
            @Override
            public void onClick(int pos) {
                Intent intent=new Intent(getApplicationContext(), CategoryItemsActivity.class);
                intent.putExtra(AllFinal.INTENT_CATEGORYNAME,categoriesModelList.get(pos).getId());
                intent.putExtra(AllFinal.INTENT_CATEGORYTOOLBAR,categoriesModelList.get(pos).getName());

                startActivity(intent);
            }
        });

        img_cat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fillRecycle() {

        categoryAllRecAdapter.addAllCategory(categoriesModelList);
        txt_result.setText("Result ( " + categoriesModelList.size() + " ) ");
    }

    private void init() {
        txt_result = findViewById(R.id.txt_result_cat_acycity);

        rec_cat = findViewById(R.id.rec_category_activity);
        rec_cat.setHasFixedSize(true);
        rec_cat.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        rec_cat.setNestedScrollingEnabled(false);
        rec_cat.setLayoutManager(new GridLayoutManager(this
                , 3));

        categoryAllRecAdapter = new CategoryAllRecAdapter();
        rec_cat.setAdapter(categoryAllRecAdapter);

        img_cat_back = findViewById(R.id.img_cat_back);
    }


}
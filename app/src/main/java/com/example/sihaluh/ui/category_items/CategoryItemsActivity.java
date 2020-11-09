package com.example.sihaluh.ui.category_items;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CategoryItemModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.category_items.viewmodel.CategoryItemViewmodel;
import com.example.sihaluh.ui.home.fagement.home.adapters.ProductRecycleAdapter;
import com.example.sihaluh.ui.product_detial.ProductDetailActivity;
import com.example.sihaluh.utils.AllFinal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoryItemsActivity extends AppCompatActivity {

    // ui
    private RecyclerView rec_product;
    private TextView txt_cat_product_name;
    private ImageView img_cat_product_back;
    private ProgressBar progress_category_item;


    // var
    private ProductRecycleAdapter productRecycleAdapter;
    private String catgory_id;
    private List<ProductModel> productModelList = new ArrayList<>();
    private CategoryItemViewmodel categoryItemViewmodel;
    private DatabaseReference products_ref = FirebaseDatabase.getInstance().getReference().child(AllFinal.CATEGORIES);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_category_items);

        if (!getIntent().hasExtra(AllFinal.INTENT_CATEGORYNAME)) {
            Toast.makeText(getApplicationContext(), "no items found try again later!", Toast.LENGTH_SHORT).show();
            onBackPressed();

        } else {
            catgory_id = getIntent().getStringExtra(AllFinal.INTENT_CATEGORYNAME);
            init();

            actions();

            retriveDataFillRec();

        }



    }

    private void retriveDataFillRec() {
        products_ref.child(catgory_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ProductModel productModel = new ProductModel();
                    productModel.setId(snapshot1.child("id").getValue().toString());
                    productModel.setName(snapshot1.child("name").getValue().toString());
                    productModel.setImg(snapshot1.child("img").getValue().toString());
                    productModel.setPrice(snapshot1.child("price").getValue().toString());
                    productModel.setOwner(snapshot1.child("owner").getValue().toString());
                    productModel.setSale(snapshot1.child("sale").getValue().toString());
                    productModelList.add(productModel);
                }
                CategoryItemModel model=new CategoryItemModel(catgory_id,productModelList);
                categoryItemViewmodel.addCategory(model);
                categoryItemViewmodel.setProductModelListLiveData(productModelList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoryItemsActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });
    }

    private void actions() {
        productRecycleAdapter.setOnItemProductclick(new ProductRecycleAdapter.onProductClickListener() {
            @Override
            public void onClick(int posOfProduct) {
               gotoProdutDeatial(posOfProduct);
            }
        });

        img_cat_product_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        rec_product = findViewById(R.id.rec_products);
        rec_product.setHasFixedSize(true);
        rec_product.setLayoutManager(new GridLayoutManager(this, 2));

        productRecycleAdapter = new ProductRecycleAdapter();
        rec_product.setAdapter(productRecycleAdapter);

        txt_cat_product_name = findViewById(R.id.txt_cat_product_name);

        setToolBarName();

        img_cat_product_back = findViewById(R.id.img_cat_product_back);

        progress_category_item = findViewById(R.id.progress_category_item);
        showProgress(true);

        observeViewModel();
    }

    private void observeViewModel() {
        categoryItemViewmodel=new ViewModelProvider(this).get(CategoryItemViewmodel.class);

        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo()!=null)
        {
            categoryItemViewmodel.getProductModelListLiveData()
                    .observe(this, new Observer<List<ProductModel>>() {
                        @Override
                        public void onChanged(List<ProductModel> productModels) {
                            productRecycleAdapter.addProducts(productModels);
                            showProgress(false);
                        }
                    });

        }
        else
        {
            categoryItemViewmodel.getCategoryModel(catgory_id).observe(this
                    , new Observer<CategoryItemModel>() {
                        @Override
                        public void onChanged(CategoryItemModel categoryItemModel) {

                            productRecycleAdapter.addProducts(categoryItemModel.getProductModelList());
                            showProgress(false);
                            productModelList=categoryItemModel.getProductModelList();
                        }
                    });
        }



    }
    private void gotoProdutDeatial(int posOfProduct) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
        intent.putExtra(AllFinal.INTENT_PRODUCT_DETIAL, productModelList.get(posOfProduct));
        startActivity(intent);
    }
    private void showProgress(Boolean show) {
        if (show) {
            rec_product.setVisibility(View.INVISIBLE);
            progress_category_item.setVisibility(View.VISIBLE);
        } else {
            rec_product.setVisibility(View.VISIBLE);
            progress_category_item.setVisibility(View.GONE);

        }
    }

    private void setToolBarName() {
        txt_cat_product_name.setText(getIntent().getStringExtra(AllFinal.INTENT_CATEGORYTOOLBAR));
    }
}
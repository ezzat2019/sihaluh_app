package com.example.sihaluh.ui.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.product_detial.ProductDetailActivity;
import com.example.sihaluh.ui.search.adapters.SearchRecAdapter;
import com.example.sihaluh.utils.AllFinal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    // ui
    private SearchView search_full;
    private RecyclerView rec_search;


    // var
    private SearchRecAdapter searchRecAdapter;
    private List<ProductModel> productModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getIntent().getParcelableArrayListExtra(AllFinal.SREARCH_PRODUCTS) == null) {
            onBackPressed();
        } else {
            productModelList = getIntent().getParcelableArrayListExtra(AllFinal.SREARCH_PRODUCTS);

            init();
            actions();
        }
    }

    private void actions() {
        search_full.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchRecAdapter.setOnItemSearchClick(new SearchRecAdapter.OnItemSearchClick() {
            @Override
            public void onclick(int pos) {
                gotoProdutDeatial(pos);
            }
        });
    }

    private void gotoProdutDeatial(int posOfProduct) {
        Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
        intent.putExtra(AllFinal.INTENT_PRODUCT_DETIAL, searchRecAdapter.getList().get(posOfProduct));
        startActivity(intent);

    }

    private void init() {
        search_full = findViewById(R.id.search_full_item);

        rec_search = findViewById(R.id.rec_search);
        buildRec();


    }

    private void buildRec() {
        rec_search.setHasFixedSize(true);
        rec_search.setLayoutManager(new LinearLayoutManager(this));

        searchRecAdapter = new SearchRecAdapter();
        rec_search.setAdapter(searchRecAdapter);
        searchRecAdapter.setList(productModelList);
    }
}
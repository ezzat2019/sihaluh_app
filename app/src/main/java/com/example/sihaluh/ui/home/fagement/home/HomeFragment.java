package com.example.sihaluh.ui.home.fagement.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CategoriesModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.all_category.CategoryActivity;
import com.example.sihaluh.ui.category_items.CategoryItemsActivity;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.home.adapters.CategoryAdapter;
import com.example.sihaluh.ui.home.fagement.home.adapters.ProductRecycleAdapter;
import com.example.sihaluh.ui.home.fagement.home.viewmodel.HomeViewModel;
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
public class HomeFragment extends Fragment {
    // ui
    private RecyclerView rec_cat, rec_product;
    private TextView txt_home_result, txt_home_more;


    // var
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference_cat = database.getReference().child(AllFinal.CATEGORIES);
    private ArrayList<CategoriesModel> categoriesModelList = new ArrayList<>();
    private List<ProductModel> productModelList = new ArrayList<>();
    private HomeViewModel homeViewModel;
    private CategoryAdapter categoryAdapter;

    private ProductRecycleAdapter productRecycleAdapter;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        categoriesModelList.add(new CategoriesModel("Cars", "https://images7.alphacoders.com/594/thumb-350-594890.jpg", "cars"));
        categoriesModelList.add(new CategoriesModel("Food",
                "https://as2.ftcdn.net/jpg/01/81/12/43/500_F_181124352_Xq5FiYmW8ukX96kM1YLvN5zoUxFLFSlc.jpg", "food"));
        categoriesModelList.add(new CategoriesModel("Furnitures",
                "https://miro.medium.com/max/1193/1*93DXyTsmbLo5ly39wGZTQQ.jpeg", "furniture"));
        categoriesModelList.add(new CategoriesModel("Smart Screen",
                "https://previews.123rf.com/images/monicaodo/monicaodo1407/monicaodo140700005/30607440-application-coming-out-from-smart-tv-on-white-background.jpg",
                "smart_screens"));
        categoriesModelList.add(new CategoriesModel("Mobile Market",
                "https://thumbs.dreamstime.com/z/collection-modern-touchscreen-smartphones-creative-abstract-mobile-phone-wireless-communication-technology-mobility-51212846.jpg", "the_mobile_market"));




    }

    private Boolean checkInternetConncertion()
    {
        ConnectivityManager networkRequest= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (networkRequest.getActiveNetworkInfo()!=null)
        {
            return true;
        }
        return false;
    }
    private void obsereViewModel() {
        if (checkInternetConncertion())
        {

            homeViewModel.getMutableLiveData().observe(this, new Observer<List<ProductModel>>() {
                @Override
                public void onChanged(List<ProductModel> productModels) {
                    productRecycleAdapter.addProducts(productModels);
                }
            });
        }
        else
        {
            homeViewModel.getProductOffline().observe(this
                    , new Observer<List<ProductModel>>() {
                        @Override
                        public void onChanged(List<ProductModel> productModelList) {
                            productRecycleAdapter.addProducts(productModelList);
                            homeViewModel.setResult_num(productModelList.size());
                        }
                    });
        }


        homeViewModel.getResult_num().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txt_home_result.setText("Result ( " + s + " ) ");
            }
        });


    }

    private void getProducts() {

        for (int h = 0; h < categoriesModelList.size(); h++) {
            String id_cat = categoriesModelList.get(h).getId();

            Log.d("gggggggg", "onDataChange:22 " + id_cat);
            reference_cat.child(id_cat).addListenerForSingleValueEvent(new ValueEventListener() {
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

                       homeViewModel.setNewroduct(productModel);
                        productModelList.add(productModel);


                    }
                    homeViewModel.setMutableLiveData(productModelList);
                    homeViewModel.setResult_num(productModelList.size());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        getProducts();

        obsereViewModel();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        actions();


    }

    private void actions() {
        categoryAdapter.setOnItemCatListener(new CategoryAdapter.OnItemCatListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(getContext(), CategoryItemsActivity.class);
                intent.putExtra(AllFinal.INTENT_CATEGORYNAME, categoriesModelList.get(pos).getId());
                intent.putExtra(AllFinal.INTENT_CATEGORYTOOLBAR, categoriesModelList.get(pos).getName());

                startActivity(intent);
            }
        });

        productRecycleAdapter.setOnItemProductclick(new ProductRecycleAdapter.onProductClickListener() {
            @Override
            public void onClick(int posOfProduct) {
                Toast.makeText(getContext(), posOfProduct + "", Toast.LENGTH_SHORT).show();
            }
        });

        txt_home_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putParcelableArrayListExtra(AllFinal.INTENT_ALLCATEGORY, categoriesModelList);
                startActivity(intent);
            }
        });
    }


    private void init(View view) {
        rec_cat = view.findViewById(R.id.rec_category);
        rec_cat.setNestedScrollingEnabled(false);
        rec_cat.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        rec_cat.setLayoutManager(gridLayoutManager);

        categoryAdapter = new CategoryAdapter();
        categoryAdapter.addCategory(categoriesModelList);
        rec_cat.setAdapter(categoryAdapter);

        // product recycle
        rec_product = view.findViewById(R.id.rec_products);
        rec_product.setNestedScrollingEnabled(false);
        // rec_product.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rec_product.setLayoutManager(gridLayoutManager2);

        productRecycleAdapter = new ProductRecycleAdapter();

        rec_product.setAdapter(productRecycleAdapter);

        txt_home_result = view.findViewById(R.id.txt_home_result);

        txt_home_more = view.findViewById(R.id.txt_home_more);


    }


    @Override
    public void onStart() {
        super.onStart();
        HomeActivity.txt_name_bar.setText(getString(R.string.title_home));

    }

}
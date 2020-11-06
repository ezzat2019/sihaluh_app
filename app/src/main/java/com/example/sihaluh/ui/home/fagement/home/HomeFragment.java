package com.example.sihaluh.ui.home.fagement.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.home.adapters.CategoryAdapter;
import com.example.sihaluh.utils.AllFinal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class HomeFragment extends Fragment {
    // ui
    private RecyclerView rec_cat;



    // var
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference reference_cat=database.getReference().child(AllFinal.CATEGORIES);
    private List<CategoriesModel> categoriesModelList=new ArrayList<>();
    private List<ProductModel>productModelList=new ArrayList<>();
    private HashMap<String,ProductModel>hashMap=new LinkedHashMap<>();
    private CategoryAdapter categoryAdapter;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        categoriesModelList.add(new CategoriesModel("Cars","https://images7.alphacoders.com/594/thumb-350-594890.jpg","cars"));
        categoriesModelList.add(new CategoriesModel("Food",
                "https://as2.ftcdn.net/jpg/01/81/12/43/500_F_181124352_Xq5FiYmW8ukX96kM1YLvN5zoUxFLFSlc.jpg","food"));
        categoriesModelList.add(new CategoriesModel("Furnitures",
                "https://miro.medium.com/max/1193/1*93DXyTsmbLo5ly39wGZTQQ.jpeg","furniture"));
        categoriesModelList.add(new CategoriesModel("Smart Screen",
                "https://previews.123rf.com/images/monicaodo/monicaodo1407/monicaodo140700005/30607440-application-coming-out-from-smart-tv-on-white-background.jpg",
                "smart_screens"));
        categoriesModelList.add(new CategoriesModel("Mobile Market",
                "https://images7.alphacoders.com/594/thumb-350-594890.jpg","the_mobile_market"));


        getProducts();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);


    }

    private void init(View view) {
        rec_cat=view.findViewById(R.id.rec_category);
        rec_cat.setNestedScrollingEnabled(false);
        rec_cat.setHasFixedSize(true);
        rec_cat.setLayoutManager(new GridLayoutManager(getContext(),2));

        categoryAdapter=new CategoryAdapter();
        categoryAdapter.addCategory(categoriesModelList);
        rec_cat.setAdapter(categoryAdapter);
    }

    private void getProducts() {

        for ( CategoriesModel categoriesModel:categoriesModelList)
        {
            String id_cat=categoriesModel.getId();

            Log.d("gggggggg", "onDataChange:22 "+id_cat);
            reference_cat.child(id_cat).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1:snapshot.getChildren())
                    {

                        ProductModel productModel=new ProductModel();
                        productModel.setId(snapshot1.child("id").getValue().toString());
                        productModel.setName(snapshot1.child("name").getValue().toString());
                        productModel.setImg(snapshot1.child("img").getValue().toString());
                        productModel.setPrice(snapshot1.child("price").getValue().toString());
                        productModel.setOwner(snapshot1.child("owner").getValue().toString());
                        productModel.setSale(snapshot1.child("sale").getValue().toString());



                        productModelList.add(productModel);
                        hashMap.put(id_cat,productModel);

                    }





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

    @Override
    public void onStart() {
        super.onStart();
        HomeActivity.txt_name_bar.setText(getString(R.string.title_home));

    }
}
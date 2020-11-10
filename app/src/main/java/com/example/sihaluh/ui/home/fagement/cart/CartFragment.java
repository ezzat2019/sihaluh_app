package com.example.sihaluh.ui.home.fagement.cart;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.cart.adapter.CartAdapter;
import com.example.sihaluh.ui.home.fagement.cart.viewmodel.MyCartViewModel;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment {
    // ui
    private NestedScrollView nestedScrollView;
    private ConstraintLayout view_include;
    private Button btn_cart_empty_continue;
    private RecyclerView rec_cart;
    private  TextView txt_total_price;


    // var
    private MyCartViewModel myCartViewModel;
    private PrefViewModel prefViewModel;
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private   Double totlal_price=0.0;
    private  int num;
    private boolean iam_remove=false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        actions();

    }

    private void actions() {

        btn_cart_empty_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.navigation_home);
            }
        });
        cartAdapter.setOnCartListerner(new CartAdapter.OnCartListerner() {
            @Override
            public void onAdd(int pos, TextView txt_num) {

                int num=Integer.parseInt(txt_num.getText().toString());
                num++;
                Double price=Double.parseDouble(productModelArrayList.get(pos).getPrice());
                totlal_price+=(price);
                txt_num.setText(num+"");
                txt_total_price.setText("EGP "+totlal_price);

            }

            @Override
            public void onminus(int pos, TextView txt_num) {
                 num=Integer.parseInt(txt_num.getText().toString());
                if (num==1)
                {
                    return;
                }
                num--;
                Double price=Double.parseDouble(productModelArrayList.get(pos).getPrice());
                totlal_price-=(price);
                txt_num.setText(num+"");
                txt_total_price.setText("EGP "+totlal_price);

            }

            @Override
            public void onremoveItem(int pos, TextView txt_num) {
                 num=Integer.parseInt(txt_num.getText().toString());

                Double price=Double.parseDouble(productModelArrayList.get(pos).getPrice());




                productModelArrayList.remove(pos);

                cartAdapter.notifyItemRemoved(pos);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iam_remove=true;
                        myCartViewModel.addProductToCar(new CartItemModel(prefViewModel.getPhone(),productModelArrayList));
                        totlal_price-=(num*price);
                        txt_total_price.setText("EGP "+totlal_price);
                    }
                },400);


            }
        });
    }

    private void init(View v) {
        nestedScrollView = v.findViewById(R.id.nestes_scroll_empty_cart);
        view_include = v.findViewById(R.id.include_cart);
        btn_cart_empty_continue = v.findViewById(R.id.btn_cart_empty_continue);
        txt_total_price=v.findViewById(R.id.txt_total_price);


        myCartViewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        rec_cart = v.findViewById(R.id.rec_cart_items);
        rec_cart.setHasFixedSize(true);
        rec_cart.setLayoutManager(new LinearLayoutManager(getContext()));


        buildRecycle();
        observViewModel();



    }

    private void buildRecycle() {

        cartAdapter = new CartAdapter();
        rec_cart.setAdapter(cartAdapter);
    }

    private void observViewModel() {
        if (myCartViewModel.getMyCartProducts(prefViewModel.getPhone()) != null) {
            myCartViewModel.getMyCartProducts(prefViewModel.getPhone())
                    .observe(getViewLifecycleOwner(), new Observer<CartItemModel>() {
                        @Override
                        public void onChanged(CartItemModel cartItemModel) {
                            if (cartItemModel != null) {
                                productModelArrayList = (ArrayList<ProductModel>) cartItemModel.getProductModelList();
                                cartAdapter.setProductModelList(productModelArrayList);
                                if (productModelArrayList.isEmpty()) {

                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    view_include.setVisibility(View.GONE);


                                } else {
                                    nestedScrollView.setVisibility(View.GONE);
                                    view_include.setVisibility(View.VISIBLE);

                                    calculateTotalPrice(productModelArrayList);





                                }
                            }
                        }
                    });
        }
    }

    private void calculateTotalPrice(ArrayList<ProductModel> productModelArrayList) {
        if (!iam_remove)
        {
            for (ProductModel productModel:productModelArrayList)
            {
                totlal_price+=Double.parseDouble(productModel.getPrice());
            }
            txt_total_price.setText("EGP "+totlal_price);

        }
        iam_remove=false;


    }


    @Override
    public void onStart() {
        super.onStart();
        HomeActivity.txt_name_bar.setText(getString(R.string.title_cart));

    }
}
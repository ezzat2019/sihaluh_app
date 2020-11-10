package com.example.sihaluh.ui.home.fagement.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.cart.viewmodel.MyCartViewModel;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    // ui

    private NestedScrollView nestedScrollView;
    private View view_include;
    private Button btn_cart_empty_continue;


    // var
    private MyCartViewModel myCartViewModel;
    private PrefViewModel prefViewModel;
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<>();


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
    }

    private void init(View v) {
        nestedScrollView = v.findViewById(R.id.nestes_scroll_empty_cart);
        view_include = v.findViewById(R.id.include_cart);
        btn_cart_empty_continue = v.findViewById(R.id.btn_cart_empty_continue);

        myCartViewModel=new ViewModelProvider(this).get(MyCartViewModel.class);
        prefViewModel=new ViewModelProvider(this).get(PrefViewModel.class);

        checkIsEmptyCart();

        observViewModel();


    }

    private void observViewModel() {
        if (myCartViewModel.getMyCartProducts(prefViewModel.getPhone())!=null)
        {
            myCartViewModel.getMyCartProducts(prefViewModel.getPhone())
                    .observe(getViewLifecycleOwner(), new Observer<CartItemModel>() {
                        @Override
                        public void onChanged(CartItemModel cartItemModel) {
                            if (cartItemModel!=null)
                            {

                            }
                        }
                    });
        }
    }

    private void checkIsEmptyCart() {
        if (productModelArrayList.isEmpty()) {

            nestedScrollView.setVisibility(View.VISIBLE);
            view_include.setVisibility(View.GONE);
        } else {
            nestedScrollView.setVisibility(View.GONE);
            view_include.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        HomeActivity.txt_name_bar.setText(getString(R.string.title_cart));

    }
}
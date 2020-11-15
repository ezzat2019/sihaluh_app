package com.example.sihaluh.ui.home.fagement.cart.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.repository.MyCartRepo;

public class MyCartViewModel extends ViewModel {

    private final MyCartRepo myCartRepo;

    @ViewModelInject
    public MyCartViewModel(MyCartRepo myCartRepo) {
        this.myCartRepo = myCartRepo;
    }

    public void addProductToCar(CartItemModel cartItemModel) {
        myCartRepo.addProductToCar(cartItemModel);
    }

    public void deleteProductToCar(CartItemModel cartItemModel) {
        myCartRepo.deleteProductToCar(cartItemModel);
    }
    public LiveData<CartItemModel> getMyCartProducts(String user_id) {
        return myCartRepo.getMyCartItems(user_id);
    }

}

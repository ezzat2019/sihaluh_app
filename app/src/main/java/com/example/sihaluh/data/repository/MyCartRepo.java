package com.example.sihaluh.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sihaluh.data.apis.CashDataDao;
import com.example.sihaluh.data.model.CartItemModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyCartRepo {

    private final CashDataDao cashDataDao;

    @Inject
    public MyCartRepo(CashDataDao cashDataDao) {
        this.cashDataDao = cashDataDao;
    }

    public void addProductToCar(CartItemModel cartItemModel) {

                cashDataDao.addProducttoCart(cartItemModel);

    }

    public void deleteProductToCar(CartItemModel cartItemModel) {

                cashDataDao.deleteProducttoCart(cartItemModel);


    }
    public LiveData<CartItemModel> getMyCartItems(String user_id) {
        return cashDataDao.getCartItem(user_id);
    }
}

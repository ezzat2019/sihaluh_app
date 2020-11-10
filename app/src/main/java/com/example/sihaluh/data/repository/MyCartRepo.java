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

    private CashDataDao cashDataDao;

    @Inject
    public MyCartRepo(CashDataDao cashDataDao) {
        this.cashDataDao = cashDataDao;
    }

    public void addProductToCar(CartItemModel cartItemModel) {
        Observable.fromCompletable(new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                cashDataDao.addProducttoCart(cartItemModel);
                observer.onComplete();
            }
        })
                .doOnError(e -> Log.d("MyCartRepo", "addProductToCar: " + e.getMessage()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe();
    }

    public LiveData<CartItemModel> getMyCartItems(String user_id){
        return cashDataDao.getCartItem(user_id);
    }
}

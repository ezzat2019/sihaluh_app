package com.example.sihaluh.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sihaluh.data.apis.CashDataDao;
import com.example.sihaluh.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductOfflineRep {
    List<ProductModel> mutableLiveData = new ArrayList<>();
    private final CashDataDao cashDataDao;

    @Inject
    public ProductOfflineRep(CashDataDao cashDataDao) {
        this.cashDataDao = cashDataDao;
    }

    public void setnewProduct(ProductModel productModel) {
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("HomeViewModel", "onerror " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d("HomeViewModel", "onComplete: ");
            }
        };

        Observable.fromCompletable(new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                cashDataDao.setProduct(productModel);
                observer.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())

                .subscribe(observer);

    }

    public LiveData<List<ProductModel>> getAllProducts() {

        return cashDataDao.getProductList();
    }
}

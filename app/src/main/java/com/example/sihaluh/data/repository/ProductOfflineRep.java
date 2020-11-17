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



                cashDataDao.setProduct(productModel);

    }

    public LiveData<List<ProductModel>> getAllProducts() {

        return cashDataDao.getProductList();
    }
}

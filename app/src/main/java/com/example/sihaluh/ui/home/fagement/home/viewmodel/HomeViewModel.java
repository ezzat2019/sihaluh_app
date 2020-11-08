package com.example.sihaluh.ui.home.fagement.home.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.data.repository.ProductOfflineRep;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<ProductModel>> mutableLiveData = new MutableLiveData<>();
    private ProductOfflineRep productOfflineRep;
    private MutableLiveData<List<ProductModel>> product_offline=new MutableLiveData<>();

    @ViewModelInject
    public HomeViewModel(ProductOfflineRep productOfflineRep) {
        this.productOfflineRep = productOfflineRep;
    }

    public MutableLiveData<String> getResult_num() {
        return result_num;
    }

    public void setResult_num(Integer num) {
        result_num.setValue(num.toString());
    }

    private MutableLiveData<String> result_num = new MutableLiveData<>();

    public MutableLiveData<List<ProductModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(List<ProductModel> productModels) {
        mutableLiveData.setValue(productModels);
    }

    public void setNewroduct(ProductModel productModel)
    {

        productOfflineRep.setnewProduct(productModel);



    }

    public LiveData<List<ProductModel>> getProductOffline() {



        return productOfflineRep.getAllProducts();
    }
}

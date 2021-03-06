package com.example.sihaluh.ui.home.fagement.home.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.data.repository.ProductOfflineRep;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<ProductModel>> mutableLiveData = new MutableLiveData<>();
    private final ProductOfflineRep productOfflineRep;
    private final MutableLiveData<List<ProductModel>> product_offline = new MutableLiveData<>();
    private final MutableLiveData<String> result_num = new MutableLiveData<>();

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

    public MutableLiveData<List<ProductModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(List<ProductModel> productModels) {
        mutableLiveData.setValue(productModels);
    }

    public void setNewroduct(ProductModel productModel) {

        productOfflineRep.setnewProduct(productModel);


    }

    public LiveData<List<ProductModel>> getProductOffline() {


        return productOfflineRep.getAllProducts();
    }
}

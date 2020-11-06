package com.example.sihaluh.ui.home.fagement.home.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.CategoriesModel;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<CategoriesModel>>listMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<List<CategoriesModel>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void setListMutableLiveData(List<CategoriesModel> categoriesModelList) {
       listMutableLiveData.setValue(categoriesModelList);
    }
}

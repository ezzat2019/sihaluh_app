package com.example.sihaluh.ui.order_complete.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.AdressUserModel;

public class OrderDetailViewModel extends ViewModel {
    private final MutableLiveData<AdressUserModel> liveLoctionName = new MutableLiveData<>();

    public MutableLiveData<AdressUserModel> getLiveLoctionName() {
        return liveLoctionName;
    }

    public void setLiveLoctionName(AdressUserModel adressUserModel) {
        liveLoctionName.setValue(adressUserModel);
    }
}

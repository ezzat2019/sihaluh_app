package com.example.sihaluh.ui.order_complete.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderDetailViewModel extends ViewModel {
    private final MutableLiveData<String> liveLoctionName = new MutableLiveData<>();

    public MutableLiveData<String> getLiveLoctionName() {
        return liveLoctionName;
    }

    public void setLiveLoctionName(String name) {
        liveLoctionName.setValue(name);
    }
}

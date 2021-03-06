package com.example.sihaluh.utils.shared_preferense;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;


public class PrefViewModel extends ViewModel {
    private final MySharedPreference mySharedPreference;


    @ViewModelInject
    public PrefViewModel(MySharedPreference mySharedPreference) {
        this.mySharedPreference = mySharedPreference;
    }

    public void putPhone(String phone) {
        mySharedPreference.putMainPhone(phone);
    }

    public String getPhone() {
        return mySharedPreference.getMainPhone();
    }


    public void putState(Boolean state) {
        mySharedPreference.setState_user(state);
    }

    public Boolean getState() {
        return mySharedPreference.getState_user();
    }


}

package com.example.sihaluh.utils.shared_preferense;


import android.content.SharedPreferences;

import com.example.sihaluh.utils.AllFinal;

import javax.inject.Inject;


public class MySharedPreference {

    private final SharedPreferences sharedPreferences;

    private final SharedPreferences.Editor editor;

    @Inject
    public MySharedPreference(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }

    public void putMainPhone(String phone) {
        editor.putString(AllFinal.PREFERENSE_PHONE, phone);
        editor.commit();
    }

    public String getMainPhone() {
        return sharedPreferences.getString(AllFinal.PREFERENSE_PHONE, "");
    }
}

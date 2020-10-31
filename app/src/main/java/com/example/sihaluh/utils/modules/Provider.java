package com.example.sihaluh.utils.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.MySharedPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class Provider {
    @Provides
    @Singleton
    public SharedPreferences providePref(Application application)
    {
        return application.getSharedPreferences(AllFinal.PREFERENSE_NAME, Context.MODE_PRIVATE);
    }
    @Provides
    @Singleton
    public SharedPreferences.Editor providePrefEditer(SharedPreferences sharedPreferences)
    {
        return sharedPreferences.edit();
    }

//    @Provides
//    @Singleton
//    public MySharedPreference provid(SharedPreferences mySharedPreference,SharedPreferences.Editor editor)
//    {
//        return new MySharedPreference(mySharedPreference,editor);
//    }
}

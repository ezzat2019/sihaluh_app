package com.example.sihaluh.utils.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.sihaluh.data.apis.CashDataDao;
import com.example.sihaluh.data.database.MainDataBase;
import com.example.sihaluh.utils.AllFinal;

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
    public SharedPreferences providePref(Application application) {
        return application.getSharedPreferences(AllFinal.PREFERENSE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public SharedPreferences.Editor providePrefEditer(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    @Provides

    public MainDataBase getDB(Application application) {
        return Room.databaseBuilder(application, MainDataBase.class, "product70")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public CashDataDao getDao(MainDataBase mainDataBase) {
        return mainDataBase.getProductDAO();
    }

}

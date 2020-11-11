package com.example.sihaluh.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.sihaluh.data.apis.CashDataDao;
import com.example.sihaluh.data.model.CategoryItemModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryItemRepo {
    private final CashDataDao cashDataDao;

    @Inject
    public CategoryItemRepo(CashDataDao cashDataDao) {
        this.cashDataDao = cashDataDao;
    }

    public void setCategory(CategoryItemModel category) {


        Observable.fromCompletable(new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                cashDataDao.setCategoryItem(category);
                observer.onComplete();
                Log.d("CategoryItemRepo", "complet ");
            }
        }).doOnError(e -> Log.d("CategoryItemRepo", "onerror " + e.getMessage()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();

    }

    public LiveData<CategoryItemModel> getCategortModel(String id) {
        return cashDataDao.getCategoryItem(id);
    }
}

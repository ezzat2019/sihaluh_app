package com.example.sihaluh.data.repository;

import androidx.lifecycle.LiveData;

import com.example.sihaluh.data.apis.CashDataDao;
import com.example.sihaluh.data.model.EndOrderModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HistoryRepo {
    private CashDataDao cashDataDao;

    @Inject
    public HistoryRepo(CashDataDao cashDataDao) {
        this.cashDataDao = cashDataDao;
    }

    public LiveData<List<EndOrderModel>>getHistoryItems()
    {

        return cashDataDao.getHistoryList();
    }
    public void addNewHisteroyItem(EndOrderModel endOrderModel)
    {


                cashDataDao.addHistoryModel(endOrderModel);


    }

    public void deleteHistoryItem(String id)
    {


                cashDataDao.deleteHistoryItem(id);

    }
}

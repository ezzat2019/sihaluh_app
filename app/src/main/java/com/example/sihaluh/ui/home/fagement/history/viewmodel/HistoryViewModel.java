package com.example.sihaluh.ui.home.fagement.history.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.EndOrderModel;
import com.example.sihaluh.data.repository.HistoryRepo;
import com.example.sihaluh.ui.end_oreder.EndOrderActivity;

import java.util.List;

public class HistoryViewModel extends ViewModel {
    private MutableLiveData<List<EndOrderModel>> mutableLiveData = new MutableLiveData<>();
    private HistoryRepo historyRepo;

    @ViewModelInject
    public HistoryViewModel(HistoryRepo historyRepo) {
        this.historyRepo = historyRepo;
    }

    public LiveData<List<EndOrderModel>> getHistoryLiveData() {

        return historyRepo.getHistoryItems();
    }

    public void addNewHistoryItem(EndOrderModel endOrderModel)
    {
        historyRepo.addNewHisteroyItem(endOrderModel);
    }
    public void deleteHistoryItem(String id)
    {
        historyRepo.deleteHistoryItem(id);
    }
}

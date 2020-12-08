package com.example.sihaluh.ui.end_oreder.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.MyResponse;
import com.example.sihaluh.data.model.SenderFragNotifictionModel;
import com.example.sihaluh.data.repository.FCMRepo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndOrderViewModel extends ViewModel {
    private final FCMRepo fcmRepo;
    private final MutableLiveData<Response<MyResponse>> responseMutableLiveData=new MutableLiveData<>();

    public EndOrderViewModel() {
       fcmRepo=new FCMRepo();
    }

    public MutableLiveData<Response<MyResponse>> getResponseMutableLiveData() {
        return responseMutableLiveData;
    }

    public void setNotifiction(SenderFragNotifictionModel senderFragNotifictionModel)
    {
        fcmRepo.sendMessageNotificationFrag(senderFragNotifictionModel)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        responseMutableLiveData.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {

                    }
                });
    }
}

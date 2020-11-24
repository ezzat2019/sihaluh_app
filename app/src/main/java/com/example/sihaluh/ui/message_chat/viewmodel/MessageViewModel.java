package com.example.sihaluh.ui.message_chat.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sihaluh.data.model.MyResponse;
import com.example.sihaluh.data.model.SenderModel;
import com.example.sihaluh.data.model.TokkenModel;
import com.example.sihaluh.data.model.TypingModel;
import com.example.sihaluh.data.repository.FCMRepo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {
    private final FCMRepo fcmRepo;
    private final MutableLiveData<Response<MyResponse>> responseLiveData=new MutableLiveData<>();
    private final MutableLiveData<TypingModel> typingModelMutableLiveData=new MutableLiveData<>();

    public MessageViewModel() {
        fcmRepo=new FCMRepo();
    }

    public MutableLiveData<TypingModel> getTypingModelMutableLiveData() {
        return typingModelMutableLiveData;
    }

    public void setTypingModelMutableLiveData(TypingModel typingModely) {
        typingModelMutableLiveData.setValue(typingModely);

    }

    public MutableLiveData<Response<MyResponse>> getResponseLiveData() {
        return responseLiveData;
    }

    public void sendNotification(SenderModel senderModel)
    {
        fcmRepo.sendMessageNotification(senderModel)
                .enqueue(new Callback<MyResponse>() {
                    @Override
                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                        responseLiveData.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<MyResponse> call, Throwable t) {
                        Log.d("MessageViewModel", "onFailure: "+t.getMessage());

                    }
                });
    }
}

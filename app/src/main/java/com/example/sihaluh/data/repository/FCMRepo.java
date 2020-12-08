package com.example.sihaluh.data.repository;

import com.example.sihaluh.data.apis.FCMAPI;
import com.example.sihaluh.data.model.MyResponse;
import com.example.sihaluh.data.model.SenderFragNotifictionModel;
import com.example.sihaluh.data.model.SenderModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FCMRepo {
    private final Retrofit retrofit;
    private final FCMAPI fcmapi;

    public FCMRepo() {
        retrofit=new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fcmapi=retrofit.create(FCMAPI.class);
    }
    public Call<MyResponse> sendMessageNotification(SenderModel senderModel)
    {
        return fcmapi.sendNotification(senderModel);
    }
    public Call<MyResponse> sendMessageNotificationFrag(SenderFragNotifictionModel senderFragNotifictionModel)
    {
        return fcmapi.sendNotificationFrag(senderFragNotifictionModel);
    }
}

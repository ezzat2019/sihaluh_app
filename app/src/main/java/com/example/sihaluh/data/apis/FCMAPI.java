package com.example.sihaluh.data.apis;

import com.example.sihaluh.data.model.MyResponse;
import com.example.sihaluh.data.model.SenderFragNotifictionModel;
import com.example.sihaluh.data.model.SenderModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMAPI {

    @Headers(
            {
                    "Content-Type:application/json"
                    ,"Authorization:key=AAAAyaa4drU:APA91bHSWsN46XaTmRK5WLP2uvjg-DNj50QIxA4C6EEHx9GkKHBaid-Y_IRCEWAsgdOIVPO8fe_f-wOns1aHo8s53760ZqfxhXYWxhiADkwvnW8FjTqckoZlpSF4NT6RPFT6FImTUfk6"

            }
    )

    @POST("fcm/send")
    Call<MyResponse>sendNotification(@Body SenderModel senderModel);

    @Headers(
            {
                    "Content-Type:application/json"
                    ,"Authorization:key=AAAAyaa4drU:APA91bHSWsN46XaTmRK5WLP2uvjg-DNj50QIxA4C6EEHx9GkKHBaid-Y_IRCEWAsgdOIVPO8fe_f-wOns1aHo8s53760ZqfxhXYWxhiADkwvnW8FjTqckoZlpSF4NT6RPFT6FImTUfk6"

            }
    )

    @POST("fcm/send")
    Call<MyResponse>sendNotificationFrag(@Body SenderFragNotifictionModel senderFragNotifictionModel);
}

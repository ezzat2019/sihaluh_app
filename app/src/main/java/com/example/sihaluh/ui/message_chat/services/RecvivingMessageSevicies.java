package com.example.sihaluh.ui.message_chat.services;

import android.app.LauncherActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sihaluh.R;
import com.example.sihaluh.utils.AllFinal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DecimalFormat;
import java.util.Random;

public class RecvivingMessageSevicies extends FirebaseMessagingService {
    private final DatabaseReference ref_state = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_STATE);
    private String reciver;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("vvvvvvvvv", "onMessageReceived: 1");
        if (remoteMessage.getData() != null) {
            reciver = remoteMessage.getData().get("reciver");
            if (reciver!=null)
            {

                sendNotificationChat(remoteMessage);

            }
            else
            {
                reciver=remoteMessage.getData().get("owner");
                if(reciver.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                {
                    sendNotificationFrag(remoteMessage);

                }


            }



        }

    }

    private void sendNotificationFrag(RemoteMessage remoteMessage) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String name = remoteMessage.getData().get("name");
        String total_price = remoteMessage.getData().get("total_price");
        Double price=Double.parseDouble(total_price);
        DecimalFormat format=new DecimalFormat("#.##");
        total_price=format.format(price);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ch2", "message", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(getApplicationContext(), "ch2")
                    .setSmallIcon(R.drawable.icon_main)
                    .setContentTitle("new message")
                    .setContentText(name+" price "+total_price+"L.E")
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();

            Random random = new Random(1000);
            manager.notify(random.nextInt(), notification);


        } else {

            Notification notification = new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.icon_main)
                    .setContentTitle("new message")
                    .setContentText(name+" price "+total_price+"L.E")
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();

            Random random = new Random(1000);
            manager.notify(random.nextInt(), notification);
        }

    }

    private void sendNotificationChat(RemoteMessage remoteMessage) {
        Log.d("vvvvvvvvv", "onMessageReceived: 2"+reciver);
        ref_state.child(remoteMessage.getData().get("sender")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("vvvvvvvvv", "onMessageReceived: 3");
                if (snapshot.hasChildren()) {
                    Log.d("vvvvvvvvv", "onMessageReceived: 4");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d("vvvvvvvvv", "onMessageReceived: 5"+dataSnapshot.getValue().toString());
                        Boolean state= (Boolean) dataSnapshot.getValue();
                        if (!state)
                        {
                            Log.d("vvvvvvvvv", "onMessageReceived: 5"+state);
                            if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(reciver)) {
                                showNotification(remoteMessage);
                                Log.d("vvvvvvvvv", "onMessageReceived: 6"+state);
                            }
                        }

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showNotification(RemoteMessage remoteMessage) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String message = remoteMessage.getData().get("message");
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext()
                , 3, new Intent(getApplicationContext(), LauncherActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK), PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ch2", "message", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(getApplicationContext(), "ch2")
                    .setSmallIcon(R.drawable.icon_main)
                    .setContentTitle("new message")
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();

            Random random = new Random(1000);
            manager.notify(random.nextInt(), notification);


        } else {

            Notification notification = new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.icon_main)
                    .setContentTitle("new message")
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .build();

            Random random = new Random(1000);
            manager.notify(random.nextInt(), notification);
        }
    }
}

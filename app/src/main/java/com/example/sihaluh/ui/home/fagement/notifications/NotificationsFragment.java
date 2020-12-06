package com.example.sihaluh.ui.home.fagement.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.NotificationModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.notifications.adapters.NotificationAdapter;
import com.example.sihaluh.utils.AllFinal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class NotificationsFragment extends Fragment {

    // fire
    private final DatabaseReference ref_notification = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_DATABASE_Notifcation);
    private final ArrayList listNotification = new ArrayList();
    // ui
    private ImageView img_notification_no;
    private RecyclerView rec_notification;
    private TextView textView15, textView16;
    // var
    private NotificationAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        init(root);
        return root;
    }

    private void init(View v) {
        img_notification_no = v.findViewById(R.id.img_notification_no);

        buildRec(v);
        showisEmptyFrag(true);
        observeNotification();
    }

    private void observeNotification() {
        Log.d("rrrrrrrr", "onDataChange: -1 "+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref_notification.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("rrrrrrrr", "onDataChange: 0");
                        if (snapshot.exists()) {
                            Log.d("rrrrrrrr", "onDataChange: 1");
                            showisEmptyFrag(true);
                            listNotification.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                NotificationModel notificationModel = dataSnapshot.getValue(NotificationModel.class);
                                Log.d("rrrrrrrr", "onDataChange: 3");
                                listNotification.add(notificationModel);
                            }
                            if (!listNotification.isEmpty()) {
                                Log.d("rrrrrrrr", "onDataChange: 2");

                                Collections.reverse(listNotification);
                                adapter.setNotificationModels(listNotification);
                                showisEmptyFrag(false);
                            } else {
                                Log.d("rrrrrrrr", "onDataChange: 4");
                                showisEmptyFrag(true);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("rrrrrrrr", "onDataChange: 5");

                    }
                });
    }

    private void buildRec(View v) {
        rec_notification = v.findViewById(R.id.rec_notification);
        textView16 = v.findViewById(R.id.textView16);
        textView15 = v.findViewById(R.id.textView15);

        rec_notification.setHasFixedSize(true);
        rec_notification.setLayoutManager(new LinearLayoutManager(v.getContext()));

        adapter = new NotificationAdapter();
        rec_notification.setAdapter(adapter);

    }

    private void showisEmptyFrag(Boolean show) {
        if (show) {
            img_notification_no.setVisibility(View.VISIBLE);
            rec_notification.setVisibility(View.GONE);
            textView15.setVisibility(View.GONE);
            textView16.setVisibility(View.GONE);
        } else {
            img_notification_no.setVisibility(View.GONE);
            rec_notification.setVisibility(View.VISIBLE);
            textView15.setVisibility(View.VISIBLE);
            textView16.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        HomeActivity.txt_name_bar.setText(getString(R.string.title_notifications));
    }
}
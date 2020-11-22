package com.example.sihaluh.ui.message_chat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.MessageModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.ui.message_chat.adapter.MessageAdapter;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;
import de.hdodenhof.circleimageview.CircleImageView;

@AndroidEntryPoint
public class MessageActivity extends AppCompatActivity {

    // firebase
    private final DatabaseReference ref_messages = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_MESSAGES);
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final DatabaseReference ref_state = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_STATE);
    // ui
    private RecyclerView rec_mesagges;
    private ImageView img_message_back;
    private CircleImageView img_message_profile_image;
    private TextView txt_message_name, txt_message_state;
    private EditText ed_message_content;
    private FloatingActionButton floating_message;
    // var
    private MessageAdapter adapter;
    private ArrayList<MessageModel> messageModelArrayList = new ArrayList<>();
    private ProductModel productModel;
    private PrefViewModel prefViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (!getIntent().hasExtra(AllFinal.REF_SELLER_ID)) {
            Toast.makeText(getApplicationContext(), "error try again!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            productModel = getIntent().getParcelableExtra(AllFinal.REF_SELLER_ID);
            init();

            actions();
        }


    }

    private void actions() {
        floating_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        img_message_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void sendMessage() {
        MessageModel messageModel = new MessageModel(ed_message_content.getText().toString().trim(),
                mAuth.getCurrentUser().getUid(), productModel.getOwner());
        ref_messages.push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ed_message_content.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        img_message_back = findViewById(R.id.img_message_back);
        img_message_profile_image = findViewById(R.id.img_message_profile_image);
        txt_message_name = findViewById(R.id.txt_message_name);
        txt_message_state = findViewById(R.id.txt_message_state);
        ed_message_content = findViewById(R.id.ed_message_content);
        floating_message = findViewById(R.id.floating_message);

        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);

        buildRec();

        setupToolbar();

        observeMessages();

        observeStates();


    }

    private void observeStates() {
        ref_state.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Boolean b = (Boolean) snapshot.child("state").getValue();
                    if (b) {
                        txt_message_state.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                        txt_message_state.setText("Online");
                    } else {
                        txt_message_state.setTextColor(getResources().getColor(android.R.color.white));
                        txt_message_state.setText("Offline");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        setStateUser(true);

    }

    private void setStateUser(Boolean stateUser) {
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("state", stateUser);

        ref_state.child(productModel.getOwner())
                .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                prefViewModel.putState(stateUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                prefViewModel.putState(stateUser);
            }
        });
    }


    private void setupToolbar() {
        txt_message_name.setText(productModel.getName());
        Glide.with(getApplicationContext())
                .load(productModel.getImg())
                .fitCenter()
                .into(img_message_profile_image);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setStateUser(false);
    }

    private void observeMessages() {
        ref_messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        messageModelArrayList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                            messageModelArrayList.add(messageModel);
                        }
                        adapter.setMessageModels(messageModelArrayList);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rec_mesagges.smoothScrollToPosition(messageModelArrayList.size());
                            }
                        }, 100);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void buildRec() {
        rec_mesagges = findViewById(R.id.rec_mesagges);
        rec_mesagges.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        rec_mesagges.setLayoutManager(linearLayoutManager);

        adapter = new MessageAdapter();
        rec_mesagges.setAdapter(adapter);

    }
}
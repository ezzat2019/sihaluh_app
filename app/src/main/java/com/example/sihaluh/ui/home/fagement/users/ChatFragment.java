package com.example.sihaluh.ui.home.fagement.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.data.model.RegisterModel;
import com.example.sihaluh.data.model.UserChatAddModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.users.adapter.UserAdapter;
import com.example.sihaluh.ui.message_chat.MessageActivity;
import com.example.sihaluh.utils.AllFinal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    // firebase
    private final DatabaseReference ref_users= FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_USER_CHAT)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private final ArrayList<RegisterModel> registerModelArrayList=new ArrayList<>();
    // ui
    private ImageView img_no_users,img_user_frag;
    private ConstraintLayout constrain_no_notification_chat;
    private CardView card_user;
    private View view_user;
    private RecyclerView rec_users;
    private TextView txt_xhat_user_name;
    // var
    private UserAdapter userAdapter;
    private UserChatAddModel userChatAddModel;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        showNoData(true);
        observeUsers();


        actions();

    }

    private void actions() {
        userAdapter.setOnClickChat(new UserAdapter.OnClickChat() {
            @Override
            public void setOnClick(int pos) {
                ProductModel productModel=new ProductModel();
                productModel.setImg(userChatAddModel.getUsers_added().get(pos).getImg_url());
                productModel.setName(userChatAddModel.getUsers_added().get(pos).getName());
                productModel.setOwner(userChatAddModel.getUsers_added().get(pos).getId());
                startActivity(new Intent(getContext(), MessageActivity.class).putExtra(AllFinal.REF_SELLER_ID, productModel));

            }
        });
    }

    private void observeUsers() {
        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {

                    userChatAddModel=snapshot.getValue(UserChatAddModel.class);

                    showNoData(false);
                    registerModelArrayList.clear();
                    registerModelArrayList.addAll(userChatAddModel.getUsers_added());
                    userAdapter.setRegisterModelList(registerModelArrayList);
                    txt_xhat_user_name.setText(userChatAddModel.getName());
                    if (userChatAddModel.getCurrent_user_img().equals(""))
                    {
                        Glide.with(getContext())
                                .load(getResources().getDrawable(R.drawable.no_user))
                                .into(img_user_frag);
                    }
                    else
                    {
                        Glide.with(getContext())
                                .load(userChatAddModel.getCurrent_user_img())
                                .into(img_user_frag);
                    }


                }
                else
                {
                    showNoData(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showNoData(true);

            }
        });
    }

    private void showNoData(Boolean b) {
        if (b) {
            img_no_users.setVisibility(View.VISIBLE);
            constrain_no_notification_chat.setBackgroundColor(getResources().getColor(R.color.gray_notification));
            rec_users.setVisibility(View.GONE);
            card_user.setVisibility(View.GONE);
            view_user.setVisibility(View.GONE);
        } else {
            img_no_users.setVisibility(View.GONE);
            constrain_no_notification_chat.setBackgroundColor(getResources().getColor(R.color.white));
            rec_users.setVisibility(View.VISIBLE);
            card_user.setVisibility(View.VISIBLE);
            view_user.setVisibility(View.VISIBLE);

        }
    }

    private void init(View view) {
        txt_xhat_user_name=view.findViewById(R.id.txt_xhat_user_name);
        img_no_users = view.findViewById(R.id.img_no_notification_chat);
        img_user_frag=view.findViewById(R.id.img_user_frag);
        constrain_no_notification_chat = view.findViewById(R.id.constrain_no_notification_chat);
        card_user=view.findViewById(R.id.card_user);
        rec_users=view.findViewById(R.id.rec_users);
        view_user=view.findViewById(R.id.view_user);




        buildRec();
    }



    private void buildRec() {
        userAdapter=new UserAdapter();
        rec_users.setHasFixedSize(true);
        rec_users.setLayoutManager(new LinearLayoutManager(getContext()));
        rec_users.setAdapter(userAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        HomeActivity.txt_name_bar.setText(getString(R.string.title_chat));
    }
}
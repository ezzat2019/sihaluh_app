package com.example.sihaluh.ui.home.fagement.users.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.RegisterModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH> {



    public OnClickChat onClickChat;
    // var
    private List<RegisterModel> registerModelList = new ArrayList<>();

    public void setOnClickChat(OnClickChat onClickChat) {
        this.onClickChat = onClickChat;
    }

    public void setRegisterModelList(List<RegisterModel> registerModelList) {
        this.registerModelList = registerModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_chat_item, parent, false);
        return new UserVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        holder.bindData(registerModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return registerModelList.size();
    }

    // interface
    public interface OnClickChat{
        void setOnClick(int pos);
    }

    class UserVH extends RecyclerView.ViewHolder {
        private final View v;
        private CircleImageView img_user_frag;
        private TextView txt_name_user_frag, txt_code_user_frag;


        public UserVH(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;
            init();

            actions();
        }

        private void actions() {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickChat.setOnClick(getAdapterPosition());
                }
            });
        }

        private void init() {
            img_user_frag = v.findViewById(R.id.img_user_frag);
            txt_name_user_frag = v.findViewById(R.id.txt_name_user_frag);
            txt_code_user_frag = v.findViewById(R.id.txt_code_user_frag);
        }

        public void bindData(RegisterModel registerModel) {
            txt_name_user_frag.setText(registerModel.getName());
            txt_code_user_frag.setText(registerModel.getPhone());
            if (registerModel.getImg_url().equals("") || registerModel.getImg_url() == null) {
                Glide.with(v.getContext())
                        .load(v.getResources().getDrawable(R.drawable.no_user)).into(img_user_frag);
            } else {
                Glide.with(v.getContext())
                        .load(registerModel.getImg_url()).into(img_user_frag);

            }
        }
    }

}

package com.example.sihaluh.ui.message_chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageVH> {
    private static final int LEFT = 11;
    private static final int RIGHT = 22;

    // var
    private List<MessageModel> messageModels = new ArrayList<>();
    private int viewType;

    @NonNull
    @Override
    public MessageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.viewType = viewType;

        View view;
        if (viewType == RIGHT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.messgae_right, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_left, parent, false);
        }
        return new MessageVH(view);
    }

    public void setMessageModels(List<MessageModel> messageModels) {

        this.messageModels = messageModels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MessageVH holder, int position) {

        holder.bindMessage(messageModels.get(position));
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!messageModels.isEmpty()) {
            if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messageModels.get(position).getSender_id())) {
                return RIGHT;
            } else return LEFT;
        } else {
            return super.getItemViewType(position);
        }


    }

    class MessageVH extends RecyclerView.ViewHolder {
        private final View v;
        private TextView txt_message;

        public MessageVH(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;

            init();
        }

        private void init() {
            if (viewType == LEFT) {
                txt_message = v.findViewById(R.id.txt_messgae_item_left);

            } else {
                txt_message = v.findViewById(R.id.txt_messgae_item_right);
            }

        }

        public void bindMessage(MessageModel messageModel) {
            txt_message.setText(messageModel.getMessage_content());
        }
    }
}

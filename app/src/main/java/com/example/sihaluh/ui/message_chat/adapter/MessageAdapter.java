package com.example.sihaluh.ui.message_chat.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.MessageModel;
import com.example.sihaluh.utils.AllFinal;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageVH> {
    private static final int LEFT = 11;
    private static final int RIGHT = 22;

    // var
    private List<MessageModel> messageModels = new ArrayList<>();
    private int viewType;
    public OnMessageClick onMessageClick;

    public void setOnMessageClick(OnMessageClick onMessageClick) {
        this.onMessageClick = onMessageClick;
    }

    // interface
    public interface OnMessageClick {
        void click(int pos, TextView txt_date, TextView txt_time_ago,ImageView imageView);
    }

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
        private TextView txt_message, txt_date, txt_time_ago;
        private ImageView img_message;
        private CardView card_message;

        public MessageVH(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;

            init();

            actions();
        }

        private void actions() {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMessageClick.click(getAdapterPosition(), txt_date, txt_time_ago,img_message);
                }
            });
        }

        private void init() {
            if (viewType == LEFT) {
                txt_message = v.findViewById(R.id.txt_messgae_item_left);
                txt_date = v.findViewById(R.id.txt_date_left);
                txt_time_ago = v.findViewById(R.id.txt_time_age_left);
                img_message = v.findViewById(R.id.img_left_messgae);
                card_message = v.findViewById(R.id.card_message_left);
                img_message.setVisibility(View.GONE);
                card_message.setVisibility(View.GONE);
                txt_date.setVisibility(View.GONE);
                txt_date.animate().scaleX(0).scaleY(0);

                txt_time_ago.setVisibility(View.GONE);
                txt_time_ago.animate().scaleX(0).scaleY(0);

            } else {
                txt_message = v.findViewById(R.id.txt_messgae_item_right);
                txt_date = v.findViewById(R.id.txt_date_right);
                txt_time_ago = v.findViewById(R.id.txt_time_age_right);
                img_message = v.findViewById(R.id.img_right_messgae);
                card_message = v.findViewById(R.id.card_message_right);
                img_message.setVisibility(View.GONE);
                card_message.setVisibility(View.GONE);
                txt_date.setVisibility(View.GONE);
                txt_date.animate().scaleX(0).scaleY(0);
                txt_time_ago.setVisibility(View.GONE);
                txt_time_ago.animate().scaleX(0).scaleY(0);
            }

        }

        public void bindMessage(MessageModel messageModel) {
            String type = messageModel.getMessage_type();
            if (type.equals(AllFinal.MEESAGE_TYPE_TEXT)) {
                img_message.setVisibility(View.GONE);
                card_message.setVisibility(View.VISIBLE);


                txt_message.setText(messageModel.getMessage_content());
            } else if (type.equals(AllFinal.MEESAGE_TYPE_PDF)) {
                img_message.setVisibility(View.GONE);
                card_message.setVisibility(View.VISIBLE);
               txt_message.setTextSize(14f);
                txt_message.setTextColor(v.getResources().getColor(android.R.color.holo_green_light));
                txt_message.setText(messageModel.getMessage_content() );


            } else if (type.equals(AllFinal.MEESAGE_TYPE_IMG)) {
                card_message.setVisibility(View.GONE);
                Glide.with(v.getContext())
                        .load(messageModel.getMessage_img())
                        .fitCenter().into(img_message);
                img_message.setVisibility(View.VISIBLE);


            }
        }
    }
}

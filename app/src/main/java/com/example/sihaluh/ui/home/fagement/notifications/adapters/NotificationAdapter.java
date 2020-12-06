package com.example.sihaluh.ui.home.fagement.notifications.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.NotificationModel;
import com.example.sihaluh.utils.helper.TimeHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationVH> {
    private List<NotificationModel> notificationModels = new ArrayList<>();

    public void setNotificationModels(List<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new NotificationVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationVH holder, int position) {
        holder.bindData(notificationModels.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    class NotificationVH extends RecyclerView.ViewHolder {
        private final View v;
        private CircleImageView img_notification;
        private TextView txt_notification_name, txt_notification_time;

        public NotificationVH(@NonNull View itemView) {
            super(itemView);

            this.v = itemView;
            init();

        }

        private void bindData(NotificationModel notificationModel) {
            if (notificationModel != null) {
                txt_notification_name.setText(notificationModel.getTo());
                String time = TimeHelper.getInstance().getTimeAgo(notificationModel.getDate().getTime());
                txt_notification_time.setText(time);
                Glide.with(v.getContext())
                        .load(notificationModel.getImg())
                        .into(img_notification);
            }
        }

        private void init() {
            img_notification = v.findViewById(R.id.img_notification);
            txt_notification_name = v.findViewById(R.id.txt_notification_name);
            txt_notification_time = v.findViewById(R.id.txt_notification_time);

        }
    }
}

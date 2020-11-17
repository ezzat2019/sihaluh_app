package com.example.sihaluh.ui.home.fagement.history.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.EndOrderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryVH> {
    private List<EndOrderModel> endOrderModelList = new ArrayList<>();

    public OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new HistoryVH(v);
    }

    public interface OnItemClick {

        void onLongClick(int pos);
    }

    public void addHistoryItems(List<EndOrderModel> endOrderModelList) {
        this.endOrderModelList = endOrderModelList;

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        holder.bindData(endOrderModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return endOrderModelList.size();
    }

    class HistoryVH extends RecyclerView.ViewHolder {
        private TextView txt_his_name, txt_his_phone, txt_his_payment, txt_his_loction, txt_his_date, txt_his_price, txt_his_id;
        private View v;

        public HistoryVH(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;

            init();

            actions();

        }

        private void actions() {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "press long click to detete this item", Toast.LENGTH_SHORT).show();
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClick.onLongClick(getAdapterPosition());
                    return false;
                }
            });
        }

        private void bindData(EndOrderModel endOrderModel) {
            txt_his_name.setText("name : " + endOrderModel.getAdressUserModel().getName());
            txt_his_id.setText(endOrderModel.getOwner_id());
            txt_his_loction.setText("Loction : " + endOrderModel.getAdressUserModel().getLoction_name());
            txt_his_phone.setText("Phone : " + endOrderModel.getId());
            txt_his_price.setText("Price : " + endOrderModel.getTotal_price() + " SAR ");
            txt_his_payment.setText("Payment Type : " + endOrderModel.getPayment_type());
            Date date = endOrderModel.getDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            txt_his_date.setText("Date : " + formatter.format(date));
        }

        private void init() {
            txt_his_name = v.findViewById(R.id.txt_his_name);
            txt_his_phone = v.findViewById(R.id.txt_his_phone);
            txt_his_payment = v.findViewById(R.id.txt_his_payment);
            txt_his_loction = v.findViewById(R.id.txt_his_loction);
            txt_his_date = v.findViewById(R.id.txt_his_date);
            txt_his_price = v.findViewById(R.id.txt_his_price);
            txt_his_id = v.findViewById(R.id.txt_his_id);


        }
    }


}

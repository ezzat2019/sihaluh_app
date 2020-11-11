package com.example.sihaluh.ui.home.fagement.cart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {
    public OnCartListerner onCartListerner;
    private List<ProductModel> productModelList = new ArrayList<>();

    public void setOnCartListerner(OnCartListerner onCartListerner) {
        this.onCartListerner = onCartListerner;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {

        holder.fillData(productModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public interface OnCartListerner {
        void onAdd(int pos, TextView txt_num);

        void onminus(int pos, TextView txt_num);

        void onremoveItem(int pos, TextView txt_num);
    }

    class CartVH extends RecyclerView.ViewHolder {
        private final View v;
        private ImageButton btn_add, btn_minus;
        private ImageView img_item;
        private TextView txt_num_item, txt_salery, txt_item_name;
        private Button btn_remove;

        public CartVH(@NonNull View itemView) {
            super(itemView);

            this.v = itemView;

            init();

            actions();
        }

        private void actions() {
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCartListerner.onremoveItem(getAdapterPosition(), txt_num_item);
                }
            });

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCartListerner.onAdd(getAdapterPosition(), txt_num_item);
                }
            });
            btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCartListerner.onminus(getAdapterPosition(), txt_num_item);
                }
            });
        }

        public void fillData(ProductModel productModel) {
            Glide.with(v.getContext())
                    .load(productModel.getImg())
                    .into(img_item);

            txt_item_name.setText(productModel.getName());
            txt_salery.setText(productModel.getPrice() + " SAR ");


        }

        private void init() {
            btn_add = v.findViewById(R.id.btn_add);
            txt_num_item = v.findViewById(R.id.txt_num_item_cart);
            btn_minus = v.findViewById(R.id.btn_minus_item_cart);
            btn_remove = v.findViewById(R.id.btn_remove);
            img_item = v.findViewById(R.id.img_cart_item);
            txt_item_name = v.findViewById(R.id.txt_cart_name);
            txt_salery = v.findViewById(R.id.txt_cart_price);
        }
    }
}

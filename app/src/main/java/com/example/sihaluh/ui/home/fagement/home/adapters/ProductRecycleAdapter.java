package com.example.sihaluh.ui.home.fagement.home.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductRecycleAdapter extends RecyclerView.Adapter<ProductRecycleAdapter.ProductVH> {
    public onProductClickListener onProductClickListener;
    private List<ProductModel> productModelList = new ArrayList<>();
    private final Context context;

    public void addProducts(List<ProductModel> productModelList) {

            this.productModelList = productModelList;
            notifyDataSetChanged();



    }

    public ProductRecycleAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemProductclick(onProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new ProductVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH holder, int position) {

        holder.fillData(productModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public interface onProductClickListener {
        void onClick(int posOfProduct);
    }

    class ProductVH extends RecyclerView.ViewHolder {
        private final View v;
        private TextView txt_name, txt_price, txt_sale;
        private ImageView img_product, img_cart;

        public ProductVH(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;
            init();

            actions();
        }

        private void actions() {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProductClickListener.onClick(getAdapterPosition());
                }
            });
        }

        public void fillData(ProductModel productModel) {


            txt_name.setText(productModel.getName());
            txt_price.setText(productModel.getPrice() + " SAR");
            Double num = Double.parseDouble(productModel.getSale());
            if (num == 1) {
                txt_sale.setText("%");
            } else {
                txt_sale.setText(num + " %");
            }
            try {
                Glide.with(context)
                        .load(productModel.getImg()).into(img_product);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.e("eeeeee", "fillData: "+e.getMessage() );
            }


        }

        private void init() {
            txt_name = v.findViewById(R.id.txt_product_item);
            txt_price = v.findViewById(R.id.txt_price_item);
            txt_sale = v.findViewById(R.id.txt_sale_product);


            img_product = v.findViewById(R.id.img_product_main);
        }
    }
}

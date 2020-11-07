package com.example.sihaluh.ui.category.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.CategoriesModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAllRecAdapter extends RecyclerView.Adapter<CategoryAllRecAdapter.RecAllCatVH> {

    private List<CategoriesModel> categoriesModelList = new ArrayList<>();
    public OnItemCatAllListener onItemCatAllListener;

    public interface OnItemCatAllListener
    {
        void onClick(int pos);
    }


    @NonNull
    @Override
    public RecAllCatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_activity_item, parent, false);
        return new RecAllCatVH(v);
    }


    public void setOnItemCatClick(OnItemCatAllListener onItemCatClick)
    {
        this.onItemCatAllListener=onItemCatClick;
    }
    public void addAllCategory(List<CategoriesModel> categoriesModelList) {
        this.categoriesModelList = categoriesModelList;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull RecAllCatVH holder, int position) {
        holder.fillData(categoriesModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoriesModelList.size();
    }

    class RecAllCatVH extends RecyclerView.ViewHolder {


        private View v;
        private TextView txt_name;
        private ImageView img_cat;


        public RecAllCatVH(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;

            init();

            actions();
        }

        private void actions() {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCatAllListener.onClick(getAdapterPosition());
                }
            });
        }

        public void fillData(CategoriesModel categoriesModel) {
            txt_name.setText(categoriesModel.getName());
            Glide.with(v.getContext())
                    .load(categoriesModel.getImg())
                    .into(img_cat);
        }

        private void init() {
            txt_name = v.findViewById(R.id.txt_name_activity);
            img_cat = v.findViewById(R.id.img_cat_actcicty);
        }
    }
}

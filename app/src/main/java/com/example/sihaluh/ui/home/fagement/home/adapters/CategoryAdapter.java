package com.example.sihaluh.ui.home.fagement.home.adapters;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatVH> {
    public OnItemCatListener onItemCatListener;
    private List<CategoriesModel> categoriesModelList = new ArrayList<>();

    public void setOnItemCatListener(OnItemCatListener onItemCatListener) {
        this.onItemCatListener = onItemCatListener;

    }

    @NonNull
    @Override
    public CatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_item, parent, false);
        return new CatVH(view);
    }

    public void addCategory(List<CategoriesModel> categoriesModelList) {
        this.categoriesModelList = categoriesModelList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CatVH holder, int position) {
        holder.fiiView(categoriesModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public interface OnItemCatListener {
        void onClick(int pos);
    }

    class CatVH extends RecyclerView.ViewHolder {
        private ImageView img_cat;
        private TextView txt_name;
        private final View vv;

        public CatVH(@NonNull View itemView) {
            super(itemView);
            this.vv = itemView;
            init(itemView);

            actions();
        }

        private void actions() {
            vv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCatListener.onClick(getAdapterPosition());
                }
            });
        }

        public void fiiView(CategoriesModel categoriesModel) {
            txt_name.setText(categoriesModel.getName());
            Glide.with(vv.getContext()).load(categoriesModel.getImg()).into(img_cat);
        }

        private void init(View v) {
            img_cat = v.findViewById(R.id.img_user_frag);
            txt_name = v.findViewById(R.id.txt_cat_item);

        }
    }
}

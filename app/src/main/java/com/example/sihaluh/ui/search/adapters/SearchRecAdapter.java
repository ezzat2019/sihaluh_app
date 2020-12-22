package com.example.sihaluh.ui.search.adapters;

import android.animation.Animator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.example.sihaluh.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class SearchRecAdapter extends RecyclerView.Adapter<SearchRecAdapter.SearchVH> implements Filterable {
    public OnItemSearchClick onItemSearchClick;
    List<ProductModel> list = new ArrayList<>();
    // for filter
    List<ProductModel> listMian = new ArrayList<>();

    public void setOnItemSearchClick(OnItemSearchClick onItemSearchClick) {
        this.onItemSearchClick = onItemSearchClick;
    }

    @NonNull
    @Override
    public SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new SearchVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchVH holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String q = constraint.toString().toLowerCase().trim();
                List<ProductModel> filter = new ArrayList<>();
                if (constraint.length() == 0) {
                    filter = listMian;
                } else {

                    for (ProductModel productModel : listMian) {

                        Log.d("tttttt", productModel.getName().toLowerCase() + " to "
                                + q + " is " + productModel.getName().toLowerCase().contains(q));
                        if (productModel.getName().toLowerCase().contains(q)) {
                            filter.add(productModel);
                            Log.d("hhhhhhhhh", "performFiltering: " + productModel.getName());

                        } else {
                            Log.d("hhhhhhhhh", "noooooooo " + productModel.getName());
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (constraint.length() > 0) {
                    list = (List<ProductModel>) results.values;
                } else {
                    list = listMian;
                }


                notifyDataSetChanged();


            }

        };
    }

    public List<ProductModel> getList() {
        return list;
    }

    public void setList(List<ProductModel> list) {
        this.list = list;
        this.listMian = list;
        notifyDataSetChanged();
    }

    // listerns
    public interface OnItemSearchClick {
        void onclick(int pos);
    }

    class SearchVH extends RecyclerView.ViewHolder {
        private final View v;
        private TextView txt_search_item;
        private ImageView img_search_item;

        public SearchVH(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;

            init();

            actions();

        }

        private void actions() {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSearchClick.onclick(getAdapterPosition());
                }
            });
        }

        void bindData(ProductModel model) {
            txt_search_item.setText(model.getName());
            img_search_item.animate().alpha(1)
                    .setDuration(1000)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            Glide.with(v.getContext())
                                    .load(model.getImg())
                                    .into(img_search_item);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

        }

        private void init() {
            txt_search_item = v.findViewById(R.id.txt_search_item);

            img_search_item = v.findViewById(R.id.img_search_item);
        }
    }


}


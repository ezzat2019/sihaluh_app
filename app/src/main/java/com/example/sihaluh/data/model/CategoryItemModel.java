package com.example.sihaluh.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "categoryitems")
public class CategoryItemModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "category_id")
    String category_id;
    @ColumnInfo(name = "productModelList")
     List<ProductModel> productModelList;

    @NonNull
    public String getCategory_id() {
        return category_id;
    }

    @Override
    public String toString() {
        return "CategoryItemModel{" +
                "category_id='" + category_id + '\'' +
                ", productModelList=" + productModelList +
                '}';
    }

    public void setCategory_id(@NonNull String category_id) {
        this.category_id = category_id;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public CategoryItemModel(@NonNull String category_id, List<ProductModel> productModelList) {
        this.category_id = category_id;
        this.productModelList = productModelList;
    }

    public CategoryItemModel() {
    }
}

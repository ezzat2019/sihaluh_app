package com.example.sihaluh.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "cartitems")
public class CartItemModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    public String user_id;
    @ColumnInfo(name = "product_cart")
    public List<ProductModel> productModelList;

    @NonNull
    public String getCategory_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "CategoryItemModel{" +
                "category_id='" + user_id + '\'' +
                ", productModelList=" + productModelList +
                '}';
    }

    public void setCategory_id(@NonNull String category_id) {
        this.user_id = category_id;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public CartItemModel(@NonNull String user_id, List<ProductModel> productModelList) {
        this.user_id = user_id;
        this.productModelList = productModelList;
    }

    public CartItemModel() {
    }
}

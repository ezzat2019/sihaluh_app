package com.example.sihaluh.data.apis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.CategoryItemModel;
import com.example.sihaluh.data.model.ProductModel;

import java.util.List;


@Dao
public interface CashDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long setProduct(ProductModel productModelList);

    @Query("select * from products")
    LiveData<List<ProductModel>> getProductList();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setCategoryItem(CategoryItemModel categoryItem);

    @Query("select * from categoryitems where category_id=:id")
    LiveData<CategoryItemModel> getCategoryItem(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProducttoCart(CartItemModel cartItemModel);

    @Query("select * from cartitems where user_id=:id")
    LiveData<CartItemModel> getCartItem(String id);


}

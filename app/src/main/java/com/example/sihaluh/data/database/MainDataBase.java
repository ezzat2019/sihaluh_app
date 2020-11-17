package com.example.sihaluh.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sihaluh.data.apis.CashDataDao;
import com.example.sihaluh.data.model.CartItemModel;
import com.example.sihaluh.data.model.CategoryItemModel;
import com.example.sihaluh.data.model.EndOrderModel;
import com.example.sihaluh.data.model.ProductModel;
import com.example.sihaluh.utils.room_helper.Converter;


@Database(entities = {ProductModel.class, CategoryItemModel.class, CartItemModel.class, EndOrderModel.class}, version = 16, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class MainDataBase extends RoomDatabase {

    public abstract CashDataDao getProductDAO();
}

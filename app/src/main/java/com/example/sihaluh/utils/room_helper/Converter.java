package com.example.sihaluh.utils.room_helper;

import androidx.room.TypeConverter;

import com.example.sihaluh.data.model.AdressUserModel;
import com.example.sihaluh.data.model.ProductModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

public class Converter {
    @TypeConverter
    public String fromCategoryListToString(List<ProductModel> productModelList) {
        return new Gson().toJson(productModelList);
    }

    @TypeConverter
    public List<ProductModel> fromStringToCategoryList(String str) {
        return new Gson().fromJson(str, new TypeToken<List<ProductModel>>() {
        }.getType());
    }


    @TypeConverter
    public String fromDatetoString(Date date) {
        return new Gson().toJson(date);
    }

    @TypeConverter
    public Date fromStringtoDate(String str) {
        return new Gson().fromJson(str,Date.class);
    }
    @TypeConverter
    public String fromAdressUserModeltoString(AdressUserModel adressUserModel)
    {
        return new Gson().toJson(adressUserModel);
    }

    @TypeConverter
    public AdressUserModel fromAdressUserModeltoString(String s){
        return new Gson().fromJson(s,AdressUserModel.class);
    }





}

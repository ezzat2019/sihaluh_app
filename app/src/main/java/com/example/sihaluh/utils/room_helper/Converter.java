package com.example.sihaluh.utils.room_helper;

import androidx.room.TypeConverter;

import com.example.sihaluh.data.model.ProductModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String fromCategoryListToString(List<ProductModel> productModelList)
    {
        return new Gson().toJson(productModelList);
    }

    @TypeConverter
    public List<ProductModel> fromStringToCategoryList(String str)
    {
        return new Gson().fromJson(str,new TypeToken<List<ProductModel>>(){}.getType());
    }

}

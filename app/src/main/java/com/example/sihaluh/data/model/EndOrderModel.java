package com.example.sihaluh.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "history_item")
public class EndOrderModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "owner_id")
    String owner_id;
    @ColumnInfo(name = "id")
    String id;
    @ColumnInfo(name = "adressUserModel")
     AdressUserModel adressUserModel;
    @ColumnInfo(name = "payment_type")
     String payment_type;
    @ColumnInfo(name = "total_price")
     String total_price;
    @ColumnInfo(name = "date")
     Date date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdressUserModel getAdressUserModel() {
        return adressUserModel;
    }

    public void setAdressUserModel(AdressUserModel adressUserModel) {
        this.adressUserModel = adressUserModel;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public EndOrderModel(String id, AdressUserModel adressUserModel, String payment_type, String total_price, Date date, String owner_id) {
        this.id = id;
        this.adressUserModel = adressUserModel;
        this.payment_type = payment_type;
        this.total_price = total_price;
        this.date = date;
        this.owner_id = owner_id;
    }

    public EndOrderModel() {
    }
}

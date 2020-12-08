package com.example.sihaluh.data.model;

public class DataNotifictionFragModel {
  private   String uid,  owner,  name,  total_price;

    public DataNotifictionFragModel() {
    }

    public DataNotifictionFragModel(String uid, String owner, String name, String total_price) {
        this.uid = uid;
        this.owner = owner;
        this.name = name;
        this.total_price = total_price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}

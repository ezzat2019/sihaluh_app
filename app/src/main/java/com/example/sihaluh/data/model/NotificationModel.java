package com.example.sihaluh.data.model;

import java.util.Date;

public class NotificationModel {
    private String name_product,img,from,to,total_price;
    private Date date;

    public NotificationModel(String name_product, String img, String from, String to, String total_price, Date date) {
        this.name_product = name_product;
        this.img = img;
        this.from = from;
        this.to = to;
        this.total_price = total_price;
        this.date = date;
    }

    public NotificationModel() {
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
}

package com.example.sihaluh.data.model;

public class SenderFragNotifictionModel {
    private String to;
    private DataNotifictionFragModel data;

    public SenderFragNotifictionModel(String to, DataNotifictionFragModel data) {
        this.to = to;
        this.data = data;
    }

    public SenderFragNotifictionModel() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public DataNotifictionFragModel getData() {
        return data;
    }

    public void setData(DataNotifictionFragModel data) {
        this.data = data;
    }
}

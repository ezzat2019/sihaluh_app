package com.example.sihaluh.data.model;

public class SenderModel {
    private String to;
    private DataModel data;

    public SenderModel(String to, DataModel data) {
        this.to = to;
        this.data = data;
    }


    public SenderModel() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }
}

package com.example.sihaluh.data.model;

public class DataModel {
    private String sender,message,reciver;

    public DataModel(String sender, String message, String reciver) {
        this.sender = sender;
        this.message = message;
        this.reciver = reciver;
    }

    public DataModel() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }
}

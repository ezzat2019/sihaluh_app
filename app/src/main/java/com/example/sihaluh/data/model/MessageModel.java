package com.example.sihaluh.data.model;

import java.util.Date;

public class MessageModel {
    private String message_content,sender_id,reciver_id,message_type,message_img,message_pdf;
    private Date date;

    public MessageModel() {
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReciver_id() {
        return reciver_id;
    }

    public void setReciver_id(String reciver_id) {
        this.reciver_id = reciver_id;
    }

    public MessageModel(String message_content, String sender_id, String reciver_id, String message_type, String message_img, String message_pdf, Date date) {
        this.message_content = message_content;
        this.sender_id = sender_id;
        this.reciver_id = reciver_id;
        this.message_type = message_type;
        this.message_img = message_img;
        this.message_pdf = message_pdf;
        this.date = date;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage_img() {
        return message_img;
    }

    public void setMessage_img(String message_img) {
        this.message_img = message_img;
    }

    public String getMessage_pdf() {
        return message_pdf;
    }

    public void setMessage_pdf(String message_pdf) {
        this.message_pdf = message_pdf;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

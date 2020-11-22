package com.example.sihaluh.data.model;

public class MessageModel {
    private String message_content,sender_id,reciver_id;

    public MessageModel(String message_content, String sender_id, String reciver_id) {
        this.message_content = message_content;
        this.sender_id = sender_id;
        this.reciver_id = reciver_id;
    }

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
}

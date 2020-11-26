package com.example.sihaluh.data.model;

import java.util.List;

public class UserChatAddModel {
    private String current_user_id;
    private String current_user_img;
    private String current_user_phone;
    private String name;
    private List<RegisterModel> users_added;

    public UserChatAddModel(String current_user_id, String current_user_img, String current_user_phone, List<RegisterModel> users_added) {
        this.current_user_id = current_user_id;
        this.current_user_img = current_user_img;
        this.current_user_phone = current_user_phone;
        this.users_added = users_added;
    }
    public UserChatAddModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrent_user_id() {
        return current_user_id;
    }

    public void setCurrent_user_id(String current_user_id) {
        this.current_user_id = current_user_id;
    }

    public String getCurrent_user_img() {
        return current_user_img;
    }

    public void setCurrent_user_img(String current_user_img) {
        this.current_user_img = current_user_img;
    }

    public String getCurrent_user_phone() {
        return current_user_phone;
    }

    public void setCurrent_user_phone(String current_user_pgone) {
        this.current_user_phone = current_user_pgone;
    }

    public List<RegisterModel> getUsers_added() {
        return users_added;
    }

    public void setUsers_added(List<RegisterModel> users_added) {
        this.users_added = users_added;
    }
}

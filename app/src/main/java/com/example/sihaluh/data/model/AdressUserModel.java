package com.example.sihaluh.data.model;

public class AdressUserModel {
    private String user_id;
    private String latuide;
    private String longtude;
    private String loction_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLatuide() {
        return latuide;
    }

    public void setLatuide(String latuide) {
        this.latuide = latuide;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public String getLoction_name() {
        return loction_name;
    }

    public void setLoction_name(String loction_name) {
        this.loction_name = loction_name;
    }

    public AdressUserModel() {
    }

    public AdressUserModel(String user_id, String latuide, String longtude, String loction_name) {
        this.user_id = user_id;
        this.latuide = latuide;
        this.longtude = longtude;
        this.loction_name = loction_name;
    }
}

package com.example.sihaluh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AdressUserModel implements Parcelable {
    private String user_id;
    private String latuide;
    private String longtude;
    private String loction_name;
    private String name;

    public AdressUserModel(String user_id, String latuide, String longtude, String loction_name) {
        this.user_id = user_id;
        this.latuide = latuide;
        this.longtude = longtude;
        this.loction_name = loction_name;
    }

    protected AdressUserModel(Parcel in) {
        user_id = in.readString();
        latuide = in.readString();
        longtude = in.readString();
        loction_name = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(latuide);
        dest.writeString(longtude);
        dest.writeString(loction_name);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdressUserModel> CREATOR = new Creator<AdressUserModel>() {
        @Override
        public AdressUserModel createFromParcel(Parcel in) {
            return new AdressUserModel(in);
        }

        @Override
        public AdressUserModel[] newArray(int size) {
            return new AdressUserModel[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdressUserModel(String user_id, String latuide, String longtude, String loction_name, String name) {
        this.user_id = user_id;
        this.latuide = latuide;
        this.longtude = longtude;
        this.loction_name = loction_name;
        this.name = name;
    }

    public AdressUserModel() {
    }
}

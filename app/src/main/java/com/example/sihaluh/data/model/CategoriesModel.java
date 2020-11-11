package com.example.sihaluh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoriesModel implements Parcelable {
    public static final Creator<CategoriesModel> CREATOR = new Creator<CategoriesModel>() {
        @Override
        public CategoriesModel createFromParcel(Parcel in) {
            return new CategoriesModel(in);
        }

        @Override
        public CategoriesModel[] newArray(int size) {
            return new CategoriesModel[size];
        }
    };
    private String name, img, id;

    public CategoriesModel() {
    }

    protected CategoriesModel(Parcel in) {
        name = in.readString();
        img = in.readString();
        id = in.readString();
    }

    public CategoriesModel(String name, String img, String id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(img);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

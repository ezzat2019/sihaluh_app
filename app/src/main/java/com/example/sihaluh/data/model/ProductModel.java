package com.example.sihaluh.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductModel implements Parcelable {
    @ColumnInfo(name = "img")
     String img;
    @ColumnInfo(name = "name")
     String name;
    @ColumnInfo(name = "owner")
     String owner;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
     String id;
    @ColumnInfo(name = "sale")
     String sale;
    @ColumnInfo(name = "price")
     String price;

    protected ProductModel(Parcel in) {
        img = in.readString();
        name = in.readString();
        owner = in.readString();
        id = in.readString();
        sale = in.readString();
        price = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(name);
        dest.writeString(owner);
        dest.writeString(id);
        dest.writeString(sale);
        dest.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ProductModel(String img, String name, String owner, String id, String sale, String price) {
        this.img = img;
        this.name = name;
        this.owner = owner;
        this.id = id;
        this.sale = sale;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", id=" + id +
                ", sale=" + sale +
                ", price=" + price +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }



    public ProductModel() {
    }
}

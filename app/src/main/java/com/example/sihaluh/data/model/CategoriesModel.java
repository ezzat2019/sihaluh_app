package com.example.sihaluh.data.model;

public class CategoriesModel {
    private String name,img,id;

    public CategoriesModel() {
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

    public CategoriesModel(String name, String img, String id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }
}

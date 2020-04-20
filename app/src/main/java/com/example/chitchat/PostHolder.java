package com.example.chitchat;

public class PostHolder {
    private String name;
    private  String id;
    private String imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostHolder() {
    }

    public PostHolder(String name,String id,String imageurl) {
        this.name = name;
        this.id=id;
        this.imageurl=imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

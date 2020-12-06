package com.example.cuahangonline.model;

import com.google.gson.annotations.SerializedName;

public class QuangCao {

    @SerializedName("id")
    public int id;

    @SerializedName("linkimage")
    public String linkImage;

    @SerializedName("linkweb")
    public String linkWeb;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLinkWeb() {
        return linkWeb;
    }

    public void setLinkWeb(String linkWeb) {
        this.linkWeb = linkWeb;
    }
}

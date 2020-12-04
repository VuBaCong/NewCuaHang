package com.example.cuahangonline.model;

import com.google.gson.annotations.SerializedName;

public class KhachHang {

    @SerializedName("response")
    private String Response;

    @SerializedName("email")
    private String email;

    @SerializedName("image")
    private String image;

    @SerializedName("id")
    private int id;

    @SerializedName("checkadmin")
    private int checkAdmin;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckAdmin() {
        return checkAdmin;
    }

    public void setCheckAdmin(int checkAdmin) {
        this.checkAdmin = checkAdmin;
    }
}

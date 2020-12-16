package com.example.cuahangonline.model;

import com.google.gson.annotations.SerializedName;

public class DonHang {

    @SerializedName("response")
    private String Response;

    @SerializedName("iddonhang")
    private int iddonhang;

    public String getResponse() {
        return Response;
    }

    public int getIddonhang() {
        return iddonhang;
    }
}

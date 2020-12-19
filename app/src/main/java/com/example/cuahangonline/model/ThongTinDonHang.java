package com.example.cuahangonline.model;

import com.google.gson.annotations.SerializedName;

public class ThongTinDonHang {

    @SerializedName("id")
    private int id;

    @SerializedName("idkhachhang")
    private int idkhachhang;

    @SerializedName("ngaytao")
    private long ngaytao;

    @SerializedName("trangthai")
    private int trangthai;

    @SerializedName("tennguoidat")
    private String tennguoidat;

    @SerializedName("sodienthoai")
    private String sodienthoai;

    @SerializedName("diachi")
    private String diachi;

    @SerializedName("tongtien")
    private long tongtien;

    public int getId() {
        return id;
    }

    public int getIdkhachhang() {
        return idkhachhang;
    }

    public long getNgaytao() {
        return ngaytao;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public String getTennguoidat() {
        return tennguoidat;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public long getTongtien() {
        return tongtien;
    }
}

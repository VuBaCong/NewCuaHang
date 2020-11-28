package com.example.cuahangonline.model;

public class DanhMuc {
    private int id;
    private String tenmuc;

    public DanhMuc(int id, String tenmuc) {
        this.id = id;
        this.tenmuc = tenmuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmuc() {
        return tenmuc;
    }

    public void setTenmuc(String tenmuc) {
        this.tenmuc = tenmuc;
    }
}

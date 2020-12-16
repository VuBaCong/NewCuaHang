package com.example.cuahangonline.utils.service;

import com.example.cuahangonline.model.DonHang;
import com.example.cuahangonline.model.KhachHang;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<KhachHang> performRegistrantion(
            @Field("email") String Name,
            @Field("username") String UserName,
            @Field("password") String PassWord,
            @Field("image") String Image
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<KhachHang> performUserLogin(
            @Field("username") String UserName,
            @Field("password") String PassWord
    );

    @FormUrlEncoded
    @POST("updateImageUser.php")
    Call<KhachHang> updateImageUser(
            @Field("username") String UserName,
            @Field("image") String Image
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<KhachHang> updatepassword(
            @Field("username") String UserName,
            @Field("password") String PassWord,
            @Field("newpassword") String NewPassWord
    );

}

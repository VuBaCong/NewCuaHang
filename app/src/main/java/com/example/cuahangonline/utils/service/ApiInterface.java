package com.example.cuahangonline.utils.service;
import com.example.cuahangonline.model.KhachHang;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
}

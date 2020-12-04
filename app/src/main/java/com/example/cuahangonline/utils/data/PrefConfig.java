package com.example.cuahangonline.utils.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.Toast;

import com.example.cuahangonline.R;

import java.io.UnsupportedEncodingException;

public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public PrefConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status),status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    public void displayToast(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public void writeEmail(String email){
        String name1= encodeBase64(email);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_email),name1);
        editor.commit();
    }

    public String readEmail(){
        String name=decodeBase64(sharedPreferences.getString(context.getString(R.string.pref_email),"email"));
        return name;
    }

    public void writeIdUser(int iduser){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(context.getString(R.string.pref_iduser),iduser);
        editor.commit();
    }

    public int readIdUser(){
        int id=sharedPreferences.getInt(context.getString(R.string.pref_iduser),-1);
        return id;
    }

    public void writeCheckAdmin(int checkadmin){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(context.getString(R.string.pref_checkadmin),checkadmin);
        editor.commit();
    }

    public int readCheckAdmin(){
        int checkAdmin=sharedPreferences.getInt(context.getString(R.string.pref_checkadmin),0);
        return checkAdmin;
    }

    public void writeLinkImage(String path){
        String name1= encodeBase64(path);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_path),name1);
        editor.commit();
    }
    public String readLinkImage(){
        String path=decodeBase64(sharedPreferences.getString(context.getString(R.string.pref_path),""));
        return path;
    }

    public void writeUserName(String username){
        String name1= encodeBase64(username);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_username),name1);
        editor.commit();
    }
    public String readUserName(){
        String name=decodeBase64(sharedPreferences.getString(context.getString(R.string.pref_username),""));
        return name;
    }

    public void writePassWord(String password){
        String name1= encodeBase64(password);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_password),name1);
        editor.commit();
    }
    public String readPassWord(){
        String name=decodeBase64(sharedPreferences.getString(context.getString(R.string.pref_password),""));
        return name;
    }

    private String encodeBase64(String s){
        byte[] data = new byte[0];
        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }

    private String decodeBase64(String s){
        byte[] data = Base64.decode(s, Base64.DEFAULT);
        String text="";
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
}

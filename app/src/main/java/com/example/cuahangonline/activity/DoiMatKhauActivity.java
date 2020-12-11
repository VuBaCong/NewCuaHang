package com.example.cuahangonline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuahangonline.R;
import com.example.cuahangonline.model.KhachHang;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiMatKhauActivity extends AppCompatActivity {
    private Toolbar tbDoiMK;
    private EditText edtPassWord, edtNewPassWord, edtCom;
    private Button btnDoiMK;
    private ArrayList<KhachHang> mangkhachhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimatkhau);
        Actiontoolbar();
        AnhXa();
    }


    private void DoiMatKhau() {
        mangkhachhang = new ArrayList<>();
        Call<KhachHang> call = LoginActivity.apiInterface.updatepassword(LoginActivity.prefConfig.readUserName(),
                edtPassWord.getText().toString(), edtNewPassWord.getText().toString());
        call.enqueue(new Callback<KhachHang>() {
            @Override
            public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                if (response == null) {
                    return;
                }
                String res = response.body().getResponse();
                if (res.equals("ok")) {
                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                } else if (res.equals("failed")) {
                    Toast.makeText(getApplicationContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra, xin vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<KhachHang> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Có lỗi xảy ra, xin vui lòng thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        edtPassWord = findViewById(R.id.edtPassword);
        edtNewPassWord = findViewById(R.id.edtNewPassword);
        edtCom = findViewById(R.id.edtConfirmPassword);
        btnDoiMK = findViewById(R.id.btnUpdatePassword);

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPassWord.getText().toString().equals("") || edtNewPassWord.getText().toString().equals("") || edtCom.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Mời bạn nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtNewPassWord.getText().toString().equals(edtCom.getText().toString())) {
                        if (edtNewPassWord.getText().toString().length() > 6) {
                            DoiMatKhau();
                        } else {
                            Toast.makeText(getApplicationContext(), "Mât khẩu phải nhiều hơn 6 ký tự", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu mới không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void Actiontoolbar() {
        tbDoiMK = findViewById(R.id.tbChangePassword);
        setSupportActionBar(tbDoiMK);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbDoiMK.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        tbDoiMK.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
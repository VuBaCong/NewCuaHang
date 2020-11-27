package com.example.cuahangonline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cuahangonline.R;
import com.example.cuahangonline.adapter.GioHangAdapter;
import com.example.cuahangonline.utils.checkconnection;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    ListView lvGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolbar();
        CheckData();
        EventUtil();
        CatchOnItemLv();
        EventButton();
    }

    private void EventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), ThongTinKhachHang.class);
                    startActivity(intent);
                } else {
                    checkconnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm đê thanh toán");
                }
            }
        });
    }

    private void CatchOnItemLv() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.manggiohang.size() <= 0) {
                            txtThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.manggiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUtil();
                            if (MainActivity.manggiohang.size() <= 0) {
                                txtThongBao.setVisibility(View.VISIBLE);
                            } else {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUtil() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.manggiohang.size(); i ++){
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien) + "Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0 ){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvGioHang = findViewById(R.id.listviewgiohang);
        txtThongBao = findViewById(R.id.textviewthongbao);
        txtTongTien = findViewById(R.id.textviewtongtien);
        btnThanhToan = findViewById(R.id.buttonthanhtoangiohang);
        btnTiepTucMua = findViewById(R.id.buttontieptucmuahang);
        toolbarGioHang = findViewById(R.id.toolbargiohang);
        gioHangAdapter = new GioHangAdapter(GioHang.this, MainActivity.manggiohang);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
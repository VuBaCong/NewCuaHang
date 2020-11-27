package com.example.cuahangonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cuahangonline.R;
import com.example.cuahangonline.model.Giohang;
import com.example.cuahangonline.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    ImageView imageViewChiTiet;
    TextView txtTen, txtGia, txtMoTa;
    Spinner spinner;
    Button btnDatMua;
    private int id = 0;
    private String tenChiTiet = "";
    private int giaChiTiet = 0;
    private String hinhanhChiTiet = "";
    private String motaChitiet = "";
    private int idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        Actiontoolbar();
        Getinformation();
        CatchEventSpinner();
        EventButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.manggiohang.size(); i++){
                        if (MainActivity.manggiohang.get(i).getIdsp() == id){
                            MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluong() >= 10) {
                                MainActivity.manggiohang.get(i).setSoluong(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(giaChiTiet * MainActivity.manggiohang.get(i).getSoluong());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * giaChiTiet;
                        MainActivity.manggiohang.add(new Giohang(id, tenChiTiet, Giamoi, hinhanhChiTiet, soluong));
                    }
                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * giaChiTiet;
                    MainActivity.manggiohang.add(new Giohang(id, tenChiTiet, Giamoi, hinhanhChiTiet, soluong));
                }
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8 ,9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void Getinformation() {
        sanpham sanpham = (sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        tenChiTiet = sanpham.getTensp();
        giaChiTiet = sanpham.getGiasp();
        hinhanhChiTiet = sanpham.getHinhanhsp();
        motaChitiet = sanpham.getMotasp();
        idsanpham = sanpham.getIdsp();
        txtTen.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá : " + decimalFormat.format(giaChiTiet) + " Đ" );
        txtMoTa.setText(motaChitiet);
        Picasso.get().load(hinhanhChiTiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageViewChiTiet);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChiTiet = (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imageViewChiTiet = (ImageView) findViewById(R.id.imagechitietsanpham);
        txtTen = (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtGia = (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtMoTa = (TextView) findViewById(R.id.textviewmotachitietsanpham);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnDatMua = (Button) findViewById(R.id.buttondatmua);
    }
}
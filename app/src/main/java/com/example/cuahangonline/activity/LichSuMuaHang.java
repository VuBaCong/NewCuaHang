package com.example.cuahangonline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.cuahangonline.R;
import com.example.cuahangonline.adapter.DonHangAdapter;
import com.example.cuahangonline.model.ThongTinDonHang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuMuaHang extends AppCompatActivity {
    private ArrayList<ThongTinDonHang> danhsachdonhang;
    private RecyclerView recyclerView;
    private DonHangAdapter donHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_hang);
        init();
        getData();
    }

    private void init() {
        danhsachdonhang=new ArrayList<>();
        recyclerView=findViewById(R.id.listviewlichsumuahang);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        donHangAdapter=new DonHangAdapter(getApplicationContext(),danhsachdonhang);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(donHangAdapter);
    }

    private void getData() {
        Call<List<ThongTinDonHang>> call=LoginActivity.apiInterface.getThongTinDonHang(LoginActivity.prefConfig.readIdUser());
        call.enqueue(new Callback<List<ThongTinDonHang>>() {
            @Override
            public void onResponse(Call<List<ThongTinDonHang>> call, Response<List<ThongTinDonHang>> response) {
                if (response.body()==null) return;
                danhsachdonhang= (ArrayList<ThongTinDonHang>) response.body();
                donHangAdapter.updateAdapter(danhsachdonhang);
            }

            @Override
            public void onFailure(Call<List<ThongTinDonHang>> call, Throwable t) {

            }
        });
    }
}
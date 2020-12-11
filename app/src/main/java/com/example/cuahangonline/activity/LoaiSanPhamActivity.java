package com.example.cuahangonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.callback.CallBackSanPhamClick;
import com.example.cuahangonline.R;
import com.example.cuahangonline.adapter.SanPhamAdapter;
import com.example.cuahangonline.model.SanPham;
import com.example.cuahangonline.utils.checkconnection;
import com.example.cuahangonline.utils.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoaiSanPhamActivity extends AppCompatActivity {
    private Toolbar toolbardt;
    private RecyclerView rcvSanPham;
    private ArrayList<SanPham> mangdt;
    private SanPhamAdapter loaiSanPhamAdapter;
    private ProgressBar progressBar, progressBarBottom;
    private Boolean isLoading = true;
    private int page = 0;
    private int pastVisbleItems, totallItemCount;
    private int view_threashold = 15;
    private int previous_total = 0;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaisanpham);
        Anhxa();
        setupRecyclerView();
        setTitle();
        Actiontoolbar();
        if (checkconnection.haveNetworkConnection(getApplicationContext())) {
            getData(page);
            loadMoreData();
        } else {
            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại internet");
            finish();
        }
    }

    private void setTitle() {
        String tenloaisp = getIntent().getStringExtra("tenloaisp");
        toolbardt.setTitle(tenloaisp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(int Page) {
        int idloaisanpham = getIntent().getIntExtra("idloaisp", -1);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = server.Duongdandienthoai + String.valueOf(Page) + "&idloaisanpham=" + idloaisanpham;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                progressBarBottom.setVisibility(View.GONE);
                int id = 0;
                String Tendt = "";
                int Giadt = 0;
                String Hinhanhdt = "";
                String Motadt = "";
                int idspdt = 0;
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Giadt = jsonObject.getInt("giasanpham");
                            Hinhanhdt = jsonObject.getString("hinhanhsanpham");
                            Motadt = jsonObject.getString("motasanpham");
                            idspdt = jsonObject.getInt("idloaisanpham");
                            mangdt.add(new SanPham(id, Tendt, Giadt, Hinhanhdt, Motadt, idspdt));
                        }
                        loaiSanPhamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                progressBarBottom.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void Anhxa() {
        mangdt = new ArrayList<>();
        toolbardt = (Toolbar) findViewById(R.id.toolbardienthoai);
        progressBar = findViewById(R.id.progressbar);
        progressBarBottom = findViewById(R.id.progressBarBottom);

    }

    private void setupRecyclerView() {
        rcvSanPham = findViewById(R.id.rcvSanPham);
        loaiSanPhamAdapter = new SanPhamAdapter(mangdt, new CallBackSanPhamClick() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", mangdt.get(position));
                startActivity(intent);
            }
        });
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcvSanPham.setLayoutManager(linearLayoutManager);
        rcvSanPham.setAdapter(loaiSanPhamAdapter);
    }

    private void loadMoreData() {
        rcvSanPham.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totallItemCount = linearLayoutManager.getItemCount();
                pastVisbleItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isLoading) {
                        if (totallItemCount > previous_total) {
                            isLoading = false;
                            previous_total = totallItemCount;
                        }

                    }
                    if (!isLoading && (totallItemCount <= (pastVisbleItems + view_threashold))) {
                        isLoading = true;
                        progressBarBottom.setVisibility(View.VISIBLE);
                        page++;
                        getData(page);
                    }
                }
            }
        });
    }

}
package com.example.cuahangonline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.R;
import com.example.cuahangonline.adapter.loaispadapter;
import com.example.cuahangonline.adapter.sanphamadapter;
import com.example.cuahangonline.model.Giohang;
import com.example.cuahangonline.model.loaisp;
import com.example.cuahangonline.model.sanpham;
import com.example.cuahangonline.utils.checkconnection;
import com.example.cuahangonline.utils.server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<loaisp> mangloaisp;
    loaispadapter loaispadapter;
    int id;
    String tenloaisanpham = "";
    String hinhanhloaisanpham = "";
    ArrayList<sanpham> mangsanpham;
    sanphamadapter sanphamadapter;
    public static ArrayList<Giohang> manggiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(checkconnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            Getdulieuloaisp();
            Getdulieusanphammoinhat();
            Catchonitemlistview();
        }else {
            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
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

    private void Catchonitemlistview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                switch (i){
                    case 0:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DienthoaiActivity.class);
                            intent.putExtra("idloaisp", mangloaisp.get(i).getId());
                            startActivity(intent);
                        } else {
                            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisp", mangloaisp.get(i).getId());
                            startActivity(intent);
                        } else {
                            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienheActivity.class);
                            startActivity(intent);
                        } else {
                            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongtinActivity.class);
                            intent.putExtra("idloaisp", mangloaisp.get(i).getId());
                            startActivity(intent);
                        } else {
                            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void Getdulieusanphammoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int Id = 0;
                    String Tensanpham = "";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham = "";
                    String Motasanpham = "";
                    int Idsanpham = 0;
                    for (int i = 0; i<response.length(); i ++){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensanpham");
                            Giasanpham = jsonObject.getInt("giasanpham");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            Motasanpham = jsonObject.getString("motasanpham");
                            Idsanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new sanpham(Id, Tensanpham, Giasanpham, Hinhanhsanpham, Motasanpham, Idsanpham ));
                            sanphamadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i = 0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisanpham = jsonObject.getString("tenloaisanpham");
                            hinhanhloaisanpham = jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new loaisp(id, tenloaisanpham, hinhanhloaisanpham));
                            loaispadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3, new loaisp(0,"Liên hệ", "https://hwp.com.vn/wp-content/uploads/2017/02/24-7-icon.png"));
                    mangloaisp.add(4, new loaisp(0,"Thông tin", "https://png.pngtree.com/png-vector/20190916/ourlarge/pngtree-info-icon-for-your-project-png-image_1731084.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkconnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://pamarketing.vn/wp-content/uploads/2019/04/tai-sao-nen-day-manh-quang-cao-tren-zalo.jpg");
        mangquangcao.add("https://pamarketing.vn/wp-content/uploads/2017/03/huong-dan-toi-uu-hinh-anh-quang-cao.jpg");
        mangquangcao.add("https://zafago.com/uploads/news/bv56/toi-uu-facebook-ads.jpg");
        mangquangcao.add("https://azseo.vn/wp-content/uploads/2018/12/banner-fb-ads-az1.jpg");
        for(int i = 0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listView = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawverlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new loaisp(0, "Trang Chinh", "https://2.bp.blogspot.com/-85kJoe7oZrU/Ua2fChXk1hI/AAAAAAAAABA/AoYp4qUuLJ0/s1600/home+icon.jpg"));
        loaispadapter = new loaispadapter(mangloaisp, getApplicationContext());
        listView.setAdapter(loaispadapter);
        mangsanpham = new ArrayList<>();
        sanphamadapter = new sanphamadapter(getApplicationContext(), mangsanpham);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3 ));
        recyclerView.setAdapter(sanphamadapter);
        if (manggiohang != null){

        } else {
            manggiohang = new ArrayList<>();
        }
    }
}
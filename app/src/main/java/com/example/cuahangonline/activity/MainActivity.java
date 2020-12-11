package com.example.cuahangonline.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.cuahangonline.R;
import com.example.cuahangonline.adapter.DanhMucAdapter;
import com.example.cuahangonline.adapter.loaispadapter;
import com.example.cuahangonline.model.DanhMuc;
import com.example.cuahangonline.model.Giohang;
import com.example.cuahangonline.model.KhachHang;
import com.example.cuahangonline.model.SanPham;
import com.example.cuahangonline.model.loaisp;
import com.example.cuahangonline.utils.checkconnection;
import com.example.cuahangonline.utils.server;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 24;
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
    private ArrayList<DanhMuc> mangDanhMuc;
    private DanhMucAdapter danhMucAdapter;
    public static ArrayList<Giohang> manggiohang;
    private ProgressBar progressBarDanhMuc;
    private Bitmap bitmap;
    private Call<KhachHang> call;
    private ImageView ivUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();

        if (checkconnection.haveNetworkConnection(getApplicationContext())) {
            setupViewNavigation();
            ActionViewFlipper();
            ActionBar();
            Getdulieumucsanpham();
            Getdulieuloaisp();
            Catchonitemlistview();
        } else {
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
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
            case R.id.search:
                Intent intent1 = new Intent(getApplicationContext(), SanPham.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Catchonitemlistview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if (i == (mangloaisp.size() - 1)) {
                    LoginActivity.prefConfig.writeLoginStatus(false);
                    LoginActivity.prefConfig.writeEmail("email");
                    LoginActivity.prefConfig.writeLinkImage("path");
                    LoginActivity.prefConfig.writeIdUser(-1);
                    LoginActivity.prefConfig.writeCheckAdmin(0);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (i == (mangloaisp.size() - 2)) {
                    Intent intent = new Intent(getApplicationContext(), DoiMatKhauActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoaiSanPhamActivity.class);
                    intent.putExtra("idloaisp", mangloaisp.get(i).getId());
                    intent.putExtra("tenloaisp", mangloaisp.get(i).getTenloaisp());
                    startActivity(intent);
                }
            }
        });
    }

    private void Getdulieumucsanpham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Duongdanmucsanpham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String tenMuc = "";
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenMuc = jsonObject.getString("tenmuc");
                            mangDanhMuc.add(new DanhMuc(id, tenMuc));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    progressBarDanhMuc.setVisibility(View.GONE);
                    danhMucAdapter.notifyDataSetChanged();
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
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisanpham = jsonObject.getString("tenloaisanpham");
                            hinhanhloaisanpham = jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new loaisp(id, tenloaisanpham, hinhanhloaisanpham));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mangloaisp.add(new loaisp(888, "Đổi mật khẩu", "https://png.pngtree.com/png-vector/20190916/ourlarge/pngtree-info-icon-for-your-project-png-image_1731084.jpg"));
                mangloaisp.add(new loaisp(999, "Đăng xuất", "https://png.pngtree.com/png-vector/20190916/ourlarge/pngtree-info-icon-for-your-project-png-image_1731084.jpg"));
                loaispadapter.notifyDataSetChanged();
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
        ArrayList<String> mangquangcao1 = new ArrayList<>();
        mangquangcao1.add("https://cdn.tgdd.vn/Products/Images/42/222596/Feature/oppo-reno4-720x333-3.jpg");
        mangquangcao1.add("https://cdn.tgdd.vn/Products/Images/42/220522/Feature/samsung-galaxy-note-20-ultra-spec-fixed-720x333-720x333.jpg");
        mangquangcao1.add("https://cdn.tgdd.vn/Products/Images/44/223534/Feature/ft-lenovo-thang-12.jpg");
        mangquangcao1.add("https://cdn.tgdd.vn/Products/Images/44/221251/Feature/ft-acer-thang-12.jpg");
        mangquangcao1.add("https://cdn.tgdd.vn/Products/Images/522/228144/Feature/samsung-galaxy-tab-a7-2020-fix-4.jpg");
        mangquangcao1.add("https://cdn.tgdd.vn/Products/Images/522/228809/Feature/ipad-8-wifi-32gb-2020-ft-fix.jpg");
        for (int i = 0; i < mangquangcao1.size(); i++) {
            ImageView imageView = new ImageView(this);

            Glide.with(this).load(mangquangcao1.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_view_headline_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setupViewNavigation() {
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        View viewNav = navigationView.getHeaderView(0);

        TextView tvUser = viewNav.findViewById(R.id.tvUser);
        tvUser.setText(LoginActivity.prefConfig.readUserName());
        ivUser = viewNav.findViewById(R.id.ivUser);
        String urlImage = LoginActivity.prefConfig.readLinkImage();
        Glide.with(getApplicationContext()).load(urlImage).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(ivUser);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        listView = viewNav.findViewById(R.id.listviewmanhinhchinh);
        mangloaisp = new ArrayList<>();
        loaispadapter = new loaispadapter(mangloaisp, getApplicationContext());
        listView.setAdapter(loaispadapter);

    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.title_change_avatar));
        builder.setMessage(getResources().getString(R.string.content_change_avatar));
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openImageResoure();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void openImageResoure() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if (checkconnection.haveNetworkConnection(getApplicationContext())) {
                    updateImageToServer();
                } else {
                    LoginActivity.prefConfig.displayToast(getString(R.string.no_network));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String convertImageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void updateImageToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải ảnh...");
        progressDialog.show();
        String image = convertImageToString();
        String username = LoginActivity.prefConfig.readUserName();
        call = LoginActivity.apiInterface.updateImageUser(username, image);
        call.enqueue(new Callback<KhachHang>() {

            @Override
            public void onResponse(Call<KhachHang> call, retrofit2.Response<KhachHang> response) {
                progressDialog.dismiss();
                KhachHang user = response.body();
                if (!response.isSuccessful()) {
                    return;
                }
                if (user == null) return;
                if (user.getResponse().equals("ok")) {
                    LoginActivity.prefConfig.displayToast(getResources().getString(R.string.upload_image_success));
                    ivUser.setImageBitmap(bitmap);
                }

            }

            @Override
            public void onFailure(Call<KhachHang> call, Throwable t) {
                progressDialog.dismiss();
                if (call.isCanceled()) {
                    Log.d("BBB", "is cancle");
                } else {
                    LoginActivity.prefConfig.displayToast(getResources().getString(R.string.register_error));
                }


            }
        });
    }

    private void Anhxa() {

        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawverlayout);

        recyclerView = findViewById(R.id.recyclerviewDanhMuc);
        mangDanhMuc = new ArrayList<>();
        danhMucAdapter = new DanhMucAdapter(getApplicationContext(), mangDanhMuc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(danhMucAdapter);
        progressBarDanhMuc = findViewById(R.id.progressbarDanhMuc);

        if (manggiohang != null) {

        } else {
            manggiohang = new ArrayList<>();
        }

    }
}
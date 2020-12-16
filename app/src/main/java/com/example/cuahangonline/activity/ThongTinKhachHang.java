package com.example.cuahangonline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.R;
import com.example.cuahangonline.fragment.LoginFragment;
import com.example.cuahangonline.utils.checkconnection;
import com.example.cuahangonline.utils.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHang extends AppCompatActivity {
    EditText edtTenkh, edtSdt, edtDiaChi;
    Button btnXacnhan, btnTrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        Anhxa();
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EventButton();

    }

    private void EventButton() {
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long tongtien = 0;
                for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                    tongtien += MainActivity.manggiohang.get(i).getGiasp();
                }
                final long finalTongtien = tongtien;
                final String ten = edtTenkh.getText().toString().trim();
                final String sdt = edtSdt.getText().toString().trim();
                final String diachi = edtDiaChi.getText().toString().trim();
                if (ten.length() > 0 && sdt.length() > 9 && diachi.length() > 0) {
                    final ProgressDialog progressDialog=new ProgressDialog(ThongTinKhachHang.this);
                    progressDialog.setMessage("Đang đặt hàng...");
                    progressDialog.show();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server.Duongandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang", madonhang);
                            if (Integer.parseInt(madonhang) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, server.Duonganchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        if (response.equals("1")) {
                                            MainActivity.manggiohang.clear();
                                            checkconnection.ShowToast_Short(getApplicationContext(), "Bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                                            startActivity(intent);
                                            checkconnection.ShowToast_Short(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
                                        } else {
                                            checkconnection.ShowToast_Short(getApplicationContext(), "Dữ liệu giỏ hàng của bạn đã bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoluong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tennguoidat", ten);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("diachi", diachi);
                            hashMap.put("idkhachhang", LoginActivity.prefConfig.readIdUser()+"");
                            hashMap.put("ngaytao", Calendar.getInstance().getTimeInMillis()+"");
                            hashMap.put("tongtien", finalTongtien+"");
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    checkconnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Anhxa() {
        edtTenkh = (EditText) findViewById(R.id.edittexttenkhachhang);
        edtSdt = (EditText) findViewById(R.id.edittextsodienthoai);
        edtDiaChi = (EditText) findViewById(R.id.edittextdiachi);
        btnXacnhan = (Button) findViewById(R.id.buttonxacnhan);
        btnTrove = (Button) findViewById(R.id.buttontrove);
    }
}
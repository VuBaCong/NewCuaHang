package com.example.cuahangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangonline.R;
import com.example.cuahangonline.model.DanhMuc;
import com.example.cuahangonline.model.sanpham;
import com.example.cuahangonline.utils.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ItemHolder> {
    private ArrayList<DanhMuc> mangDanhMuc;
    private Context context;

    public DanhMucAdapter(Context context,ArrayList<DanhMuc> mangDanhMuc) {
        this.context=context;
        this.mangDanhMuc = mangDanhMuc;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_mucsanpham, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        View view=holder.getView();
        TextView tvDanhMuc=view.findViewById(R.id.tvmucsanpham);
        DanhMuc sanpham = mangDanhMuc.get(position);
        tvDanhMuc.setText(sanpham.getTenmuc());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerviewSanPham);
        ArrayList<sanpham> arrayList=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        SanPhamAdapter sanphamadapter=new SanPhamAdapter(context,arrayList);
        recyclerView.setAdapter(sanphamadapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        getDuLieuSanPham(sanpham.getId(),arrayList,sanphamadapter);
    }

    private void getDuLieuSanPham(final int idDanhMuc, final ArrayList<sanpham> arrayList, final SanPhamAdapter sanphamadapter) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String duongdan=server.Duongdansanphamdanhmuc+"?idmuc="+idDanhMuc;
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, duongdan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String Tendt = "";
                int Giadt = 0;
                String Hinhanhdt = "";
                String Motadt = "";
                int idspdt = 0;
                if(response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensanpham");
                            Giadt = jsonObject.getInt("giasanpham");
                            Hinhanhdt = jsonObject.getString("hinhanhsanpham");
                            Motadt = jsonObject.getString("motasanpham");
                            idspdt = jsonObject.getInt("idloaisanpham");
                            arrayList.add(new sanpham(id,Tendt,Giadt,Hinhanhdt,Motadt,idspdt));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    sanphamadapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return mangDanhMuc.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        View view;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }
        public View getView() {
            return view;
        }

    }
}

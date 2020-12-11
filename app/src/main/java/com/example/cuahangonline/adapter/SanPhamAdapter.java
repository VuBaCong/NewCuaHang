package com.example.cuahangonline.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangonline.callback.CallBackSanPhamClick;
import com.example.cuahangonline.R;
import com.example.cuahangonline.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private ArrayList<SanPham> mangSanPham;
    private CallBackSanPhamClick callBackSanPhamClick;

    public SanPhamAdapter(ArrayList<SanPham> sanPhams, CallBackSanPhamClick callBackSanPhamClick){
        this.mangSanPham=sanPhams;
        this.callBackSanPhamClick=callBackSanPhamClick;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.dong_dienthoai,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        View view= holder.getView();
        SanPham sanpham=mangSanPham.get(position);
        TextView txttendienthoai = view.findViewById(R.id.textviewdienthoai);
        TextView txtgiadienthoai = view.findViewById(R.id.textviewgiadienthoai);
        TextView txtmotadienthoai = view.findViewById(R.id.textviewmotadienthoai);
        ImageView imagedienthoai = view.findViewById(R.id.imageviewdienthoai);

        txttendienthoai.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiadienthoai.setText("Giá : " + decimalFormat.format(sanpham.getGiasp()) + " Đ");
        txtmotadienthoai.setMaxLines(2);
        txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        txtmotadienthoai.setText(sanpham.getMotasp());
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imagedienthoai);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackSanPhamClick.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangSanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }

        public View getView() {
            return view;
        }
    }
}

package com.example.cuahangonline.adapter;

import android.Manifest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.GioHang;
import com.example.cuahangonline.activity.MainActivity;
import com.example.cuahangonline.callback.CallBackGioHangLongClick;
import com.example.cuahangonline.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {

    Context context;
    ArrayList<Giohang> arrayGioHang;
    private CallBackGioHangLongClick callback;

    public GioHangAdapter(Context context, ArrayList<Giohang> arrayGioHang, CallBackGioHangLongClick callback) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtTenGioHang, txtGiaGioHang;
        public ImageView imgGioHang;
        public Button btnMinus, btnValues, btnPlus;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.txtTenGioHang = view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtGiaGioHang = view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imgGioHang = view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnMinus = view.findViewById(R.id.buttonminus);
            viewHolder.btnValues = view.findViewById(R.id.buttonvalues);
            viewHolder.btnPlus = view.findViewById(R.id.buttonplus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txtTenGioHang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(giohang.getGiasp()) + "Đ");
        Picasso.get().load(giohang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgGioHang);
        viewHolder.btnValues.setText(giohang.getSoluong() + "");
        int sl = Integer.parseInt(viewHolder.btnValues.getText().toString());
        if (sl >= 10) {
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
        } else if (sl <= 1) {
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) + 1;
                int slhientai = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giamoinhat) + "Đ");
                GioHang.EventUtil();
                if (slmoinhat > 9) {
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        finalViewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) - 1;
                int slhientai = MainActivity.manggiohang.get(position).getSoluong();
                long giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluong(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slhientai;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giamoinhat) + "Đ");
                GioHang.EventUtil();
                if (slmoinhat < 2) {
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callback.onLongClick(position);
                return false;
            }
        });
        return view;
    }
}

package com.example.cuahangonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangonline.R;
import com.example.cuahangonline.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class laptopadapter extends BaseAdapter {
    Context context;
    ArrayList<sanpham> arraylaptop;

    public laptopadapter(Context context, ArrayList<sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttenlaptop, txtgialaptop, txtmotalaptop;
        public ImageView imagelaptop;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        laptopadapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop, null);
            viewHolder.txttenlaptop = view.findViewById(R.id.textviewlaptop);
            viewHolder.txtgialaptop = view.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imagelaptop = view.findViewById(R.id.imageviewlaptop);
            view.setTag(viewHolder);
        } else {
            viewHolder = (laptopadapter.ViewHolder) view.getTag();
        }
        sanpham sanpham = (sanpham) getItem(i);
        viewHolder.txttenlaptop.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá : " + decimalFormat.format(sanpham.getGiasp()) + " Đ");
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMotasp());
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imagelaptop);
        return view;
    }
}

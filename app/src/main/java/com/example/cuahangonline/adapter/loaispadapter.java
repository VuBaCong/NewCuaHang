package com.example.cuahangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangonline.R;
import com.example.cuahangonline.model.loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class loaispadapter extends BaseAdapter {
    ArrayList<loaisp> arraylistloaisp;
    Context context;

    public loaispadapter(ArrayList<loaisp> arraylistloaisp, Context context) {
        this.arraylistloaisp = arraylistloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylistloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp, null);
            viewHolder.txttenloaisp = (TextView) view.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = (ImageView) view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        loaisp loaisp = (loaisp) getItem(i);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.get().load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return view;
    }
}
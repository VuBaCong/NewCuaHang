package com.example.cuahangonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangonline.R;
import com.example.cuahangonline.activity.ChiTietSanPham;
import com.example.cuahangonline.model.sanpham;
import com.example.cuahangonline.utils.checkconnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<sanpham> arraysanpham;

    public SanPhamAdapter(Context context, ArrayList<sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " + decimalFormat.format(sanpham.getGiasp()) + " Đ");
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhanhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhsanpham;
        public TextView txttensanpham, txtgiasanpham;
        View view;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanhsanpham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txttensanpham = (TextView) itemView.findViewById(R.id.textviewtensanpham);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham", arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    checkconnection.ShowToast_Short(context, arraysanpham.get(getPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
            view=itemView;
        }
        public View getView() {
            return view;
        }

    }

    public static int getDeviceWidth(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Point point = new Point();
            wm.getDefaultDisplay().getSize(point);
            return point.x;
        } else {
            return wm.getDefaultDisplay().getWidth();
        }
    }
}
package com.example.cuahangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangonline.R;
import com.example.cuahangonline.model.ThongTinDonHang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ItemHolder> {

    private ArrayList<ThongTinDonHang> danhsachdonhang;
    private Context context;

    public DonHangAdapter(Context context, ArrayList<ThongTinDonHang> danhsachdonhang) {
        this.context = context;
        this.danhsachdonhang = danhsachdonhang;
    }

    public void updateAdapter( ArrayList<ThongTinDonHang> danhsachdonhang){
        this.danhsachdonhang = danhsachdonhang;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_donhang, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        View view = holder.getView();
        ThongTinDonHang thongTinDonHang=danhsachdonhang.get(position);
        TextView tvNguoiNhan=view.findViewById(R.id.textviewnguoimuahang);
        TextView tvsdt=view.findViewById(R.id.textviewsodienthoai);
        TextView tvdiachi=view.findViewById(R.id.textviewdiachi);
        TextView tvngaydathang=view.findViewById(R.id.tvngaydathang);
        TextView tvtrangthai=view.findViewById(R.id.textviewtrangthai);
        TextView tvTongTien=view.findViewById(R.id.textviewtongtien);
        tvNguoiNhan.setText("Người nhận: "+thongTinDonHang.getTennguoidat());
        tvsdt.setText("Số điện thoại: "+thongTinDonHang.getSodienthoai()+"");
        tvdiachi.setText("Địa chỉ: "+thongTinDonHang.getDiachi());
        tvngaydathang.setText("Ngày đặt hàng: "+convertTime(thongTinDonHang.getNgaytao()));
        if (thongTinDonHang.getTrangthai()==0){
            tvtrangthai.setText("Trạng thái: Chờ xác nhận");
        }else if (thongTinDonHang.getTrangthai()==1){
            tvtrangthai.setText("Trạng thái: Đã xác nhận");
        }else {
            tvtrangthai.setText("Trạng thái: Đã nhận hàng");
        }
        tvTongTien.setText("Tổng tiền: " + thongTinDonHang.getTongtien());

    }

    private String convertTime(long time){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(time);
    }

    @Override
    public int getItemCount() {
        return danhsachdonhang.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        View view;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public View getView() {
            return view;
        }

    }
}

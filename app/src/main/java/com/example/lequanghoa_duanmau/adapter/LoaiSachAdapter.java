package com.example.lequanghoa_duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lequanghoa_duanmau.R;
import com.example.lequanghoa_duanmau.dao.LoaiSachDAO;
import com.example.lequanghoa_duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LoaiSach> list;

    private LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = loaiSachDAO;
    }

    //quản lý giao diện từng item hiện lên rece
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_type, parent, false);
        return new ViewHolder(view);

    }

    //nơi sử lý dữ liệu truyền vô
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMaLoai.setText("ID: " + list.get(position).getMaloai());
        holder.tvTenLoai.setText(list.get(position).getTenloai());

        holder.ivTypeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });


        //vieetj goi trong hamf khacs nen su dung hoder.
        holder.ivTypeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showw laij dialog
//                showDialogUpdate(list.get(holder.getAdapterPosition()));
                AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn chắc chắn muốn xóa thể loại " + list.get(holder.getAdapterPosition()).getTenloai() + " không?");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getMaloai());
                        switch (check) {
                            case -1:
                                Toast.makeText(context,"Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context,"Bạn cần xóa các cuốn sách trông thể loại này trước", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context,"Xóa thành công", Toast.LENGTH_SHORT).show();
                                //thêm dialog xóa hay ko
                                loadData();
                                break;
                        }
                    }
                });
            builder.setNegativeButton("Không", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();

        //số hiện thị trên recycle
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenLoai, tvMaLoai;
        ImageView ivTypeEdit, ivTypeDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaLoai = itemView.findViewById(R.id.tvMaLoai);
            tvTenLoai = itemView.findViewById(R.id.tvTypeTenLoai);
            ivTypeEdit = itemView.findViewById(R.id.ivTypeEdit);
            ivTypeDelete = itemView.findViewById(R.id.ivTypeDelete);
        }


    }

    private void showDialogUpdate(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_loai, null);
        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //khi nào nhấn nút mới out dialog, bo góc đẹp
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView txtTieuDe = view.findViewById(R.id.tvTieuDe);
        EditText edtTenLoai = view.findViewById(R.id.edtTypeTenloai);
        Button btnLuu = view.findViewById(R.id.btnTypeThem);
        Button btnHuy = view.findViewById(R.id.btnTypeHuy);

        txtTieuDe.setText("Cập nhật thông tin");
        btnLuu.setText("Cập nhật");
        edtTenLoai.setText(loaiSach.getTenloai());

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTenLoai.getText().toString();
                LoaiSach loaiSachUpdate = new LoaiSach(loaiSach.getMaloai(), tenLoai);
                boolean check = loaiSachDAO.suaLoaiSach(loaiSachUpdate);
                if (check) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    //load lại danh sách
                    loadData();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void loadData() {
        list.clear();
        list = loaiSachDAO.getDSLoaiSach();
        notifyDataSetChanged();
    }
}

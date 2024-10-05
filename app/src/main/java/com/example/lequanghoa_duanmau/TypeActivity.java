package com.example.lequanghoa_duanmau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lequanghoa_duanmau.adapter.LoaiSachAdapter;
import com.example.lequanghoa_duanmau.dao.LoaiSachDAO;
import com.example.lequanghoa_duanmau.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class TypeActivity extends AppCompatActivity {

    //b12:data
    private LoaiSachDAO loaiSachDAO;
    private RecyclerView recyclerViewType;
    private ArrayList<LoaiSach> list;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        //b1:thiết kế giao diện( chính + giao diện item)
         recyclerViewType = findViewById(R.id.recyclerViewType);
        FloatingActionButton floatAdd = findViewById(R.id.floatAdd);
        RelativeLayout relativeLayout1 = findViewById(R.id.relativeLoaiSach);

        //b2:data (dao, model)
        loaiSachDAO = new LoaiSachDAO(this);
        list = loaiSachDAO.getDSLoaiSach();

        //b3:adapter load lại danh sách
        loadData();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerViewType.setLayoutManager(linearLayoutManager);
//        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this,list);
//        recyclerViewType.setAdapter(loaiSachAdapter);


        //nhấn nút Float ting
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hieển thị dialog thêm
                showDialogThem();
            }
        });


    }

    //adater
    //load lại data
    //đỏ do biến cục bố
    private void loadData() {
        list = loaiSachDAO.getDSLoaiSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewType.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this, list, loaiSachDAO);
        recyclerViewType.setAdapter(loaiSachAdapter);
    }


    //show dialog
    private void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //đưa lên giao diện
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loai, null);
        builder.setView(view);

        //show
        AlertDialog alertDialog = builder.create();
        // sửa bo góc tran
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //khi nào nhấn nút mới out dialog
        alertDialog.setCancelable(false);
        alertDialog.show();

        //sử dụng thêm
            //ánh xạ
        //sử dụng vieew. do quản lý bởi view
        EditText edtTypeTenLoai = view.findViewById(R.id.edtTypeTenloai);
        Button btnTypeThem = view.findViewById(R.id.btnTypeThem);
        Button btnTypeHuy = view.findViewById(R.id.btnTypeHuy);

        btnTypeThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy dữ liệu ra
                String tenLoai = edtTypeTenLoai.getText().toString();

                //kiểm tra rỗng
                if(tenLoai.equals("")){
                    Toast.makeText(TypeActivity.this,"Bạn chưa nhập thông tin",Toast.LENGTH_SHORT).show();
                    return;
                }

                //gọi hàm ra
                boolean check =  loaiSachDAO.ThemLoaiSach(tenLoai);

                if(check){
//                    Snackbar .make(relativeLayout, "Thêm thành công", Snackbar.LENGTH_SHORT)
//                            .setAction("ok", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            }).show();
                    Toast.makeText(TypeActivity.this,"Thêm Thành công",Toast.LENGTH_SHORT).show();
                    loadData();
                    //ẩn dialog
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(TypeActivity.this,"Them thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //nút hủy
        btnTypeHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}

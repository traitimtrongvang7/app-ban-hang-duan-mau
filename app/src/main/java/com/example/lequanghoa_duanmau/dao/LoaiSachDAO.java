package com.example.lequanghoa_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lequanghoa_duanmau.database.DbHelper;
import com.example.lequanghoa_duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    private DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
       dbHelper = new DbHelper(context);
    }

    //lấy danh sách Loại Sách
    public ArrayList<LoaiSach> getDSLoaiSach(){
        ArrayList<LoaiSach> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount()>0){
            //đưa dữ liệu về: 1: di chuyển con trỏ vị trí đầu
            cursor.moveToFirst();
            //duyệt và add danh sách
            // số 0, 1 là khi sử dụng select lấy tất cả có 2 thuộc tính trả 2 bảng số 0, 1 là thứ tự cột
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        return  list;
        //có bao nhieu lưu vào lists trả về




    }
    //thêm loại sách
    public boolean ThemLoaiSach(String tenLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        //goi hàm insert trả về số(-1 sai,)

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenLoai);
        //thuộc tính muốn thêm trong bảng viết giống SQLLiite

        //trả về kiểu long vì bản chất insert mặt định kiểu long (-1)
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);

//        if(check == -1) {
//            return false;
//        }
//        else{
//            return true;
//        }
        return check != -1;

    }

    public boolean suaLoaiSach(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenloai());

        int check = sqLiteDatabase.update("LOAISACH", contentValues, "maloai = ?", new String[]{String.valueOf(loaiSach.getMaloai())});
        return check !=0;
    }


    //nếu btrar về -1: không xóa được do lỗi hệ thống
    //0: không xóa dduocj(ràng buộc khóa ngoại)
    //1:xóa thành công
    public int xoaLoaiSach (int maLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


        //kiểm tra sự tồn tại của nhũng cuốn sách trong bảng sách với thể loại đang thực hiện xóa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maLoai = ?", new String[]{String.valueOf(maLoai)});
        if(cursor.getCount() >0){
            return 0;
            //không được xóa vì ràng buộc kha ngoại
        }else {
            int check = sqLiteDatabase.delete("LOAISACH","maloai = ?",new String[]{String.valueOf(maLoai)});
            if (check ==0){
                return  - 1;//xóa không dduwoocj vì lỗi, ko tìm thấy loại sach scanaa fxoas
            }else {
                return 1;
            }
        }
    }
}

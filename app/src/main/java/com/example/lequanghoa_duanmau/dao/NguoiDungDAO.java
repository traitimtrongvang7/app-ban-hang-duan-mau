package com.example.lequanghoa_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lequanghoa_duanmau.database.DbHelper;
import com.example.lequanghoa_duanmau.model.NguoiDung;

public class NguoiDungDAO {
    private DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public NguoiDungDAO(Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
    }

    //kieemr tra thoong tin dang nhập
    public boolean KiemTraDangNhap(String username, String password){
        //gọi đến database
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        //con trỏ chuột
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?", new  String[]{username, password});
        //kiểm tra( nếu có giá trị thì true = user+pass)
//        if (cursor.getCount() >0)
//            return true;
//        else return false;
        //lưu role  của account đang đăng nhập
        if (cursor.getCount() >0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("role",cursor.getInt(6));
            editor.apply();
        }
        return cursor.getCount() >0;
    }

    //dang ky
    public boolean dangkyTaiKhoan(NguoiDung nguoiDung){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tennd", nguoiDung.getTennd());
        contentValues.put("sdt", nguoiDung.getSdt());
        contentValues.put("diachi", nguoiDung.getDiachi());
        contentValues.put("tendangnhap", nguoiDung.getTendangnhap());
        contentValues.put("matkhau", nguoiDung.getMatkhau());
        contentValues.put("role", 1);

        long check = sqLiteDatabase.insert("NGUOIDUNG", null,contentValues);
        return check != -1;


    }
}

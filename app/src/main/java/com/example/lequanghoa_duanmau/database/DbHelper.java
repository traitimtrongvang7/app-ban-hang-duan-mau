package com.example.lequanghoa_duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    //tao Constructor
    public DbHelper( Context context) {
        super(context, "QUANLYTHUVIEN", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng loại sách
        String tLoaiSach = "CREATE TABLE LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(tLoaiSach);

        //data mẫu loai sach
        //sự khác nhau
        db.execSQL("INSERT INTO LOAISACH VALUES (1,'thiếu nhi'),(2,'tình cảm'),(3,'hành động')");
//        db.execSQL("INSERT INTO LOAISACH(maloai, tenloai) VALUES (1,'thiếu nhi')");

        //tạo bảng sách
        String tSach = "CREATE TABLE SACH(masach integer primary key autoincrement, tensach text, tacgia text, giaban integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(tSach);
        db.execSQL("INSERT INTO SACH VALUES(1,'Kể cho em nghe', 'Nguyễn Nhật Anh', 15000, 1), (2,'Trạng Quỳnh', 'Kim Đồng', 5000, 1)");
        //bang nguoi dung
        //role: quyen
            //1: Nguoi Dung
            //2: Thu Thu
            //3: Admin
        String tNguoiDung = "CREATE TABLE NGUOIDUNG(mand integer primary key autoincrement, tennd text, sdt text, diachi text, tendangnhap text, matkhau text, role integer)";
        db.execSQL(tNguoiDung);
        db.execSQL("INSERT INTO NGUOIDUNG VALUES(1,'Nguyễn Văn Anh', '0931803103', 'Q12 TP.HCM', 'vananh01', '123456',1)," +
                " (2, 'Trịnh Hòa Bình', '0909090088', 'Q9 TP.HCM','hoabinh01','111222',2), " +
                "(3, 'Lê Văn Hùng', '018218721', 'Q6 TP.HCM','hunglv01','12341234',3)");

        //bang phieu muon
        String tPhieuMuon = "CREATE TABLE PHIEUMUON(mapm integer primary key autoincrement, ngaymuon text, ngaytra text, mand integer references NGUOIDUNG(mand))";
        db.execSQL(tPhieuMuon);
        db.execSQL("INSERT INTO PHIEUMUON VALUES(1,'20/09/20', '26/09/2023', 1)");


        //ch tiet phei muon
        String tCTPM = "CREATE TABLE CTPM(mactpm integer primary key autoincrement, mapm integer  references PHIEUMUON(mapm), masach integer references SACH(masach), soluong integer)";
        db.execSQL(tCTPM);
        db.execSQL("INSERT INTO CTPM VALUES(1,1,1,2),(2,1,2,1)");
    }


    //onUpgade chạy khi version thay đổi
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            db.execSQL("DROP TABLE IF EXISTS CTPM");
            onCreate(db);
        }
    }
}

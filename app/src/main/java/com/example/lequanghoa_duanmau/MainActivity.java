package com.example.lequanghoa_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ánh xạ
        LinearLayout linearLayoutType = findViewById(R.id.linearLoaiSach);
        LinearLayout linearLayoutBook = findViewById(R.id.linearSach);
        LinearLayout linearPM = findViewById(R.id.linearPM);
        LinearLayout linearThongKe = findViewById(R.id.linearThongKe);
        LinearLayout linearLSMS = findViewById(R.id.linearLSPM);

        //Lấy role đã lưu trong shar
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser",MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);

        //1: người dùng
        // 2: thủ thư
        //3 : admin
        switch (role){
            case 1://người dùng
                linearLayoutType.setVisibility(View.GONE);
                linearLayoutBook.setVisibility(View.GONE);
                linearThongKe.setVisibility(View.GONE);
                linearPM.setVisibility(View.GONE);
                break;
            case 2://thủ thư
                linearLayoutType.setVisibility(View.GONE);
                linearLayoutBook.setVisibility(View.GONE);
                linearThongKe.setVisibility(View.GONE);
                linearLSMS.setVisibility(View.GONE);
                break;
            case 3://ADMIN
                linearLSMS.setVisibility(View.GONE);
                break;
            default:
                linearLayoutType.setVisibility(View.GONE);
                linearLayoutBook.setVisibility(View.GONE);
                linearThongKe.setVisibility(View.GONE);
                linearPM.setVisibility(View.GONE);
                linearLSMS.setVisibility(View.GONE);
                break;

        }


        linearLayoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( MainActivity.this, TypeActivity.class));
            }
        });

        linearLayoutBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookActivity.class));
            }
        });
    }
}
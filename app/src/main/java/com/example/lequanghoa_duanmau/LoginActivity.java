package com.example.lequanghoa_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lequanghoa_duanmau.dao.NguoiDungDAO;

public class LoginActivity extends AppCompatActivity {

    //gọi dao
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUser = findViewById(R.id.edtName);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin  = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        //khởi tạo
        nguoiDungDAO = new NguoiDungDAO(this);

        //sử lý sự kiện nút click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                //kiểm tra
                boolean check = nguoiDungDAO.KiemTraDangNhap(user,pass);

                if(check){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu sai",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
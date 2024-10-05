package com.example.lequanghoa_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lequanghoa_duanmau.dao.NguoiDungDAO;
import com.example.lequanghoa_duanmau.model.NguoiDung;

public class RegisterActivity extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        EditText edtUser = findViewById(R.id.edtName);
        EditText edtPass = findViewById(R.id.edtPass);
        EditText edtRePass = findViewById(R.id.edtReturnPass);
        EditText edtName = findViewById(R.id.edtHoTenName);
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtAddress = findViewById(R.id.edtAddress);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnBack = findViewById(R.id.btnBack);

        nguoiDungDAO = new NguoiDungDAO(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edtPass.getText().toString();
                String repass = edtRePass.getText().toString();

                if (!pass.equals(repass)){
                    Toast.makeText(RegisterActivity.this,"Mật khẩu không trùng nhau",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String user = edtUser.getText().toString();
                    String name = edtName.getText().toString();
                    String phone = edtPhone.getText().toString();
                    String address = edtAddress.getText().toString();

                    NguoiDung nguoiDung = new NguoiDung(name,phone, address, user, pass);
                    boolean check = nguoiDungDAO.dangkyTaiKhoan(nguoiDung);
                    if (check){
                        Toast.makeText(RegisterActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();

                    }
//có thể check username có trùng nhau hay khônge
                }
            }
        });
    }
}
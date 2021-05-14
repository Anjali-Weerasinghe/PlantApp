package com.example.planthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.planthome.PlantsManagement.PlantManagerUserInterface;

public class Login extends AppCompatActivity {

    private ImageView back;
    private Button btn1;
    private Button btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       back=(ImageView) findViewById(R.id.btn_back);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openHome();
           }
       });
        Button loginAdmin_btn=(Button) findViewById(R.id.login_as_admin);
        loginAdmin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });

        btn1=(Button) findViewById(R.id.btn_fogotPassword);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
        btn2=(Button) findViewById(R.id.btn_login);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openUserInterface();
            }
        });
        btn3=(Button) findViewById(R.id.btn_forgotPassword);

    }


    private void loginAdmin() {
        Intent intent=new Intent(this,AdminLogin.class);
        startActivity(intent);
    }



    public void openRegister(){
        Intent intent4=new Intent(this,Register.class);
        startActivity(intent4);

    }
    public void openHome(){
        Intent intent3=new Intent(this,Home.class);
        startActivity(intent3);
    }
    public void openUserInterface(){
        Intent intent5=new Intent(this, PlantManagerUserInterface.class);
        startActivity(intent5);
    }

}
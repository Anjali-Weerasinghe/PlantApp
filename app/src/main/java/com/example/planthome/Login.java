package com.example.planthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
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
        Intent intent5=new Intent(this,TutorialManagerUserInterface.class);
        startActivity(intent5);
    }
    public void forgotPassword(){
        Intent intent6=new Intent(this,ChangePassword.class);
        startActivity(intent6);
    }
}
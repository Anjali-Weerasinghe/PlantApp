package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        BottomNavigationView navigationView1=(BottomNavigationView) findViewById(R.id.navigation1);

        navigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1=new Intent(Message.this,Home.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_login:
                        Intent intent2=new Intent(Message.this,Login.class);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
    }
}
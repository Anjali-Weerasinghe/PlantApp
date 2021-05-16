package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView img = (ImageView) findViewById(R.id.imageView4);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Home.this, ItemViwer.class);
//                startActivity(intent);
            }
        });


        BottomNavigationView navigationView1=(BottomNavigationView) findViewById(R.id.navigation1);

        navigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_message:
                        Intent intent1=new Intent(Home.this,Message.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_login:
                        Intent intent2=new Intent(Home.this,Login.class);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
    }
}
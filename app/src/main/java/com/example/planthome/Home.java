package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.planthome.PlantsManagement.Customer_view_plant_item;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    ImageView cus_plant_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cus_plant_view=findViewById(R.id.plant_image_View);


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

    @Override
    protected void onResume() {
        super.onResume();
        cus_plant_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this, Customer_view_plant_item.class);
                startActivity(intent);
            }
        });

    }
}
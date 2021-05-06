package com.example.planthome.CustomerManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.planthome.Home;
import com.example.planthome.R;
import com.google.android.material.navigation.NavigationView;

public class UserInterface extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);



        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.side_nav1);
        toolbar=findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

       navigationView.setNavigationItemSelectedListener(this);

//       Intent intent=getIntent();
//       String userName=intent.getStringExtra("username");



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.side_nav_home:
                break;
            case R.id.side_nav_address:

                Intent intent=getIntent();
                String userName=intent.getStringExtra("username");
                System.out.println(userName);
                Intent intent1=new Intent( UserInterface.this, ViewShippingAddress.class);
                intent1.putExtra("userName",userName);
                startActivity(intent1);
                break;
            case R.id.side_nav_cart:
                Intent intent2=new Intent(UserInterface.this, ShoppingCart.class);
                startActivity(intent2);
                break;
            case R.id.side_nav_payment:
                Intent intent3=new Intent(UserInterface.this, ViewPaymentMethod.class);
                startActivity(intent3);
                break;
            case R.id.side_nav_password:
                Intent intent4=new Intent(UserInterface.this, ChangePassword.class);
                startActivity(intent4);
                break;
            case R.id.side_nav_logout:
                Intent intent5=new Intent(UserInterface.this, Home.class);
                startActivity(intent5);
                break;
        }
        return true;
    }



}
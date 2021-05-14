//package com.example.planthome;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.navigation.NavigationView;
//
//public class PlantHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
//
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Toolbar toolbar;
//    EditText searchPlant;
//    RecyclerView plantRecycleView;
//    FloatingActionButton plant_floating_button;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_plant_home);
//
//
//        searchPlant=findViewById(R.id.plant_search);
//        plantRecycleView=findViewById(R.id.plant_recycleview);
//        plant_floating_button=findViewById(R.id.add_floating_btn);
//        plantRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        plantRecycleView.setHasFixedSize(true);
//
//        drawerLayout=findViewById(R.id.drawerLayout);
//        navigationView=findViewById(R.id.side_nav2);
//        toolbar=findViewById(R.id.toolbar);
//
//
//        setSupportActionBar(toolbar);
//
//        navigationView.bringToFront();
//        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView.setNavigationItemSelectedListener(this);
//
//        plant_floating_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),AddPlant.class));
//            }
//        });
//    }
//
//
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//
//            case R.id.side_nav_home:
//                break;
//            case R.id.side_nav_add_plants:
//                Intent intent1=new Intent( PlantHome.this,AddPlant.class);
//                startActivity(intent1);
//                break;
//
//
//
//        }
//
//
//        return false;
//    }
//}
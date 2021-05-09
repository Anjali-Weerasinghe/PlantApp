package com.example.planthome.PlantsManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.planthome.Home;
import com.example.planthome.PlantsManagement.Model.Plant;
import com.example.planthome.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class PlantManagerUserInterface extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    EditText searchPlant;
    RecyclerView plantRecycleView;
    FloatingActionButton plant_floating_button;

    FirebaseRecyclerOptions<Plant> options;
    FirebaseRecyclerAdapter<Plant,MyViewHolder>adapter;
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_manager_user_interface);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("sampleAdminPlant");

        searchPlant=findViewById(R.id.plant_search);
        plantRecycleView=findViewById(R.id.plant_recycleview);
        plant_floating_button=findViewById(R.id.add_floating_btn);
        plantRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        plantRecycleView.setHasFixedSize(true);

        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.side_nav2);
        toolbar=findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        plant_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddPlant.class));
            }
        });

        loadData("");
        searchPlant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null){
                    loadData(s.toString());
                }else{
                    loadData("");
                }

            }
        });
    }

    private void loadData(String data) {

        Query query=databaseReference.orderByChild("PlantType").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Plant>().setQuery(query,Plant.class).build();
        adapter=new FirebaseRecyclerAdapter<Plant, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Plant model) {
                holder.textViewPlantName.setText(model.getPlantName());
                holder.textViewPlantType.setText(model.getPlantType());
                holder.textViewPlantPrice.setText(String.valueOf(model.getPlantPrice()));
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(PlantManagerUserInterface.this, PlantItemView.class);
                        intent.putExtra("plantKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_view,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        plantRecycleView.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.side_nav_home:
                break;
            case R.id.side_nav_add_plants:
                Intent intent1=new Intent( PlantManagerUserInterface.this,AddPlant.class);
                startActivity(intent1);
                break;
            case R.id.side_nav_logout:
                Intent intent2=new Intent( PlantManagerUserInterface.this, Home.class);
                startActivity(intent2);
                break;




        }


        return false;
    }
}
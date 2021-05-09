package com.example.planthome.CustomerManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.planthome.PlantsManagement.Model.Plant;
import com.example.planthome.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ViewPlants extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RecyclerView cusPlantRecycleView;
    EditText cusSearchPlant;
    private ImageView add,back;

    FirebaseRecyclerOptions<Plant> options;
    FirebaseRecyclerAdapter<Plant,PlantViewHolder> adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plants);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("sampleAdminPlant");

        cusSearchPlant=findViewById(R.id.cus_plant_search);
        cusPlantRecycleView=findViewById(R.id.cus_plant_recycleview1);
        cusPlantRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cusPlantRecycleView.setHasFixedSize(true);

        drawerLayout=findViewById(R.id.drawerLayout);


        loadData("");
        cusSearchPlant.addTextChangedListener(new TextWatcher() {
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

        back=findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back() {
        Intent intent=new Intent(this, UserInterface.class);
        startActivity(intent);
    }


    private void loadData(String data) {

        Query query=databaseReference.orderByChild("PlantType").startAt(data).endAt(data+"\uf8ff");

        options=new FirebaseRecyclerOptions.Builder<Plant>().setQuery(query,Plant.class).build();
        adapter=new FirebaseRecyclerAdapter<Plant, PlantViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PlantViewHolder holder, int position, @NonNull Plant model) {
                holder.textViewPlantName.setText(model.getPlantName());
                holder.textViewPlantType.setText(model.getPlantType());
                holder.textViewPlantPrice.setText(String.valueOf(model.getPlantPrice()));
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ViewPlants.this,Customer_one_plant_item_view.class);
                        intent.putExtra("plantKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_view,parent,false);
                return new PlantViewHolder(v);
            }
        };
        adapter.startListening();
        cusPlantRecycleView.setAdapter(adapter);
    }
}
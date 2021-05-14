package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.planthome.TutorialManagement.AddTutorials;
import com.example.planthome.TutorialManagement.ManageTutorials;
import com.example.planthome.TutorialManagement.Model.Tutes;
import com.example.planthome.TutorialManagement.SearchTutesActivity;
import com.example.planthome.TutorialManagement.TuteViewHolder.TuteViewHolder;
import com.example.planthome.TutorialManagement.Update_Tutorials;
import com.example.planthome.TutorialManagement.ViewTuteMethodActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class TutorialManager extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private DatabaseReference TutesReference;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //edit
    private String type="Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_manager);
        //edit
       //type=getIntent().getExtras().get("Admin").toString();
//       Intent intent =getIntent();
//       Bundle bundle =intent.getExtras();
//    if (bundle!=null)
//       {
//          type=getIntent().getExtras().get("Admin").toString();
//      }


        TutesReference = FirebaseDatabase.getInstance().getReference().child("Tutes");
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.side_nav3);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        recyclerView= findViewById((R.id.plantTute_recycleview));
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Tutes> options =
                new FirebaseRecyclerOptions.Builder<Tutes>()
                        .setQuery(TutesReference, Tutes.class)
                        .build();
        FirebaseRecyclerAdapter<Tutes, TuteViewHolder> adapter=
                new FirebaseRecyclerAdapter<Tutes, TuteViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TuteViewHolder holder, int i, @NonNull Tutes model)
                    {
                        holder.plntType.setText(model.getType());
                        holder.plntName.setText(model.getName());
                        Picasso.get().load(model.getImage()).into(holder.pntImage);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //edit
                                if (type.equals("Admin"))
                                {
                                    Intent intent=new Intent(TutorialManager.this, Update_Tutorials.class);
                                    intent.putExtra("tid",model.getTid());
                                    startActivity(intent);
                                }
                                else
                                {

                                    Intent intent=new Intent(TutorialManager.this, ViewTuteMethodActivity.class);
                                    intent.putExtra("tid",model.getTid());
                                    startActivity(intent);

                                }

                                //Intent intent=new Intent(TutorialManagerUserInterface.this, ViewTuteMethodActivity.class);
                                //intent.putExtra("tid",model.getTid());
                                //startActivity(intent);
                            }
                        });



                    }

                    @NonNull
                    @Override
                    public TuteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tute_view, parent, false);
                        TuteViewHolder holder = new TuteViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.side_nav_home:
                break;
            case R.id.side_nav_add_tutorials:
                Intent intent1=new Intent( TutorialManager.this, AddTutorials.class);
                startActivity(intent1);
                break;
            case R.id.side_nav_manage_tutorials:
                Intent intent2=new Intent(TutorialManager.this, ManageTutorials.class);
                startActivity(intent2);
                break;
            case R.id.side_nav_search:
                Intent intent3=new Intent(TutorialManager.this, SearchTutesActivity.class);
                startActivity(intent3);
                break;
            case R.id.side_nav_logout:
                Intent intent4=new Intent(TutorialManager.this, Home.class);
                startActivity(intent4);
                break;

        }
        return false;
    }
}
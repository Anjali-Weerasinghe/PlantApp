package com.example.planthome;



import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;

import com.example.planthome.Model.Items;
import com.example.planthome.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.security.AlgorithmParameterGenerator;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;

import static com.example.planthome.R.id.side_nav_logout;

public class AdminProfileNavigation extends AppCompatActivity implements AdminProfileNavigations {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference ItemsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile_navigation);



        ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }@Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(ItemsRef, Items.class)
                        .build();

        FirebaseRecyclerAdapter<Items, ItemViewHolder> adapter =
                new FirebaseRecyclerAdapter<Items, ItemViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Items model) {

                        holder.txtItemCode.setText(model.getItemKey());
                        holder.txtItemCategory.setText(model.getCategory());
                        holder.txtItemName.setText(model.getName());
                        holder.txtItemPrice.setText("Price = Rs." + model.getPrice() + ".00");
                        holder.txtDescription.setText(model.getDescription());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        String type = holder.txtItemCategory.toString();

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                                if (type.equals("Clay") || type.equals("Concrete") || type.equals("Concrete") || type.equals("Foam") || type.equals("Ceramic") || type.equals("Metal") || type.equals("Fiber")) {
//                                    Intent intent = new Intent(AdminProfileNavigation.this, UpdatePots.class);
//                                    intent.putExtra("itemKey",model.getItemKey());
//                                    startActivity(intent);
//
//                                }
//                                else{
//                                    Intent intent = new Intent(AdminProfileNavigation.this, UpdateFertilizer.class);
//                                    intent.putExtra("itemKey",model.getItemKey());
//                                    startActivity(intent);
//                                }
                                        Intent intent = new Intent(AdminProfileNavigation.this, UpdatePots.class);
                                        intent.putExtra("itemKey",model.getItemKey());
                                        startActivity(intent);



                            }
                        });

                    }


                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pots_and_fertilizer_viewer, parent, false);
                        ItemViewHolder holder = new ItemViewHolder(view);
                        return holder;
                    }


                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_profile_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//        int id = item.getItemId();
        switch (item.getItemId()){

            case R.id.nav_home:
                Intent intent = new Intent(AdminProfileNavigation.this,Home.class);
                startActivity(intent);
                break;
            case R.id.nav_addpot:

                Intent intent1=new Intent( AdminProfileNavigation.this,addnewpot.class);
                startActivity(intent1);
                break;
            case R.id.nav_addfertilizer:
                Intent intent2=new Intent(AdminProfileNavigation.this,addnewfertilizer.class);
                startActivity(intent2);
                break;
        }


//        if (id == R.id.nav_home) {
//            Intent intent1 = new Intent(AdminProfileNavigation.this,Home.class);
//            startActivity(intent1);
//
//
//        }
//        else if (id == R.id.nav_addpot) {
//
//            Intent intent2  = new Intent(AdminProfileNavigation.this,addnewpot.class);
//            startActivity(intent2);
//
//        }
//        else if (id == R.id.nav_addfertilizer) {
//            Intent intent3 = new Intent(AdminProfileNavigation.this,addnewfertilizer.class);
//            startActivity(intent3);
//
//        }
//
//        else if (id == R.id.side_nav_logout) {
//
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
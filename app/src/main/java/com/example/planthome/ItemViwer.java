package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.planthome.Model.Items;
import com.example.planthome.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

public class ItemViwer extends AppCompatActivity {

    private DatabaseReference ItemsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_viwer);



        ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
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
                        holder.txtItemCategory.setText(model.getCategory());
                        holder.txtItemName.setText(model.getItemName());
                        holder.txtItemPrice.setText("Price = Rs." + model.getPrice() + ".00");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ItemViwer.this, ItemDetails.class);
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
}
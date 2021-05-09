package com.example.planthome.CustomerManagement;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.CustomerManagement.Model.Plants;
import com.example.planthome.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ShoppingCart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView back;
    private Button checkOut;
    private Double TotalPrice=0.00;
    private TextView totalPrice;
    FirebaseRecyclerAdapter<Plants,CartViewHolder> adapter;
    FirebaseRecyclerOptions<Plants> options;
    DatabaseReference reference;
    CartItemAdapter cartItemAdapter;
    ArrayList<Plants> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView=findViewById(R.id.yourBagRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        totalPrice=findViewById(R.id.cartTextView4);

        checkOut=findViewById(R.id.checkout);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent=new Intent(ShoppingCart.this, PlaceOrder.class);
                        startActivity(intent);
                        finish();
            }
        });
        back=findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShoppingCart.this, UserInterface.class);
                startActivity(intent);
            }
        });
        String Nic = CurrentOnlineCustomer.currentOnlineCustomer.getNic();
        reference=FirebaseDatabase.getInstance().getReference("CustomerCart").child(Nic);


        list=new ArrayList<Plants>();
        cartItemAdapter=new CartItemAdapter(list, this);
        recyclerView.setAdapter(cartItemAdapter);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Plants plants=dataSnapshot.getValue(Plants.class);
                    list.add(plants);

                }
                cartItemAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}
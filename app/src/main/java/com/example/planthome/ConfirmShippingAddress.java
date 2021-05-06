package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.Model.CustomerAddressHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmShippingAddress extends AppCompatActivity {

    private ImageView add,back;

    RecyclerView recyclerView;
    DatabaseReference reference;
    ConfirmShippingAddressAdapter confirmShippingAddressAdapter;
    ArrayList<CustomerAddressHelperClass> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_shipping_address);

        String nic=getIntent().getStringExtra("userName");
        String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
        System.out.println("current user nic="+Nic);
        recyclerView=findViewById(R.id.confirmShippingAddressRecycleView);

        reference= FirebaseDatabase.getInstance().getReference("customerAddress").child(Nic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<CustomerAddressHelperClass>();
        confirmShippingAddressAdapter =new ConfirmShippingAddressAdapter(list,this);
        recyclerView.setAdapter(confirmShippingAddressAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                    String value=String.valueOf(snapshot.child("no").getValue());
//                    System.out.println(value);
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CustomerAddressHelperClass customerAddressHelperClass=dataSnapshot.getValue(CustomerAddressHelperClass.class);

                    list.add(customerAddressHelperClass);
                }
                confirmShippingAddressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        back=findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConfirmShippingAddress.this,PlaceOrder.class);
                startActivity(intent);
            }
        });
    }
}
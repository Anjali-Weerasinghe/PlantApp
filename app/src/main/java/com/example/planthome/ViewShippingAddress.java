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
import com.example.planthome.CustomerManagement.UserInterface;
import com.example.planthome.Model.CustomerAddressHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewShippingAddress extends AppCompatActivity {


    private ImageView add,back;

    RecyclerView recyclerView;
    DatabaseReference reference;
    CustomerAdapter customerAdapter;
    ArrayList<CustomerAddressHelperClass> list;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shipping_address);

        String nic=getIntent().getStringExtra("userName");
        String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
        System.out.println("current user nic="+Nic);
        recyclerView=findViewById(R.id.recyclerView1);

        reference=FirebaseDatabase.getInstance().getReference("customerAddress").child(Nic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<CustomerAddressHelperClass>();
        customerAdapter=new CustomerAdapter(this,list);
        recyclerView.setAdapter(customerAdapter);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                    String value=String.valueOf(snapshot.child("no").getValue());
//                    System.out.println(value);
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CustomerAddressHelperClass customerAddressHelperClass=dataSnapshot.getValue(CustomerAddressHelperClass.class);

                    list.add(customerAddressHelperClass);
                }
                customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        add=(ImageView) findViewById(R.id.address_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openAddShippingAddress();
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


    private void openAddShippingAddress() {


        Intent intent1=getIntent();
        String userName=intent1.getStringExtra("userName");
        System.out.println(userName);
        Intent intent=new Intent(this,AddShippingAddress.class);
        intent.putExtra("username",userName);
        startActivity(intent);

    }
}
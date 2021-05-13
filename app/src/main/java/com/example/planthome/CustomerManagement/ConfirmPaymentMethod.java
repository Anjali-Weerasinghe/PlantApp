package com.example.planthome.CustomerManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.CustomerManagement.Model.CustomerPaymentHelperClass;
import com.example.planthome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfirmPaymentMethod extends AppCompatActivity {
    private ImageView add,back;

    RecyclerView recyclerView;
    DatabaseReference reference;
    ConfirmPaymentAdapter confirmPaymentAdapter;
    ArrayList<CustomerPaymentHelperClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment_method);

        String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
        System.out.println("current user nic="+Nic);
        recyclerView=findViewById(R.id.confirmPaymentRecycleView);

        reference= FirebaseDatabase.getInstance().getReference("customerPaymentMethod").child(Nic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<CustomerPaymentHelperClass>();
        confirmPaymentAdapter =new ConfirmPaymentAdapter(list,this);
        recyclerView.setAdapter(confirmPaymentAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                    String value=String.valueOf(snapshot.child("no").getValue());
//                    System.out.println(value);
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CustomerPaymentHelperClass customerPaymentHelperClass=dataSnapshot.getValue(CustomerPaymentHelperClass.class);

                    list.add(customerPaymentHelperClass);
                }
                confirmPaymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        back=findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConfirmPaymentMethod.this, PlaceOrder.class);
                startActivity(intent);
            }
        });

    }

}


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
import com.example.planthome.Model.CustomerPaymentHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPaymentMethod extends AppCompatActivity {

    private ImageView add,back;
    RecyclerView recyclerView;
    DatabaseReference reference;
    CustomerPaymentAdapter customerPaymentAdapter;
    ArrayList<CustomerPaymentHelperClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment_method);
        String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();

        recyclerView=findViewById(R.id.PaymentRecyclerView);
        reference= FirebaseDatabase.getInstance().getReference("customerPaymentMethod").child(Nic);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list= new ArrayList<>();
        customerPaymentAdapter=new CustomerPaymentAdapter(this,list);
        recyclerView.setAdapter(customerPaymentAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CustomerPaymentHelperClass customerPaymentHelperClass=dataSnapshot.getValue(CustomerPaymentHelperClass.class);

                    list.add(customerPaymentHelperClass);
                }
                customerPaymentAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        add=(ImageView) findViewById(R.id.Payment_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPaymentMethod();
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

    private void openAddPaymentMethod() {
        Intent intent1=getIntent();
        Intent intent=new Intent(this,AddPaymentMethod.class);
        startActivity(intent);

    }

    private void back() {
        Intent intent=new Intent(this, UserInterface.class);
        startActivity(intent);
    }
}
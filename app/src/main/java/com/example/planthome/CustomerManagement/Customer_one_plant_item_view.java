package com.example.planthome.CustomerManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.CustomerManagement.Model.CustomerAddressHelperClass;
import com.example.planthome.CustomerManagement.Model.Plants;
import com.example.planthome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Customer_one_plant_item_view extends AppCompatActivity {
    ImageView cusOneImageView;
    TextView CusPlantName, CusPlantType, CusPlantPrice;
    Button CusAddCartBtn;

    DatabaseReference databaseReference, dataReference,reference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_one_plant_item_view);

        cusOneImageView = findViewById(R.id.cus_single_plant_item_img_view);
        CusPlantName = findViewById(R.id.text_cus_plant_item_name);
        CusPlantType = findViewById(R.id.text_cus_plant_item_type);
        CusPlantPrice = findViewById(R.id.text_cus_plant_item_price);
        CusAddCartBtn=findViewById(R.id.plant_add_cart_button);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("sampleAdminPlant");

       reference = FirebaseDatabase.getInstance().getReference().child("CustomerCart");


        String plantKey = getIntent().getStringExtra("plantKey");
        dataReference = FirebaseDatabase.getInstance().getReference().child("sampleAdminPlant").child(plantKey);
        storageReference = FirebaseStorage.getInstance().getReference().child("PlantImage").child(plantKey + ".jpg");


        databaseReference.child(plantKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String plantName = snapshot.child("PlantName").getValue().toString();
                    String plantPrice = snapshot.child("PlantPrice").getValue().toString();
                    String plantType = snapshot.child("PlantType").getValue().toString();
                    String plantImgUrl = snapshot.child("ImageUrl").getValue().toString();
                    CusAddCartBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
                            Double Price = Double.parseDouble(plantPrice);


                            Plants plants=new  Plants(plantName,plantType,Price,plantImgUrl,plantKey);
                            reference.child(Nic).child(plantKey).setValue(plants).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Customer_one_plant_item_view.this,"Your Item Added Successfully",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Customer_one_plant_item_view.this,ShoppingCart.class);
                                        intent.putExtra("plantKey",plantKey);
                                        startActivity(intent);


                                    } else {
                                        Toast.makeText(Customer_one_plant_item_view.this,"Faild",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });


                    Picasso.get().load(plantImgUrl).into(cusOneImageView);
                    CusPlantName.setText(plantName);
                    CusPlantPrice.setText(plantPrice);
                    CusPlantType.setText(plantType);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
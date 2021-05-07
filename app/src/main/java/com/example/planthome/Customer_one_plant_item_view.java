package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView CusPlantName,CusPlantType,CusPlantPrice;
    Button CusAddCartBtn;

    DatabaseReference databaseReference,dataReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_one_plant_item_view);

        cusOneImageView=findViewById(R.id.cus_single_plant_item_img_view);
        CusPlantName=findViewById(R.id.text_cus_plant_item_name);
        CusPlantType=findViewById(R.id.text_cus_plant_item_type);
        CusPlantPrice=findViewById(R.id.text_cus_plant_item_price);
        CusAddCartBtn=findViewById(R.id.plant_add_cart_button);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("sampleAdminPlant");


        String plantKey=getIntent().getStringExtra("plantKey");
        dataReference=FirebaseDatabase.getInstance().getReference().child("sampleAdminPlant").child(plantKey);
        storageReference= FirebaseStorage.getInstance().getReference().child("PlantImage").child(plantKey+".jpg");



        databaseReference.child(plantKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String plantName=snapshot.child("PlantName").getValue().toString();
                    String plantPrice=snapshot.child("PlantPrice").getValue().toString();
                    String plantType=snapshot.child("PlantType").getValue().toString();
                    String plantImgUrl=snapshot.child("ImageUrl").getValue().toString();

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
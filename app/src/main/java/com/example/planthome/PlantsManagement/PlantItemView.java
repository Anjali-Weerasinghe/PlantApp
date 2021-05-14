package com.example.planthome.PlantsManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.planthome.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PlantItemView extends AppCompatActivity {
    ImageView imageView;
    TextView pl_name,pl_type,pl_price;
    Button plant_delete_btn;

    DatabaseReference databaseReference,dataReference;
    StorageReference  storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_item_view);

        imageView=findViewById(R.id.single_plant_item_img_view);
        pl_name=findViewById(R.id.text_plant_item_name);
        pl_type=findViewById(R.id.text_plant_item_type);
        pl_price=findViewById(R.id.text_plant_item_price);
        plant_delete_btn=findViewById(R.id.plant_delete_button);

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

                    Picasso.get().load(plantImgUrl).into(imageView);
                    pl_name.setText(plantName);
                    pl_price.setText(plantPrice);
                    pl_type.setText(plantType);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        plant_delete_btn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
              AlertDialog diaBox = AskDeleteOption();
                diaBox.show();

           }
       });
   }

    private AlertDialog AskDeleteOption()
    {
        AlertDialog DeleteDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
               .setMessage("Do you want to Delete")

                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dataReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(getApplicationContext(),PlantManagerUserInterface.class));
                                    }
                                });
                            Toast.makeText(PlantItemView.this, "Plant Item Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return DeleteDialogBox;
    }

}
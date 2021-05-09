package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateFertilizer extends AppCompatActivity {

    private Button item_update, item_delete;
    private EditText item_Name, item_Price, item_Description;
    Spinner spinner;
    private ImageView imageView1;
    private String itemKey = "" , category, saveCurrentDate, saveCurrentTime ;

    private DatabaseReference itemsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fertilizer);

        itemKey = getIntent().getStringExtra("itemKey");
        itemsRef = FirebaseDatabase.getInstance().getReference().child("Pots").child(itemKey);

        item_update = findViewById(R.id.fertilizer_update_btn);
        item_Name = findViewById(R.id.fertilizer_name_update);
        item_Price = findViewById(R.id.fertilizer_price_update);
        item_Description = findViewById(R.id.fertilizer_description_update);
        spinner = findViewById(R.id.fertilizer_spinner_update);
        imageView1 = findViewById(R.id.fertilizer_image_update);
        item_delete = findViewById(R.id.fertilizer_delete_btn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories2, R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MM DD yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());


        item_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });

        item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisItem();
            }
        });




        displaySpecificItemInfo();
    }



    private void deleteThisItem() {

        itemsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateFertilizer.this,"Item is deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void applyChanges() {

        String itname = item_Name.getText().toString();
        String itprice = item_Price.getText().toString();
        String itdescription = item_Description.getText().toString();
        category = spinner.getSelectedItem().toString();

        if(itname.equals("")){
            Toast.makeText(this,"Write down Item Name", Toast.LENGTH_SHORT).show();
        }
        else if(itprice.equals("")){
            Toast.makeText(this,"Write down Item Price", Toast.LENGTH_SHORT).show();
        }
        else if(itdescription.equals("")){
            Toast.makeText(this,"Write down Item Description", Toast.LENGTH_SHORT).show();
        }
        else{

            HashMap<String, Object> itemMap = new HashMap<>();

            itemMap.put("name", itname);
            itemMap.put("date", saveCurrentDate);
            itemMap.put("time", saveCurrentTime);
            itemMap.put("price", itprice);
            itemMap.put("description", itdescription);
            itemMap.put("category", category);

            itemsRef.updateChildren(itemMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(UpdateFertilizer.this,"Changes applied successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UpdateFertilizer.this,AdminProfileNavigation.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
        }

    }

    private void displaySpecificItemInfo() {
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String itemName = snapshot.child("name").getValue().toString();
                    String itemPrice = snapshot.child("price").getValue().toString();
                    String itemDescription = snapshot.child("description").getValue().toString();
                    String imageView = snapshot.child("image").getValue().toString();

                    item_Name.setText(itemName);
                    item_Price.setText(itemPrice);
                    item_Description.setText(itemDescription);
                    Picasso.get().load(imageView).into(imageView1);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
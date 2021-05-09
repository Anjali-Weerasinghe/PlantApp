package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.planthome.Model.Items;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ItemDetails extends AppCompatActivity {

//    private FloatingActionButton addToCartBtn;
    private Button addToCartButton;
    private ImageView itemImage;
    private ElegantNumberButton numberButton;
    private TextView itemPrice, itemDescription, itemName, itemCode;
    private String itemKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemKey = getIntent().getStringExtra("itemKey");

        addToCartButton = (Button) findViewById(R.id.it_add_to_cart_button);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        itemImage = (ImageView) findViewById(R.id.item_image_details);
        itemName = (TextView) findViewById(R.id.item_name_details);
        itemPrice = (TextView) findViewById(R.id.item_price_details);
        itemDescription= (TextView) findViewById(R.id.item_description_details);
        itemCode = (TextView) findViewById(R.id.item_code_details);

        getItemDetails(itemKey);
    }

    private void getItemDetails(String itemKey){
        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference().child("Pots");

        itemsRef.child(itemKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Items items = snapshot.getValue(Items.class);

                    itemCode.setText(items.getItemKey());
                    itemName.setText(items.getItemName());
                    itemPrice.setText("Price = Rs." + items.getPrice() +".00");
                    itemDescription.setText("Description - " + items.getDescription());
                    Picasso.get().load(items.getImage()).into(itemImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
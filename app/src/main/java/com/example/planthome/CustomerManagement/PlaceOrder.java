package com.example.planthome.CustomerManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.planthome.R;

public class PlaceOrder extends AppCompatActivity {

    TextView confirm_shipping_Address,confirm_payment_method;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        confirm_shipping_Address=findViewById(R.id.select_shipping_address);
        confirm_shipping_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlaceOrder.this, ConfirmShippingAddress.class);
                startActivity(intent);
            }
        });
        confirm_payment_method=findViewById(R.id.select_payment_method);
        confirm_payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlaceOrder.this, ConfirmPaymentMethod.class);
                startActivity(intent);
            }
        });

        back=findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlaceOrder.this, ShoppingCart.class);
                startActivity(intent);
            }
        });
    }
}
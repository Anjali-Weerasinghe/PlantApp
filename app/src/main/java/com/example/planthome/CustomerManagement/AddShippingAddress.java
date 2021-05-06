package com.example.planthome.CustomerManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.CustomerManagement.Model.CustomerAddressHelperClass;
import com.example.planthome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShippingAddress extends AppCompatActivity {

    Button saveAddressBtn;
    ImageView back;
    TextInputLayout  saveNo,saveAddress1,saveAddress2,saveCity,saveCountry,savePostalCode;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipping_address);

        saveNo=findViewById(R.id.no);
        saveAddress1=findViewById(R.id.address1);
        saveAddress2=findViewById(R.id.address2);
        saveCity=findViewById(R.id.city);
        saveCountry=findViewById(R.id.country);
        savePostalCode=findViewById(R.id.postalCode);
        saveAddressBtn=findViewById(R.id.save_address);
        back=findViewById(R.id.btn_back);

        saveAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateNo() | !validateAddress1()  | !validateCity() | !validatePostalCode() | !validateCountry()){
                    return;
                }
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference().child("customerAddress");

                String no=saveNo.getEditText().getText().toString();
                String address1=saveAddress1.getEditText().getText().toString();
                String address2=saveAddress2.getEditText().getText().toString();
                String city=saveCity.getEditText().getText().toString();
                String country=saveCountry.getEditText().getText().toString();
                String postalCode=savePostalCode.getEditText().getText().toString();
                String key=(no+address1);
                String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
                System.out.println(no);

                CustomerAddressHelperClass helperClass=new CustomerAddressHelperClass(Nic,no,address1,address2,city,country,postalCode);
                reference.child(Nic).child(no).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(AddShippingAddress.this,"Your Shipping Address Added successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AddShippingAddress.this, ViewShippingAddress.class);
                            startActivity(intent);
                        }


                    }
                });


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

    }

    private void back() {
        Intent intent=new Intent(this,ViewShippingAddress.class);
        startActivity(intent);
    }

    private Boolean validateNo(){
        String val=saveNo.getEditText().getText().toString();

        if(val.isEmpty()){
            saveNo.setError("No is Required");
            return false;
        }
        else{
            saveNo.setError(null);
            saveNo.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateAddress1(){
        String val=saveNo.getEditText().getText().toString();

        if(val.isEmpty()){
            saveAddress1.setError("Address1 is Required");
            return false;
        }
        else{
            saveAddress1.setError(null);
            saveAddress1.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateCity(){
        String val=saveCity.getEditText().getText().toString();

        if(val.isEmpty()){
            saveCity.setError("City is Required");
            return false;
        }
        else{
            saveCity.setError(null);
            saveCity.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateCountry(){
        String val=saveCountry.getEditText().getText().toString();

        if(val.isEmpty()){
            saveCountry.setError("Country is Required");
            return false;
        }
        else{
            saveCountry.setError(null);
            saveCountry.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePostalCode(){
        String val=savePostalCode.getEditText().getText().toString();


        if(val.isEmpty()){
            savePostalCode.setError("PostalCode is Required");
            return false;
        }else{
            savePostalCode.setError(null);
            savePostalCode.setErrorEnabled(false);
            return true;
        }
    }
}
package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.CustomerManagement.ChangePassword;
import com.example.planthome.CustomerManagement.UserInterface;
import com.example.planthome.CustomerManagement.Model.Customer;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
        TextInputLayout username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        Button login_btn = (Button) findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginCustomer();
            }
        });

        Button loginAdmin_btn=(Button) findViewById(R.id.login_as_admin);
        loginAdmin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });
        ImageView back = (ImageView) findViewById(R.id.btn_back);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openHome();
           }
       });


        Button btn1 = (Button) findViewById(R.id.btn_fogotPassword);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

        Button btn3 = (Button) findViewById(R.id.btn_forgotPassword);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    private void loginAdmin() {
        Intent intent=new Intent(this,AdminLogin.class);
        startActivity(intent);
    }

    private void loginCustomer() {

        if(!validateNIC() | !validatePassword()){
            return;
        }
        else{
            isCustomer();
        }
    }

    private Boolean validateNIC(){

        String val=username.getEditText().getText().toString();
        String noWhiteSpace="(?=\\S+$)";
        if(val.isEmpty()){
            username.setError("NIC is Required");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){

        String val=password.getEditText().getText().toString();

        if(val.isEmpty()) {
            password.setError("Password is Required");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    private void isCustomer() {

        String customerEnteredNIC=username.getEditText().getText().toString().trim();
        String customerEnteredPassword=password.getEditText().getText().toString().trim();

        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference();



        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("customer").child(customerEnteredNIC).exists()){
                    username.setError(null);
                    username.setErrorEnabled(false);

                    Customer customer=snapshot.child("customer").child(customerEnteredNIC).getValue(Customer.class);

                    if(customer.getNic().equals(customerEnteredNIC)){
                        username.setError(null);
                        username.setErrorEnabled(false);
                        if(customer.getPassword().equals(customerEnteredPassword)){



                            Intent intent=new Intent(getApplicationContext(), UserInterface.class);

                            CurrentOnlineCustomer.currentOnlineCustomer=customer;
                            startActivity(intent);

                        }


                    }
                    else{
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
                else{
                    username.setError("No such User Exit");
                    password.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void openRegister(){
        Intent intent4=new Intent(this,Register.class);
        startActivity(intent4);

    }
    public void openHome(){
        Intent intent3=new Intent(this,Home.class);
        startActivity(intent3);
    }

    public void forgotPassword(){
        Intent intent6=new Intent(this, ChangePassword.class);
        startActivity(intent6);
    }
}
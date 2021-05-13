package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private Button btnRegister;
    TextInputLayout regEmail,regPassword,regName,regNic,regMobile;
    ImageView back;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail = findViewById(R.id.Email);
        regPassword = findViewById(R.id.password);
        regName = findViewById(R.id.fullName);
        regMobile = findViewById(R.id.mobile);
        regNic = findViewById(R.id.nic);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CreateAccount();

            }
        });
    }

    private void CreateAccount() {

        String email=regEmail.getEditText().getText().toString();
        String password=regPassword.getEditText().getText().toString();
        String name=regName.getEditText().getText().toString();
        String mobile=regMobile.getEditText().getText().toString();
        String nic=regNic.getEditText().getText().toString();

        if(!validateName() | !validateMobile()  | !validateEmail() | !validatePassword() | !validateNIC()){
            return;
        }

        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("customer").child(nic).exists())) {

                    HashMap<String,Object> customerDataMap=new HashMap<>();


                    customerDataMap.put("nic",nic);
                    customerDataMap.put("name",name);
                    customerDataMap.put("email",email);
                    customerDataMap.put("mobile",mobile);
                    customerDataMap.put("password",password);
                    reference.child("customer").child(nic).updateChildren(customerDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Register.this,"Congratulations,Your Account Successfully Created.",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Register.this,Login.class);
                                startActivity(intent);
                            }
                            else {

                                Toast.makeText(Register.this,"Error,Please try again later.",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                else {
                    Toast.makeText(Register.this,"Your NIC already exits.",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register.this,Login.class);
                    startActivity(intent);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void back() {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }

    private Boolean validateName(){
        String val=regName.getEditText().getText().toString();

        if(val.isEmpty()){
            regName.setError("Full Name is Required");
            return false;
        }else if(val.length()>=25){
            regName.setError("Full Name too long");
            return false;
        }else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateNIC(){
        String val=regNic.getEditText().getText().toString();

        if(val.isEmpty()){
            regNic.setError("NIC is Required");
            return false;
        }
        else if(val.length()>12 || val.length()<11|| !val.matches("\\w*")){
            regNic.setError("NIC is Wrong");
            return false;

        }
        else{
            regNic.setError(null);
            regNic.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail(){
        String val=regEmail.getEditText().getText().toString();
        String emailPattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Email is Required");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid Email Type");
            return false;
        }else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateMobile(){
        String val=regMobile.getEditText().getText().toString();

        if(val.isEmpty()){
            regMobile.setError("Mobile Number is Required");
            return false;
        }else if(val.length()>10 ||!val.matches("\\w*")){
            regMobile.setError("Invalid Mobile Number");
            return false;
        }else{
            regMobile.setError(null);
            regMobile.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val=regPassword.getEditText().getText().toString();
        String passwordval="^"+"(?=.*[a-zA-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{4,}"+"$";
        if(val.isEmpty()){
            regPassword.setError("Password is Required");
            return false;
        }else if(!val.matches(passwordval)){
            regPassword.setError("Password is too weak");
            return false;
        }else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
//    public void openRegister(){
//        Intent intent4=new Intent(this,AddShippingAddress.class);
//        startActivity(intent4);
//
//    }
}
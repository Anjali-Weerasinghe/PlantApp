package com.example.planthome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.planthome.Model.CustomerHelperClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private Button btn1;
    TextInputLayout regEmail,regPassword,regName,regNic,regMobile;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        regEmail=findViewById(R.id.Email);
        regPassword=findViewById(R.id.password);
        regName=findViewById(R.id.fullName);
        regMobile=findViewById(R.id.mobile);
        regNic=findViewById(R.id.nic);

        btn1=(Button) findViewById(R.id.btn_register);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("customer");

//                if(!validateName() | !validateMobile()  | !validateEmail() |validatePassword()){
//                    return;
//                }
                String email=regEmail.getEditText().getText().toString();
                String password=regPassword.getEditText().getText().toString();
                String name=regName.getEditText().getText().toString();
                String mobile=regMobile.getEditText().getText().toString();
                String nic=regNic.getEditText().getText().toString();



                CustomerHelperClass helperClass= new CustomerHelperClass(name,nic,mobile,email,password);
                reference.child(nic).setValue(helperClass);

//                openRegister();
            }
        });
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
        String noWhiteSpace="(?=\\S+$)";
        if(val.isEmpty()){
            regNic.setError("NIC is Required");
            return false;
        }else if(val.length()>12){
            regNic.setError("Invalid Nic type");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            regNic.setError("White Spaces are not allowed");
            return false;
        }
        else{
            regName.setError(null);
            regName.setErrorEnabled(false);
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
        }else if(val.length()>10){
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
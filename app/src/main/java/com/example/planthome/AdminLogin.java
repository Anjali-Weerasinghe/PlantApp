package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {
    TextInputLayout username,password;
    Spinner spinner;
    Button loginAdmin_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        username=findViewById(R.id.usernameAdmin);
        password=findViewById(R.id.passwordAdmin);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.userType,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        loginAdmin_btn=findViewById(R.id.btn_loginAdmin);
        loginAdmin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });


    }

    private void loginAdmin() {

        if(!validateNIC() | !validatePassword()){
            return;
        }
        else{
            isAdmin();
        }
    }

    private void isAdmin() {

        String adminEnteredNIC=username.getEditText().getText().toString().trim();
        String adminEnteredPassword=password.getEditText().getText().toString().trim();

        String item=spinner.getSelectedItem().toString();

        if(item.equals("Plant Manager")){
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AdminPlant");

            Query checkAdmin=reference.orderByChild("nic").equalTo(adminEnteredNIC);

            checkAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String passwordFromDB=snapshot.child(adminEnteredNIC).child("password").getValue(String.class);
                        if(passwordFromDB.equals(adminEnteredPassword)){
                            username.setError(null);
                            username.setErrorEnabled(false);

                            String usernameFromDB=snapshot.child(adminEnteredNIC).child("nic").getValue(String.class);

                            Intent intent=new Intent(getApplicationContext(),PlantManager.class);
                            intent.putExtra("username",usernameFromDB);
                            System.out.println(usernameFromDB);
                            startActivity(intent);

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
        }else if(item.equals("Tutorial Manager")){

            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AdminTutorial");

            Query checkAdmin=reference.orderByChild("nic").equalTo(adminEnteredNIC);

            checkAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String passwordFromDB=snapshot.child(adminEnteredNIC).child("password").getValue(String.class);
                        if(passwordFromDB.equals(adminEnteredPassword)){
                            username.setError(null);
                            username.setErrorEnabled(false);

                            String usernameFromDB=snapshot.child(adminEnteredNIC).child("nic").getValue(String.class);



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
        else{

            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AdminPots");

            Query checkAdmin=reference.orderByChild("nic").equalTo(adminEnteredNIC);

            checkAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String passwordFromDB=snapshot.child(adminEnteredNIC).child("password").getValue(String.class);
                        if(passwordFromDB.equals(adminEnteredPassword)){
                            username.setError(null);
                            username.setErrorEnabled(false);

                            String usernameFromDB=snapshot.child(adminEnteredNIC).child("nic").getValue(String.class);

                            Intent intent=new Intent(getApplicationContext(),addnewpot.class);
                           // intent.putExtra("username",usernameFromDB);
                            //System.out.println(usernameFromDB);
                            startActivity(intent);

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
}
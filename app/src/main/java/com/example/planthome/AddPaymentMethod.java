package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.Model.CustomerPaymentHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPaymentMethod extends AppCompatActivity {

    Button saveAddressBtn;
    ImageView back;
    TextInputLayout saveCardNo,saveExpiryDateMonth,saveExpiryDateYear,saveCvc;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner spinnerMonth,spinnerYear;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_method);

        saveCardNo=findViewById(R.id.cardNo);
//        saveExpiryDateMonth=findViewById(R.id.expiry_date_month);
//        saveExpiryDateYear=findViewById(R.id.expiry_date_year);
        spinnerMonth=findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> adapterMonth=ArrayAdapter.createFromResource(this,R.array.month,R.layout.support_simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);
        spinnerYear=findViewById(R.id.year_spinner);
        ArrayAdapter<CharSequence> adapterYear=ArrayAdapter.createFromResource(this,R.array.year,R.layout.support_simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
        saveCvc=findViewById(R.id.cvc);
        radioGroup=findViewById(R.id.paymentGroup);



//        Toast.makeText(AddPaymentMethod.this,radioButton.getText(),Toast.LENGTH_SHORT).show();


        saveAddressBtn=findViewById(R.id.paymentSave_btn);
        saveAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateCardNO() | !validateCVC() ){
                    return;
                }
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference().child("customerPaymentMethod");

                String cardNo=saveCardNo.getEditText().getText().toString();
                String expiryDateMonth= spinnerMonth.getSelectedItem().toString();
                String expiryDateYear=spinnerYear.getSelectedItem().toString();
                String cvc=saveCvc.getEditText().getText().toString();
                String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
                String type=radioButton.getText().toString();
                String expiryDate="expiryDateMonth"+"/"+"expiryDateYear";


                CustomerPaymentHelperClass customerPaymentHelperClass=new CustomerPaymentHelperClass(cardNo,expiryDate,cvc,Nic,type);
                reference.child(Nic).child(cardNo).setValue(customerPaymentHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddPaymentMethod.this,"Your Payment Method Added successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddPaymentMethod.this,ViewPaymentMethod.class);
                        startActivity(intent);
                    }
                });
            }
        });
        back=findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }
    public void checkBtn(View v){
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
    }

    private void back() {
        Intent intent=new Intent(this,ViewPaymentMethod.class);
        startActivity(intent);
    }
    private Boolean validateCardNO() {
        String val = saveCardNo.getEditText().getText().toString();


        if (val.isEmpty()) {
            saveCardNo.setError("Card Number is Required");
            return false;
        } else {
            saveCardNo.setError(null);
            saveCardNo.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateCVC() {
        String val = saveCardNo.getEditText().getText().toString();


        if (val.isEmpty()) {
            saveCvc.setError("CVC is Required");
            return false;
        }  else if (val.length() == 3) {
            saveCvc.setError("Invalid CVC Type");
            return false;
        } else {
            saveCvc.setError(null);
            saveCvc.setErrorEnabled(false);
            return true;
        }
    }
//    private Boolean validateExpiryDateMonth() {
//        String val = saveExpiryDateMonth.getEditText().getText().toString();
//
//
//        if (val.isEmpty()) {
//            saveExpiryDateMonth.setError("Month is Required");
//            return false;
//        }else if (val.equals("01")|val.equals("02")|val.equals("03")|val.equals("04")|val.equals("05")|val.equals("06")|val.equals("07")|val.equals("08")|val.equals("09")|val.equals("10")|val.equals("11")|val.equals("12")) {
//            saveCvc.setError("Invalid Month Type");
//            return false;
//        } else {
//            saveExpiryDateMonth.setError(null);
//            saveExpiryDateMonth.setErrorEnabled(false);
//            return true;
//        }
//    }
//    private Boolean validateExpiryDateYear() {
//        String val = saveExpiryDateYear.getEditText().getText().toString();
//
//
//        if (val.isEmpty()) {
//            saveExpiryDateYear.setError("Year is Required");
//            return false;
//        }  else {
//            saveExpiryDateYear.setError(null);
//            saveExpiryDateYear.setErrorEnabled(false);
//            return true;
//        }
//    }
}
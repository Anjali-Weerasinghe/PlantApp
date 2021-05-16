package com.example.planthome;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class addnewfertilizer extends AppCompatActivity {

    private Button btn_add;
    private EditText add_ItemCode, add_Name, add_Price, add_Description;
    private ImageView add_Image;
    private ImageView btn_back;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ItemImagesRef;
    private DatabaseReference ItemsRef;
    private ProgressDialog loadingBar;

    Spinner spinner;

    private String itemCode, name, price, description, category, saveCurrentDate, saveCurrentTime, itemKey, downloadImageUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewfertilizer);

        ItemImagesRef = FirebaseStorage.getInstance().getReference().child("PotsImages");
        ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots");


        btn_add = (Button) findViewById(R.id.btn_addFertilizer);
        btn_back = (ImageView) findViewById(R.id.backbtn_image);
        add_Image = (ImageView) findViewById(R.id.add_imageFertilizer);
        add_ItemCode = (EditText) findViewById(R.id.addFertilizerCode);
        add_Name = (EditText) findViewById(R.id.addFertilizerName);
        add_Price = (EditText) findViewById(R.id.addFertilizerPrice);
        add_Description = (EditText) findViewById(R.id.addFertilizerDescription);
        loadingBar = new ProgressDialog(this);



        spinner = findViewById(R.id.spinner_2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories2, R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //category = spinner.getSelectedItem().toString();

//        if(category.equals("Clay")){
//            ItemImagesRef = FirebaseStorage.getInstance().getReference().child("Pots").child("ClayPotsImages");
//            ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots").child("ClayPots");
//        }
//        else if(category.equals("Metal")){
//            ItemImagesRef = FirebaseStorage.getInstance().getReference().child("Pots").child("MetalPotsImages");
//            ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots").child("MetalPots");
//        }
//        else if(category.equals("Concrete")){
//            ItemImagesRef = FirebaseStorage.getInstance().getReference().child("Pots").child("ConcretePotsImages");
//            ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots").child("ConcretePots");
//        }
//        else if(category.equals("Ceramic")){
//            ItemImagesRef = FirebaseStorage.getInstance().getReference().child("Pots").child("CeramicPotsImages");
//            ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots").child("CeramicPots");
//        }
//        else if(category.equals("Fiber")){
//            ItemImagesRef = FirebaseStorage.getInstance().getReference().child("Pots").child("FiberPotsImages");
//            ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots").child("FiberPots");
//        }
//        else if(category.equals("Foam")){
//            ItemImagesRef = FirebaseStorage.getInstance().getReference().child("Pots").child("FoamPotsImages");
//            ItemsRef = FirebaseDatabase.getInstance().getReference().child("Pots").child("FoamPots");
//        }




        add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePotData();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addnewfertilizer.this, addnewfertilizer.class);
                startActivity(intent);
            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode == RESULT_OK && data!= null){
            ImageUri = data.getData();
            add_Image.setImageURI(ImageUri);
        }
    }

    private void ValidatePotData(){
        itemCode = add_ItemCode.getText().toString();
        name = add_Name.getText().toString();
        price = add_Price.getText().toString();
        description = add_Description.getText().toString();
        category = spinner.getSelectedItem().toString();

        if(ImageUri == null){
            Toast.makeText(this, "Item image is mandatory..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(itemCode)){
            Toast.makeText(this,"Item code is mandatory..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Item name is mandatory..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this,"Item price is mandatory..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description)){
            Toast.makeText(this,"Item description is mandatory..",Toast.LENGTH_SHORT).show();
        }
        /*else if (category.equals("Choose")){
            Toast.makeText(this,"Choose item category is mandatory..",Toast.LENGTH_SHORT).show();
        }*/
        else{
            StoreItemInformation();
        }
    }

    private void StoreItemInformation() {
        loadingBar.setTitle("Add New Fertilizer");
        loadingBar.setMessage("Please wait, while we are adding the new item.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();




        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MM DD yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        itemKey = itemCode;

        StorageReference filePath = ItemImagesRef.child(ImageUri.getLastPathSegment() + itemKey + "jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure (@NonNull Exception e){
                String message = e.toString();
                Toast.makeText(addnewfertilizer.this,"Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addnewfertilizer.this,"Item image uploaded successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(addnewfertilizer.this, "got the item image url successfully..", Toast.LENGTH_SHORT).show();

                            SaveItemInfoToDatabase();

                        }
                    }
                });
            }

        });
    }

    private void SaveItemInfoToDatabase() {
        HashMap<String, Object> itemMap = new HashMap<>();
        itemMap.put("itemKey", itemKey);
        itemMap.put("name", name);
        itemMap.put("date", saveCurrentDate);
        itemMap.put("time", saveCurrentTime);
        itemMap.put("price", price);
        itemMap.put("description", description);
        itemMap.put("image", downloadImageUrl);
        itemMap.put("category", category);


        ItemsRef.child(itemKey).updateChildren(itemMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(addnewfertilizer.this, addnewfertilizer.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(addnewfertilizer.this, "Item is added successfully..", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(addnewfertilizer.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
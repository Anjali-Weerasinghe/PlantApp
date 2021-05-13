package com.example.planthome.PlantsManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planthome.PlantsManagement.samplePlantItem;
import com.example.planthome.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class sampleAddPlant extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_IMAGE =101 ;
    EditText plant_name, plant_price;
    Button add_plant_btn,upload_plant_img_btn;
    Spinner plant_type;
    ImageView add_plant_image;
    TextView text_img_progress;
    ProgressBar img_progressBar;


    Uri addImageUri;
    boolean isImageAdded=false;

    DatabaseReference databaseMenu;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_add_plant);



        plant_name = (EditText) findViewById(R.id.edit_add_plant_name);
        plant_price = (EditText) findViewById(R.id.edit_add_plant_price);
        plant_type = (Spinner) findViewById(R.id.dropdown_add_plant_type);
        add_plant_btn = (Button) findViewById(R.id.btn_plant_add);
        upload_plant_img_btn=findViewById(R.id.btn_plant_img_upload);
        add_plant_image =findViewById(R.id.add_plant_image);
        text_img_progress=findViewById(R.id.text_image_progress);
        img_progressBar=findViewById(R.id.image_progress_bar);

        text_img_progress.setVisibility(View.GONE);
        img_progressBar.setVisibility(View.GONE);

        databaseMenu = FirebaseDatabase.getInstance().getReference("samplemenuItem");
        storageReference= FirebaseStorage.getInstance().getReference().child("samplePlantImage");


        add_plant_image.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_ADD_IMAGE);
            }
        });

        upload_plant_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isImageAdded!=false ){
                    addImage();
                }
            }
        });



    }

    private void addImage() {
        text_img_progress.setVisibility(View.VISIBLE);
        img_progressBar.setVisibility(View.VISIBLE);

        final String key= databaseMenu.push().getKey();
        storageReference.child(key+".jpg").putFile(addImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap=new HashMap();

                        hashMap.put("Image Url",uri);



                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(snapshot.getBytesTransferred()*100)/snapshot.getTotalByteCount();
                img_progressBar.setProgress((int)progress);
                text_img_progress.setText(progress +"%");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_IMAGE && data!=null){
            addImageUri=data.getData();
            isImageAdded=true;
            add_plant_image.setImageURI( addImageUri);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        add_plant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenu();

            }
        });
    }

    private void addMenu(){
        String pName=plant_name.getText().toString().trim();
        String pType=plant_type.getSelectedItem().toString();
        Integer pPrice=Integer.parseInt(plant_price.getText().toString());


        if(!TextUtils.isEmpty(pName)){

            String plant_id =  databaseMenu.push().getKey();
            samplePlantItem pItem=new samplePlantItem(plant_id,pName,pType,pPrice);
            databaseMenu.child(plant_id).setValue(pItem);
            Toast.makeText(this,"Menu Item Added",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"You must enter a name",Toast.LENGTH_LONG).show();
        }
    }
}
package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddPlant extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_IMAGE =101;
    private EditText addPlantName,addPlantPrice;
    private Spinner addMenuType;
    private ImageView addPlantImage;
    private TextView textImgProgress;
    private ProgressBar imgProgressBar;
    private Button addPlantButton;


    Uri addImageUri, dbIMG;
    boolean isImageAdded=false;

    DatabaseReference databaseReference;
    StorageReference storageReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        addPlantName=findViewById(R.id.edit_add_plant_name);
        addPlantPrice=findViewById(R.id.edit_add_plant_price);
        addMenuType=findViewById(R.id.dropdown_add_plant_type);
        addPlantImage=findViewById(R.id.add_plant_image);
        textImgProgress=findViewById(R.id.text_image_progress);
        imgProgressBar=findViewById(R.id.image_progress_bar);
        addPlantButton=findViewById(R.id.btn_plant_add);

        textImgProgress.setVisibility(View.GONE);
        imgProgressBar.setVisibility(View.GONE);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("sampleAdminPlant");
        storageReference= FirebaseStorage.getInstance().getReference().child("PlantImage");






    }

    @Override
    protected void onResume() {
        super.onResume();

        addPlantImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE_ADD_IMAGE);
            }
        });

        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String plantName=  addPlantName.getText().toString().trim();
                final String plantType= addMenuType.getSelectedItem().toString();
                String ptnPrice =addPlantPrice.getText().toString();
                double ptnPriceValue=0;
                if(!ptnPrice.isEmpty())
                    try {
                        ptnPriceValue = Double.parseDouble(ptnPrice);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

//               if( plantName!=null && plantType!=null && plantPrice!=null && isImageAdded!=false ){
//                   addPlant(plantName,plantType,plantPrice);
//               }
                Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                Matcher matcher = pattern.matcher(plantName);
                boolean isStringContainsSpecialCharacter = matcher.find();

                //if(TextUtils.isEmpty(plantName))
                if(isStringContainsSpecialCharacter)
                {
                    addPlantName.setError("Please Enter Plant Name...");
                    return;
                }
                if(plantType.equals("Select plant type"))
                {
                    Toast.makeText(AddPlant.this, "Please Select Plant Type...", Toast.LENGTH_SHORT).show();
                    return;
                }
                 if(ptnPriceValue == 0)
                {
                    addPlantPrice.setError("Enter a price");
                    return;

                }
                 if(isImageAdded==false)
                {
                    Toast.makeText(AddPlant.this, "Please select image....", Toast.LENGTH_SHORT).show();
                    return;

                }

                addPlant(plantName,plantType,ptnPriceValue);

            }
        });


    }

    private void addPlant(final String plantName, final String plantType, final Double plantPrice) {
            textImgProgress.setVisibility(View.VISIBLE);
            imgProgressBar.setVisibility(View.VISIBLE);

            final String key=databaseReference.push().getKey();
            storageReference.child(key+".jpg").putFile(addImageUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress=(snapshot.getBytesTransferred()*100)/snapshot.getTotalByteCount();
                        imgProgressBar.setProgress((int)progress);
                        textImgProgress.setText(progress +"%");
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    textImgProgress.setVisibility(View.INVISIBLE);
                    imgProgressBar.setVisibility(View.INVISIBLE);

                    storageReference.child(key+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap hashMap=new HashMap();
                            hashMap.put("PlantName",plantName);
                            hashMap.put("PlantPrice",plantPrice);
                            hashMap.put("PlantType",plantType);
                            hashMap.put("ImageUrl",uri.toString());

                            databaseReference.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(getApplicationContext(),PlantManagerUserInterface.class));
                                    Toast.makeText(AddPlant.this,"Plant Item Added",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    finish();
                                }
                            });


                        }
                    });



                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_IMAGE && data!=null){
                addImageUri=data.getData();
                isImageAdded=true;
                addPlantImage.setImageURI( addImageUri);
        }
    }
}
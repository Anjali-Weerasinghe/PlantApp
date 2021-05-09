package com.example.planthome.TutorialManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.planthome.R;
import com.example.planthome.TutorialManager;
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

public class AddTutorials extends AppCompatActivity {

    private String plantName,plantM,saveCurrentData,saveCurrentTime,tuteType;////  final String plantType= addMenuType.getSelectedItem().toString();
    private  EditText pName,pMethod;
    private ImageView addPlantImage;
    private Button add;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String tuteRandomKey,downloadImageURL;
    private StorageReference TuteImageRef;
    private DatabaseReference TuteRef;
    private ProgressDialog loadingBar;
    private  Spinner addTuteType;

//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tutorials);

        TuteImageRef = FirebaseStorage.getInstance().getReference().child("plant images");
        TuteRef = FirebaseDatabase.getInstance().getReference().child("Tutes");

        add=(Button)findViewById(R.id.button_Addtute);
        addPlantImage=(ImageView)findViewById(R.id.textImageAdd);
        addTuteType=(Spinner) findViewById(R.id.spinnerAddTuteType);

        pName=(EditText)findViewById(R.id.editTuteName);
        pMethod=(EditText)findViewById(R.id.textViewAddTuteMethodBox);

        loadingBar =new ProgressDialog(this);

        addPlantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateTuteData();
            }
        });
    }
    private void OpenGallery(){
        Intent galleryIntent =new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            addPlantImage.setImageURI(ImageUri);
        }
    }

    private void  ValidateTuteData(){
        tuteType=addTuteType.getSelectedItem().toString();
        plantName=pName.getText().toString();
        plantM=pMethod.getText().toString();


        if(ImageUri==null)
        {
            Toast.makeText(this, "Plant image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(tuteType))
        {
            Toast.makeText(this, "Please Select Plant Type...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(plantName))
        {
            Toast.makeText(this, "Please Enter Plant Name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(plantM))
        {
            Toast.makeText(this, "Please Enter Planting Method....", Toast.LENGTH_SHORT).show();
        }
        else {

            StoreTuteInformation();

        }
    }

    private void StoreTuteInformation() {

        loadingBar.setTitle("Adding New Tute");
        loadingBar.setMessage(" Dear Admin Please wait,while we are adding the new tute.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentData=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentData =currentData.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calendar.getTime());

        tuteRandomKey =saveCurrentData +saveCurrentTime;

        StorageReference filePath=TuteImageRef.child(ImageUri.getLastPathSegment() + tuteRandomKey + ".jpg");
        final UploadTask uploadTask= filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddTutorials.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddTutorials.this, "Plant Image Uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Task<Uri>uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException() ;

                        }
                        downloadImageURL =filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageURL = task.getResult().toString();
                            Toast.makeText(AddTutorials.this, "got Plant Image  Url Successfully...", Toast.LENGTH_SHORT).show();

                            SavePlantInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SavePlantInfoToDatabase()
    {
        HashMap<String, Object> tuteMap =new HashMap<>();
        tuteMap.put("tid",tuteRandomKey);
        tuteMap.put("date",saveCurrentData);
        tuteMap.put("time",saveCurrentTime);
        tuteMap.put("image",downloadImageURL);
        tuteMap.put("type",tuteType);
        tuteMap.put("name",plantName);
        tuteMap.put("met",plantM);

        TuteRef.child(tuteRandomKey).updateChildren(tuteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Intent intent =new Intent(AddTutorials.this, TutorialManager.class);
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(AddTutorials.this, "Tute is added Succesfully...", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AddTutorials.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

}
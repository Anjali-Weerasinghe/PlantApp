package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Update_Tutorials extends AppCompatActivity {

    private Button editButton;
    private EditText editName,editMethod;
    private Spinner edittype;
    private ImageView editImage;
    private  String tutorialID="";
    private DatabaseReference tuteRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__tutorials);

        tutorialID =getIntent().getStringExtra("tid");
        tuteRef= FirebaseDatabase.getInstance().getReference().child("Tutes").child(tutorialID);

        editButton=(Button)findViewById(R.id.button_update);
        editImage=(ImageView)findViewById(R.id.plantTuteUpdateMethod_Image);
        //edittype=(Spinner) findViewById(R.id.editTypePlantTute);
        editName=(EditText) findViewById(R.id.editTutePlntName);
        editMethod=(EditText) findViewById(R.id.methodTuteUpdate);

        displaySpecificTuteInfo();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                applychanges();
            }
        });
    }

    private void applychanges()
    {
        String ePName= editName.getText().toString();
        String ePMeth= editMethod.getText().toString();
        //String ePType= edittype.getText().toString();
        if (ePName.equals(""))
        {
            Toast.makeText(this, "Write Down Plant Name", Toast.LENGTH_SHORT).show();
        }
        else if (ePMeth.equals(""))
        {
            Toast.makeText(this, "Write Down Planting Method", Toast.LENGTH_SHORT).show();
        }
        else
            {
                HashMap<String, Object> tuteMap =new HashMap<>();
                tuteMap.put("tid",tutorialID);
                //tuteMap.put("type",ePtype);
                tuteMap.put("name",ePName);
                tuteMap.put("met",ePMeth);
                tuteRef.updateChildren(tuteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                            if (task.isSuccessful()){
                                Toast.makeText(Update_Tutorials.this, "Changes Applied Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(Update_Tutorials.this, TutorialManagerUserInterface.class);
                                startActivity(intent);
                                finish();
                            }
                    }
                });
            }
    }

    private void displaySpecificTuteInfo()
    {
        tuteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                    if (dataSnapshot.exists())
                    {
                       String eName= dataSnapshot.child("name").getValue().toString();
                       String  etype= dataSnapshot.child("type").getValue().toString();
                       String eMeth= dataSnapshot.child("met").getValue().toString();
                       String eImage= dataSnapshot.child("image").getValue().toString();

                        editName.setText(eName);
                        editMethod.setText(eMeth);
                        //edittype.setAdapter(etype);
                        Picasso.get().load(eImage).into(editImage);

                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
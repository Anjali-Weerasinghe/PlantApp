package com.example.planthome.TutorialManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.planthome.R;
import com.example.planthome.TutorialManagement.Model.Tutes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewTuteMethodActivity extends AppCompatActivity
{
    private ImageView methodimage;
    private TextView  methodName,methodDes;
    private  String tutorialID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tute_method);

        tutorialID =getIntent().getStringExtra("tid");
        methodName=(TextView) findViewById(R.id.tuteDescriptionMethodName);
        methodDes=(TextView) findViewById(R.id.tuteMethodDescription_view);
        methodimage=(ImageView)findViewById(R.id.plantTuteMethod_Image);

        getTutesDettails(tutorialID);
    }

    private void getTutesDettails(String tutorialID)
    {
        DatabaseReference tutesRef = FirebaseDatabase.getInstance().getReference().child("Tutes");
        tutesRef.child(tutorialID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Tutes tutes =dataSnapshot.getValue(Tutes.class);
                    methodName.setText(tutes.getName());
                    System.out.println(tutes.getName());
                    methodDes.setText(tutes.getMet());
                    Picasso.get().load(tutes.getImage()).into(methodimage);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
}
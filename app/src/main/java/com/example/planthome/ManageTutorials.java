package com.example.planthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageTutorials extends AppCompatActivity {
    private Button editTutes,RemoveTutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tutorials);

        editTutes=(Button)findViewById(R.id.EditButton);

        editTutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ManageTutorials.this,TutorialManager.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

            }
        });
    }
}
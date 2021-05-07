package com.example.planthome;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textViewPlantName,textViewPlantType,textViewPlantPrice;
    View v;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=itemView.findViewById(R.id.single_plant_img_view);
        textViewPlantName=itemView.findViewById(R.id.text_plant_name);
        textViewPlantType=itemView.findViewById(R.id.text_plant_type);
        textViewPlantPrice=itemView.findViewById(R.id.text_plant_price);
        v=itemView;


    }
}

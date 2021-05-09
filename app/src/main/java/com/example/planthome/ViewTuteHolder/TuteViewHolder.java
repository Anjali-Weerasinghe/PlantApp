package com.example.planthome.ViewTuteHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.Interface.TuteClickListner;
import com.example.planthome.R;

public class TuteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView plntName,plntType;
    public ImageView pntImage;
    public TuteClickListner listner;

    public TuteViewHolder(@NonNull View itemView) {
        super(itemView);
        pntImage =(ImageView) itemView.findViewById(R.id.plantTute_Image);
        plntName =(TextView) itemView.findViewById(R.id.tuteName);
        plntType =(TextView) itemView.findViewById(R.id.tuteType_view);

    }
    public void setTuteClickListner(TuteClickListner listner)
    {
            this.listner=listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition() , false);
    }
}

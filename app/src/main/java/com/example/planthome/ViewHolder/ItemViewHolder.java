package com.example.planthome.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.Interface.ItemClickListener;
import com.example.planthome.R;

import org.w3c.dom.Text;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtItemCategory, txtItemName, txtItemPrice, txtDescription, txtItemCode;
    public ImageView imageView;
    public ItemClickListener listener;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        txtItemCode = (TextView) itemView.findViewById(R.id.item_code);
        imageView = (ImageView) itemView.findViewById(R.id.item_image);
        txtItemCategory = (TextView) itemView.findViewById(R.id.item_category);
        txtItemName = (TextView) itemView.findViewById(R.id.item_name);
        txtItemPrice = (TextView) itemView.findViewById(R.id.item_price);
        txtDescription = (TextView) itemView.findViewById(R.id.item_description1);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);

    }
}

package com.example.planthome.CustomerManagement;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.CustomerManagement.Interface.ItemClickListener;
import com.example.planthome.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private ItemClickListener itemClickListener;
    public TextView price;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

//        cartItem=itemView.findViewById(R.id.cartItem);
//        itemNo=itemView.findViewById(R.id.ItemNo);
//        plantName=itemView.findViewById(R.id.plantName);
        price=itemView.findViewById(R.id.cartItemPrice);
//        deleteItem=itemView.findViewById(R.id.deleteCartItem);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

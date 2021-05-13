package com.example.planthome.CustomerManagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.CustomerManagement.Model.CustomerAddressHelperClass;
import com.example.planthome.CustomerManagement.Model.CustomerCart;
import com.example.planthome.CustomerManagement.Model.Plants;
import com.example.planthome.PlantsManagement.Model.Plant;
import com.example.planthome.PlantsManagement.MyViewHolder;
import com.example.planthome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.myView> {

    ArrayList<Plants> mList;
    Context context;
    private Double Price=0.00;
    DatabaseReference reference;
    String TotalPrice;




    public CartItemAdapter(ArrayList<Plants> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cartitem,parent,false);
        return  new CartItemAdapter.myView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.myView holder, int position) {

        Plants plants=mList.get(position);

        holder.plantName.setText(plants.getPlantName());
        System.out.println(plants.getPlantName());
        holder.price.setText("Rs "+plants.getPlantPrice().toString());
        Picasso.get().load(plants.getImageUrl()).into(holder.cartItem);


        Price=Price+plants.getPlantPrice();
        TotalPrice=Price.toString();
        System.out.println(TotalPrice);
        holder.totalPrice.setText("Total Price:Rs "+TotalPrice);




        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nic = CurrentOnlineCustomer.currentOnlineCustomer.getNic();



                FirebaseDatabase.getInstance().getReference().child("CustomerCart")
                        .child(Nic).child(plants.getPlantKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context.getApplicationContext(), ShoppingCart.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.getApplicationContext().startActivity(intent);


                        } else {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  static  class  myView extends  RecyclerView.ViewHolder{
        ImageView cartItem,deleteItem;
        TextView itemNo,plantName,price,totalPrice;

        public  myView(@NonNull View itemView){
            super(itemView);

            cartItem=itemView.findViewById(R.id.cartItem);
            itemNo=itemView.findViewById(R.id.ItemNo);
            plantName=itemView.findViewById(R.id.plantName);
            price=itemView.findViewById(R.id.cartItemPrice);
            deleteItem=itemView.findViewById(R.id.deleteCartItem);
            totalPrice=itemView.findViewById(R.id.totalPrice);



        }

    }

}

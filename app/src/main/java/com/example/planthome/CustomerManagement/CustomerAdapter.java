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
import com.example.planthome.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.myView> {

    ArrayList<CustomerAddressHelperClass> mList;
    Context context;

    DatabaseReference reference;

    public CustomerAdapter(Context context, ArrayList<CustomerAddressHelperClass> mList){
        this.mList=mList;
        this.context=context;
    }
    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.addressitem,parent,false);
        return  new myView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
                CustomerAddressHelperClass customerAddressHelperClass=mList.get(position);
                holder.no.setText("no "+customerAddressHelperClass.getNo()+",");
                holder.address1.setText(customerAddressHelperClass.getAddress1()+",");
                holder.address2.setText(customerAddressHelperClass.getAddress2()+",");
                holder.city.setText(customerAddressHelperClass.getCity()+",");
                holder.postalcode.setText(customerAddressHelperClass.getPostalCode()+",");
                holder.country.setText(customerAddressHelperClass.getCountry()+".");
                holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();

                        FirebaseDatabase.getInstance().getReference().child("customerAddress")
                        .child(Nic).child(customerAddressHelperClass.getNo()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context,"Delete successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(context.getApplicationContext(), ViewShippingAddress.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.getApplicationContext().startActivity(intent);


                                }else{
                                    Toast.makeText(context, "failed",Toast.LENGTH_SHORT).show();
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
        ImageView deleteAddress;
        TextView no,address1,address2,city,postalcode,country;

        public  myView(@NonNull View itemView){
            super(itemView);

            no=itemView.findViewById(R.id.address_text1);
            address1=itemView.findViewById(R.id.address_text2);
            address2=itemView.findViewById(R.id.address_text3);
            city=itemView.findViewById(R.id.address_text4);
            postalcode=itemView.findViewById(R.id.address_text5);
            country=itemView.findViewById(R.id.address_text6);
            deleteAddress=itemView.findViewById(R.id.deleteAddress);


        }

    }
}

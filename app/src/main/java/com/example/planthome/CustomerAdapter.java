package com.example.planthome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.Model.CustomersAddressHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.myView> {

    ArrayList<CustomersAddressHelperClass> mList;
    Context context;

    DatabaseReference reference;

    public CustomerAdapter(Context context, ArrayList<CustomersAddressHelperClass> mList){
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
                CustomersAddressHelperClass customersAddressHelperClass=mList.get(position);
                holder.no.setText(customersAddressHelperClass.getNo());

                holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("customerAddress")
                        .child("199835710332").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context,"Delete successfully",Toast.LENGTH_SHORT).show();
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
        TextView no;
        public  myView(@NonNull View itemView){
            super(itemView);

            no=itemView.findViewById(R.id.address_text1);
            deleteAddress=itemView.findViewById(R.id.deleteAddress);

        }

    }
}

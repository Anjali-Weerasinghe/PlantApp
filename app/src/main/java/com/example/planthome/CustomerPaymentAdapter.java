package com.example.planthome;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;

import com.example.planthome.Model.CustomerPaymentHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerPaymentAdapter extends RecyclerView.Adapter<CustomerPaymentAdapter.myView> {

    ArrayList<CustomerPaymentHelperClass> mList;
    Context context;
    Drawable drawable;
    DatabaseReference reference;

    public CustomerPaymentAdapter(Context context, ArrayList<CustomerPaymentHelperClass> mList){
        this.mList=mList;
        this.context=context;
    }

    @NonNull
    @Override
    public CustomerPaymentAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.paymentitem,parent,false);
        return  new CustomerPaymentAdapter.myView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerPaymentAdapter.myView holder, int position) {
        CustomerPaymentHelperClass customerPaymentHelperClass = mList.get(position);

        holder.cardNo.setText(customerPaymentHelperClass.getCardNo());
        String type=customerPaymentHelperClass.getType().toString();

        if(type.equals("Visa Card")){
            holder.cardType.setBackgroundResource(R.drawable.visalogo);
        }
        else{
            holder.cardType.setBackgroundResource(R.drawable.mastercardlogo);
        }

        holder.deletePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nic = CurrentOnlineCustomer.currentOnlineCustomer.getNic();

                FirebaseDatabase.getInstance().getReference().child("customerPaymentMethod")
                        .child(Nic).child(customerPaymentHelperClass.getCardNo()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context.getApplicationContext(), ViewPaymentMethod.class);
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
    public  static class myView extends RecyclerView.ViewHolder{
        ImageView deletePayment,cardType;
        TextView cardNo;




        public myView(@NonNull View itemView) {
            super(itemView);

            cardNo=itemView.findViewById(R.id.payment_text1);
            cardType=itemView.findViewById(R.id.cardType);
            deletePayment=itemView.findViewById(R.id.deletePayment);
        }
    }
}

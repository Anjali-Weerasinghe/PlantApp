package com.example.planthome;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.Model.CustomerPaymentHelperClass;

import java.util.ArrayList;

public class ConfirmPaymentAdapter extends RecyclerView.Adapter<ConfirmPaymentAdapter.myView> {

    ArrayList<CustomerPaymentHelperClass> mList;
    Context context;

    public ConfirmPaymentAdapter(ArrayList<CustomerPaymentHelperClass> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }


    @NonNull
    @Override
    public ConfirmPaymentAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.confirmpaymentitem,parent,false);
        return  new ConfirmPaymentAdapter.myView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmPaymentAdapter.myView holder, int position) {
        CustomerPaymentHelperClass customerPaymentHelperClass = mList.get(position);

        holder.cardNo.setText(customerPaymentHelperClass.getCardNo());
        String type=customerPaymentHelperClass.getType().toString();

        if(type.equals("Visa Card")){
            holder.cardType.setBackgroundResource(R.drawable.visalogo);
        }
        else{
            holder.cardType.setBackgroundResource(R.drawable.mastercardlogo);
        }
        holder.select_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(),PlaceOrder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
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
        Button select_payment;


        public myView(@NonNull View itemView) {
            super(itemView);

            cardNo=itemView.findViewById(R.id.confirmPayment_text1);
            cardType=itemView.findViewById(R.id.cardType);
            select_payment=itemView.findViewById(R.id.choosePayment);

        }
    }
}

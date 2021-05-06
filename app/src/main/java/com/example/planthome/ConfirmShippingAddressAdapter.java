package com.example.planthome;

import android.content.Context;
import android.content.Intent;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planthome.CurrentOnlineUser.CurrentOnlineCustomer;
import com.example.planthome.Model.CustomerAddressHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ConfirmShippingAddressAdapter extends RecyclerView.Adapter<ConfirmShippingAddressAdapter.myView>{

    ArrayList<CustomerAddressHelperClass> mList;
    private int checkedPosition=0;
    Context context;
    boolean visible;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public ConfirmShippingAddressAdapter(ArrayList<CustomerAddressHelperClass> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public ConfirmShippingAddressAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.confirmaddressitem,parent,false);
        return  new ConfirmShippingAddressAdapter.myView(v);


    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmShippingAddressAdapter.myView holder, int position) {

        CustomerAddressHelperClass customerAddressHelperClass=mList.get(position);
          System.out.println(mList.get(position));
//          holder.bind(mList.get(position));
        holder.no.setText("no "+customerAddressHelperClass.getNo()+",");
        holder.address1.setText(customerAddressHelperClass.getAddress1()+",");
        holder.address2.setText(customerAddressHelperClass.getAddress2()+",");
        holder.city.setText(customerAddressHelperClass.getCity()+",");
        holder.postalcode.setText(customerAddressHelperClass.getPostalCode()+",");
        holder.country.setText(customerAddressHelperClass.getCountry()+".");

        holder.chooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();

               storeShippingDetails(customerAddressHelperClass.getNo(),customerAddressHelperClass.getAddress1(),customerAddressHelperClass.getAddress2(),
                       customerAddressHelperClass.getCity(),customerAddressHelperClass.getPostalCode(),customerAddressHelperClass.getCountry());
//                  Toast.makeText(context,"Delete successfully",Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(context.getApplicationContext(),PlaceOrder.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.getApplicationContext().startActivity(intent);

            }
        });


    }

  public void storeShippingDetails(String no,String address1,String address2,String city,String postalcode,String country){

      rootNode=FirebaseDatabase.getInstance();
      reference=rootNode.getReference().child("customerOderDetails");
      String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
      CustomerAddressHelperClass helperClass=new CustomerAddressHelperClass(Nic,no,address1,address2,city,postalcode,country);
      reference.child(Nic).child(no).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful()){

                  Toast.makeText(context.getApplicationContext(),"Your Shipping Address Added successfully",Toast.LENGTH_SHORT).show();
                  Intent intent=new Intent(context.getApplicationContext(),PlaceOrder.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  context.getApplicationContext().startActivity(intent);
              }


          }
      });

  }


    @Override
    public int getItemCount() {
        return mList.size();
    }
    class  myView extends  RecyclerView.ViewHolder {
        ImageView selectAddress;
        TextView no, address1, address2, city, postalcode,country;
        Button chooseAddress;

        ViewGroup container;

        public myView(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.confirmAddress_text1);
            address1 = itemView.findViewById(R.id.confirmAddress_text2);
            address2 = itemView.findViewById(R.id.confirmAddress_text3);
            city = itemView.findViewById(R.id.confirmAddress_text4);
            postalcode = itemView.findViewById(R.id.confirmAddress_text5);
            country = itemView.findViewById(R.id.confirmAddress_text6);
            chooseAddress = itemView.findViewById(R.id.chooseAddress);
            selectAddress=itemView.findViewById(R.id.select_shipping_address);
//            container=itemView.findViewById(R.id.layout_container);


//           chooseAddress.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String Nic= CurrentOnlineCustomer.currentOnlineCustomer.getNic();
//
//
////                  Toast.makeText(context,"Delete successfully",Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(context.getApplicationContext(),PlaceOrder.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.getApplicationContext().startActivity(intent);
//
//                }
//            });

        }

        public void bind(final CustomerAddressHelperClass customerAddressHelperClass) {
            if(checkedPosition==-1){

                selectAddress.setVisibility(View.GONE);
            }else{
                if(checkedPosition==getAdapterPosition()){
                    selectAddress.setVisibility(View.VISIBLE);
                }
                else {
                    selectAddress.setVisibility(View.GONE);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectAddress.setVisibility(View.VISIBLE);
                    if(checkedPosition!=getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition=getAdapterPosition();
                    }
                }
            });


        }
    }
    public CustomerAddressHelperClass getSelected(){
        if(checkedPosition !=-1){
            return  mList.get(checkedPosition);
        }
        return null;
    }
}

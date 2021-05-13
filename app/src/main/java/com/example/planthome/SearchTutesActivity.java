package com.example.planthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.planthome.Model.Tutes;
import com.example.planthome.ViewTuteHolder.TuteViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchTutesActivity extends AppCompatActivity {

    private Button searchbtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String searchinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tutes);

        searchbtn=findViewById(R.id.search_button);
        inputText=findViewById(R.id.serchTute);
        searchList=findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchTutesActivity.this));


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchinput = inputText.getText().toString();
                onStart();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Tutes");
        FirebaseRecyclerOptions<Tutes> options=new FirebaseRecyclerOptions.Builder<Tutes>()
                .setQuery(reference.orderByChild("name").startAt(searchinput),Tutes.class).build();

        FirebaseRecyclerAdapter<Tutes, TuteViewHolder> adapter= new FirebaseRecyclerAdapter<Tutes, TuteViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TuteViewHolder holder, int i, @NonNull Tutes model)
            {
                holder.plntType.setText(model.getType());
                holder.plntName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.pntImage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        //edit


                            Intent intent=new Intent(SearchTutesActivity.this, ViewTuteMethodActivity.class);
                            intent.putExtra("tid",model.getTid());
                            startActivity(intent);



                        //Intent intent=new Intent(TutorialManagerUserInterface.this, ViewTuteMethodActivity.class);
                        //intent.putExtra("tid",model.getTid());
                        //startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public TuteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tute_view, parent, false);
                TuteViewHolder holder = new TuteViewHolder(view);
                return holder;
            }
        };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
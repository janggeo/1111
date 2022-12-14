package com.example.tux0;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ingredientAdapter extends RecyclerView.Adapter<ingredientAdapter.Customviewholder> {

    private ArrayList<ingre> arrayList;
    private Context context;
    public String url;
    public ingredientAdapter(ArrayList<ingre> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    private RecyclerView recyclerView;
    private FirebaseUser firebaseUser;
    private RecyclerView.Adapter adpater;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public String id;
    public String uid;
    public String recipe_name;
    public String name;

    @NonNull
    @Override
    public ingredientAdapter.Customviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_main,parent,false);
        ingredientAdapter.Customviewholder holder = new ingredientAdapter.Customviewholder((view));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ingredientAdapter.Customviewholder holder, int position) {
        holder.in_name.setText((arrayList.get(position).getName()));
        holder.in_date.setText((arrayList.get(position).getDate()));
        holder.in_count.setText((arrayList.get(position).getCnt()));

        holder.ingre_delete.setOnClickListener(new View.OnClickListener() { // ?????? ?????? ?????????
            @Override
            public void onClick(View v) {

                //?????? ???????????? ????????? ?????? ????????????
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser != null) {  //????????? ????????? ??? ??????????????? ??????
                    uid = firebaseUser.getUid();
                    int position = holder.getAdapterPosition();
                    //????????? ingre??? arrayList ????????? ??????(?????????)????????????
//                    id = arrayList.get(position).getid();
                    name = arrayList.get(position).getName();
                    Log.v("test", "test" + name);
                    //position, arraylist ??????
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("Users"); //???????????? ????????? ??????
                    databaseReference.child(uid).child("ingre").child(name).removeValue();
                    Toast.makeText(context.getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                    arrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();

                    adpater = new com.example.tux0.ingredientAdapter(arrayList, context);
                    MainActivity.recyclerView.setAdapter(adpater);
                    //??? ????????????........
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    static public class Customviewholder extends RecyclerView.ViewHolder {

        TextView in_date;
        TextView in_name;
        TextView in_count;
        Button ingre_delete;

        public Customviewholder(@NonNull View itemView) {
            super(itemView);

            this.in_count = itemView.findViewById(R.id.in_count);
            this.in_date = itemView.findViewById(R.id.in_date);
            this.in_name = itemView.findViewById((R.id.in_name));
            this.ingre_delete = itemView.findViewById(R.id.ingre_delete);
        }
    }
}

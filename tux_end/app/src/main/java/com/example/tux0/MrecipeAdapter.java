package com.example.tux0;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class MrecipeAdapter extends RecyclerView.Adapter<MrecipeAdapter.Customviewholder> {

        private ArrayList<recipe> arrayList;
        private Context context;
        public String url;
        public MrecipeAdapter(ArrayList<recipe> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }
        private FirebaseDatabase database;
        private DatabaseReference databaseReference;
        public String id;
        private FirebaseUser firebaseUser;
        private String uid;

        @NonNull
        @Override
        public MrecipeAdapter.Customviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dashboard_main,parent,false);
            MrecipeAdapter.Customviewholder holder = new MrecipeAdapter.Customviewholder((view));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MrecipeAdapter.Customviewholder holder, int position) {
            Glide.with(holder.itemView)
                    .load(arrayList.get(position).getimg())
                    .into(holder.iv_img);

            holder.tv_name.setText((arrayList.get(position).getname()));
            holder.tv_url.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int url_position = holder.getAdapterPosition();    //arrayList?????? ?????? url??? ???????????? ??????
                    url = arrayList.get(url_position).geturl();     //?????? ????????? url ??????
                    Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  //?????? url??? ??????
                    urlintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(urlintent);
                }
            });

            holder.favorite.setOnClickListener(new View.OnClickListener() { // ???????????? ?????? ?????????
                @Override
                public void onClick(View v) {
                    //?????? ???????????? ????????? ?????? ????????????
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(firebaseUser != null) {  //????????? ????????? ??? ??????????????? ??????
                        uid = firebaseUser.getUid();
                        //String email = firebaseUser.getEmail();

                        int position = holder.getAdapterPosition();// ??????????????? ????????? ???????????? arrayList ????????? ??????(?????????)????????????
                        id = arrayList.get(position).getid(); //arraylist position???????????? ????????? ???????????? id??????
                        database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference("Users"); //???????????? ????????? ??????
                        databaseReference.child(uid).child("favorite").child(id).child("id").setValue(arrayList.get(position).getid());
                        databaseReference.child(uid).child("favorite").child(id).child("name").setValue(arrayList.get(position).getname());
                        databaseReference.child(uid).child("favorite").child(id).child("summary").setValue(arrayList.get(position).getsummary());
                        databaseReference.child(uid).child("favorite").child(id).child("img").setValue(arrayList.get(position).getimg());
                        databaseReference.child(uid).child("favorite").child(id).child("url").setValue(arrayList.get(position).geturl());
                        Toast.makeText(context.getApplicationContext(), "???????????? ??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            holder.tv_summary.setText((arrayList.get(position).getsummary()));

        }

        @Override
        public int getItemCount() {
            return (arrayList != null ? arrayList.size() : 0);
        }

        static public class Customviewholder extends RecyclerView.ViewHolder {

            TextView iv_id;
            ImageView iv_img;
            TextView tv_name;
            Button tv_url;
            TextView tv_summary;
            Button favorite;

            public Customviewholder(@NonNull View itemView) {
                super(itemView);

                this.iv_id = itemView.findViewById(R.id.iv_id);
                this.iv_img = itemView.findViewById(R.id.iv_img);
                this.tv_name = itemView.findViewById((R.id.tv_name));
                this.tv_url = itemView.findViewById((R.id.tv_url));
                this.tv_summary = itemView.findViewById((R.id.tv_summary));
                this.favorite = itemView.findViewById(R.id.favorite);
            }

        }

    }

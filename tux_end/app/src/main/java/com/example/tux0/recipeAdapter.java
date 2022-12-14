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

public class recipeAdapter extends RecyclerView.Adapter<recipeAdapter.Customviewholder> {

    private ArrayList<recipe> arrayList;
    private Context context;
    public String url;
    public recipeAdapter(ArrayList<recipe> arrayList, Context context) {
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
    public Customviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_main,parent,false);
        Customviewholder holder = new Customviewholder((view));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Customviewholder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getimg())
                .into(holder.iv_img);

        holder.tv_name.setText((arrayList.get(position).getname()));
        holder.tv_url.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int url_position = holder.getAdapterPosition();    //arrayListėė íīëđ urlėī ëĪėīėë ėėđ
                url = arrayList.get(url_position).geturl();     //íīëđ ėėđė url ėŧęļ°
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  //íīëđ urlëĄ ė°ęē°
                urlintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(urlintent);
            }
        });

        //ëēížė íĩíī íėīėīëē ėīėĪ 'favorite' ëļëė ë ėíž ėķę°
        holder.favorite.setOnClickListener(new View.OnClickListener() { // ėĶęēĻė°ūęļ° ëēíž íīëĶ­ė
            @Override
            public void onClick(View v) {
                //íėŽ ëĄę·ļėļë ėŽėĐė ė ëģī ę°ė ļėĪęļ°
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser != null) {  //ėŽėĐė ė ëģīę° ė ę°ė ļėėĄė ęē―ė°
                    uid = firebaseUser.getUid();
                    //String email = firebaseUser.getEmail();

                    int position = holder.getAdapterPosition();// ėĶęēĻė°ūęļ°ė ėķę°í  ë ėížė arrayList ėėė ėėđ(ėļëąėĪ)ę°ė ļėĪęļ°
                    id = arrayList.get(position).getid(); //arraylist positionėļëąėĪė ėėđí ë ėížė idė ėĨ
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("Users"); //ë ėížëĨž ė ėĨí  ėėđ
                    databaseReference.child(uid).child("favorite").child(id).child("id").setValue(arrayList.get(position).getid());
                    databaseReference.child(uid).child("favorite").child(id).child("name").setValue(arrayList.get(position).getname());
                    databaseReference.child(uid).child("favorite").child(id).child("summary").setValue(arrayList.get(position).getsummary());
                    databaseReference.child(uid).child("favorite").child(id).child("img").setValue(arrayList.get(position).getimg());
                    databaseReference.child(uid).child("favorite").child(id).child("url").setValue(arrayList.get(position).geturl());
                    Toast.makeText(context.getApplicationContext(), "ë ėížę° ėĶęēĻė°ūęļ°ė ėķę°ëėėĩëëĪ.", Toast.LENGTH_SHORT).show();

                }
            }
        });
//        holder.tv_url.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.tv_url.toString()));
//
//            }
//        });
        holder.tv_summary.setText((arrayList.get(position).getsummary()));

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    static public class Customviewholder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView tv_name;
        Button tv_url;
        TextView tv_summary;
        Button favorite;

        public Customviewholder(@NonNull View itemView) {
            super(itemView);

            this.iv_img = itemView.findViewById(R.id.iv_img);
            this.tv_name = itemView.findViewById((R.id.tv_name));
            this.tv_url = itemView.findViewById((R.id.tv_url));
            this.tv_summary = itemView.findViewById((R.id.tv_summary));
            this.favorite = itemView.findViewById(R.id.favorite);
        }

    }

}

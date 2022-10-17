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
                int url_position = holder.getAdapterPosition();    //arrayList에서 해당 url이 들어있는 위치
                url = arrayList.get(url_position).geturl();     //해당 위치의 url 얻기
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));  //해당 url로 연결
                urlintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(urlintent);
            }
        });
        //버튼을 통해 파이어베이스 'favorite' 노드에 레시피 추가
        holder.favorite.setOnClickListener(new View.OnClickListener() { // 즐겨찾기 버튼 클릭시
            @Override
            public void onClick(View v) {
                //현재 로그인된 사용자 정보 가져오기
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser != null) {  //사용자 정보가 잘 가져와졌을 경우
                    uid = firebaseUser.getUid();
                    //String email = firebaseUser.getEmail();

                    int position = holder.getAdapterPosition();// 즐겨찾기에 추가할 레시피의 arrayList 에서의 위치(인덱스)가져오기
                    id = arrayList.get(position).getid(); //arraylist position인덱스에 위치한 레시피의 id저장
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("Users"); //레시피를 저장할 위치
                    databaseReference.child(uid).child("favorite").child(id).child("id").setValue(arrayList.get(position).getid());
                    databaseReference.child(uid).child("favorite").child(id).child("name").setValue(arrayList.get(position).getname());
                    databaseReference.child(uid).child("favorite").child(id).child("summary").setValue(arrayList.get(position).getsummary());
                    databaseReference.child(uid).child("favorite").child(id).child("img").setValue(arrayList.get(position).getimg());
                    databaseReference.child(uid).child("favorite").child(id).child("url").setValue(arrayList.get(position).geturl());
                    Toast.makeText(context.getApplicationContext(), "레시피가 즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();

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

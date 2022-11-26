//package com.example.tux0;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseException;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class ingredient extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adpater;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<ingre> arrayList;
//    private FirebaseDatabase database;
//    private DatabaseReference databaseReference;
//    private String uid;
//    private FirebaseUser firebaseUser;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        getSupportActionBar().setTitle("냉장고");
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize((true));
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager((layoutManager));
//        arrayList = new ArrayList<>();
//
//        // getInstance()를 사용하여 데이터베이스의 인스턴스를 검색하고 쓰려는 위치를 참조합니다
//        database = FirebaseDatabase.getInstance();
//        //firebaseAuth 인스턴스 현재 접속된 사용자로 초기화
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (firebaseUser != null) { //접속한 사용자인스턴스가 제대로 불려와졌을 경우
//
//            uid = firebaseUser.getUid(); //uid저장
//
//            //데이터베이스에서 데이터를 읽거나 쓰려면 DatabaseReference 인스턴스가 필요
//            //데이터를 읽을 위치로 Users.uid.favorite(사용자가 즐겨찾기한 레시피가 저장되어있음)를 지정
//            databaseReference = database.getReference("Users").child(uid).child("ingre");
//
//            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @SuppressLint("NotifyDataSetChanged")
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    arrayList.clear();//arrayList 초기화
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //ingre하위 항목들 하나하나 불러오기
//                        try {
//                            ingre ingre = snapshot.getValue(ingre.class);
//                            arrayList.add(ingre);
//                        } catch (DatabaseException e) {
//
//                        }
//                        adpater.notifyDataSetChanged();
//                    }
//                    Log.v("test", "test" + arrayList);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Log.e("MainActivity", String.valueOf(error.toException()));
//                }
//            });
//        }
//        adpater = new com.example.tux0.ingredientAdapter(arrayList, this);
//        recyclerView.setAdapter(adpater);   //리사이클러뷰 호출?
//
//    }
//}

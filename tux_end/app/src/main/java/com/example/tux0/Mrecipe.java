package com.example.tux0;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mrecipe extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adpater;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<recipe> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String search;
    private Button search_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboardmm);

        getSupportActionBar().setTitle("레시피 목록");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize((true));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager((layoutManager));
        arrayList = new ArrayList<>();

        EditText searchbar = (EditText)findViewById(R.id.recipe_search);

        //파이어베이스 realtime database의 데이터 읽을 위치를 "recipe"노드로 지정
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("recipe");

        ShowRecipe();

        search_Btn = findViewById(R.id.searchBtn);
        search_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //검색창 값 가져오기
                search = searchbar.getText().toString();

                ShowRecipe1(search); //showrecipe함수 호출
            }
        });

    }

    private void ShowRecipe() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //arrayList초기화
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //재료명 밑의 자식들을 하나하나 snapshot을 찍어 arrayList에 저장
                        for (DataSnapshot snapshot_2 : snapshot.getChildren()) {
                            recipe recipe = snapshot_2.getValue(recipe.class);
                            arrayList.add(recipe);
                        }
                }
                adpater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });
        adpater = new com.example.tux0.MrecipeAdapter(arrayList, this);
        recyclerView.setAdapter(adpater);
    }

    private void ShowRecipe1(String ingre1) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //arrayList초기화
                arrayList.clear();
                //snapshot을 찍어 입력받은 ingre와 일치하는 name이 있는지 확인
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //재료명 밑의 자식들을 하나하나 snapshot을 찍어 arrayList에 저장
                        for(DataSnapshot snapshot_1 : snapshot.getChildren()) {
                                //recipe 안에 name에서 검색어 포함 확인
                                if (snapshot_1.getValue(recipe.class).getname().contains(ingre1)) {
                                    recipe recipe = snapshot_1.getValue(recipe.class);
                                    arrayList.add(recipe);
                                }
                        }
                }
                adpater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });
        adpater = new com.example.tux0.MrecipeAdapter(arrayList, this);
        recyclerView.setAdapter(adpater);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}

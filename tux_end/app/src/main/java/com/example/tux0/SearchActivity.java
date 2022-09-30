package com.example.tux0;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adpater;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<recipe> arrayList;
    private ArrayList<recipe> arrayList0;
    private ArrayList<recipe> arrayList1;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String search1;
    private String search2;
    private String search3;
    private Button search_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize((true));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager((layoutManager));
        arrayList = new ArrayList<>();
        arrayList0 = new ArrayList<>();
        arrayList1 = new ArrayList<>();

        //EditText 재료 검색바
        EditText searchbar1 = (EditText)findViewById(R.id.searchWord1);
        EditText searchbar2 = (EditText)findViewById(R.id.searchWord2);
        EditText searchbar3 = (EditText)findViewById(R.id.searchWord3);

        //처음에는 다 안보이게 설정
        searchbar1.setVisibility(View.INVISIBLE);
        searchbar2.setVisibility(View.INVISIBLE);
        searchbar3.setVisibility(View.INVISIBLE);

        //파이어베이스 realtime database의 데이터 읽을 위치를 "recipe"노드로 지정
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("recipe");


        Button rb1 = findViewById(R.id.radioButton1);
        Button rb2 = findViewById(R.id.radioButton2);
        Button rb3 = findViewById(R.id.radioButton3);

        //"검색창 1개" 라디오버튼 클릭
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //검색창 1개만 보이도록
                searchbar1.setVisibility(View.VISIBLE);
                searchbar2.setVisibility(View.INVISIBLE);
                searchbar3.setVisibility(View.INVISIBLE);

                //검색 버튼 클릭시
                search_Btn = findViewById(R.id.searchBtn);
                search_Btn.setVisibility(View.VISIBLE);
                search_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //검색창 값 가져오기
                        search1 = searchbar1.getText().toString();

                        ShowRecipe(search1); //showrecipe함수 호출
                    }
                });
            }
        });
        //"검색창 2개" 라디오 버튼 클릭
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbar1.setVisibility(View.VISIBLE);
                searchbar2.setVisibility(View.VISIBLE);
                searchbar3.setVisibility(View.INVISIBLE);

                //검색버튼 클릭시
                search_Btn = findViewById(R.id.searchBtn);
                search_Btn.setVisibility(View.VISIBLE);
                search_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //검색창 값 가져오기
                        search1 = searchbar1.getText().toString();
                        search2 = searchbar2.getText().toString();

                        ShowRecipe(search1, search2); //showrecipe함수 호출

                    }
                });
            }
        });
        //"검색창 3개" 라디오버튼 클릭시
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbar1.setVisibility(View.VISIBLE);
                searchbar2.setVisibility(View.VISIBLE);
                searchbar3.setVisibility(View.VISIBLE);

                //검색버튼 클릭시
                search_Btn = findViewById(R.id.searchBtn);
                search_Btn.setVisibility(View.VISIBLE);
                search_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //검색창 값 가져오기
                        search1 = searchbar1.getText().toString();
                        search2 = searchbar2.getText().toString();
                        search3 = searchbar3.getText().toString();

                        ShowRecipe(search1, search2, search3); //showrecipe함수 호출
                    }
                });
            }
        });
    }


    //ShowRecipe 함수

    //1번 재료가 포함된 레시피 arrayList에 저장, 리사이클러뷰로 출력
    private void ShowRecipe(String ingre1) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //arrayList초기화
                arrayList.clear();
                //snapshot을 찍어 입력받은 ingre와 일치하는 재료명이 있는지 확인
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(ingre1)) {
                        //재료명 밑의 자식들을 하나하나 snapshot을 찍어 arrayList에 저장
                        for (DataSnapshot snapshot_2 : snapshot.getChildren()) {
                            recipe recipe = snapshot_2.getValue(recipe.class);
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
        adpater = new com.example.tux0.recipeAdapter(arrayList, this);
        recyclerView.setAdapter(adpater);
    }

    //1번,2번 재료가 포함된  레시피 arrayList에 저장, 리사이클러뷰로 출력
    private void ShowRecipe(String ingre1, String ingre2) {
//        Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //arrayList 초기화
                arrayList.clear();
                arrayList0.clear();
                //snapshot을 찍어 입력받은 ingre1와 일치하는 재료명이 있는지 확인
//                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(ingre1)) {
                        //재료명 밑의 자식들을 하나하나 snapshot을 찍어 arrayList0에 저장
                        for (DataSnapshot snapshot_2 : snapshot.getChildren()) {
                            recipe recipe1 = snapshot_2.getValue(recipe.class);
                            arrayList0.add(recipe1);
                        }
                    }
                }
//                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                //1번 검색창에 입력된 재료가 포함된 레시피들은 arrayList0에 저장된 상태
                //2번 검색창에 입력된 재료가 포함된 레시피 하나하나를 arrayList0의 레시피들과 비교, 일치 할경우(둘다에 포함된경우) arrayList에 저장
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.getKey().equals(ingre2)) {
                        for (DataSnapshot snapshot_2 : snapshot.getChildren()) {
                            recipe recipe2 = snapshot_2.getValue(recipe.class);
                            for (int i = 0; i < arrayList0.size(); i++) {
                                if (arrayList0.get(i).getid().equals(recipe2.getid())) {
                                    arrayList.add(recipe2);
                                }
                            }
                        }
                    }
                }
                adpater.notifyDataSetChanged();
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });
        adpater = new com.example.tux0.recipeAdapter(arrayList, this);
        recyclerView.setAdapter(adpater);
    }

    private void ShowRecipe(String ingre1, String ingre2, String ingre3) {
        Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList0.clear();
                arrayList1.clear();
                arrayList.clear();
                //snapshot을 찍어 입력받은 ingre와 일치하는 재료명이 있는지 확인
                //Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(ingre1)) {
                        //재료명 밑의 자식들을 하나하나 snapshot을 찍어 arrayList에 저장
                        for (DataSnapshot snapshot_2 : snapshot.getChildren()) {
                            recipe recipe = snapshot_2.getValue(recipe.class);
                            arrayList0.add(recipe);
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.getKey().equals(ingre2)) {
                        for (DataSnapshot snapshot_2 : snapshot.getChildren()) {
                            recipe recipe2 = snapshot_2.getValue(recipe.class);
                            for (int i = 0; i < arrayList0.size(); i++) {
                                if (arrayList0.get(i).getid().equals(recipe2.getid())) {
                                    arrayList1.add(recipe2);
                                }
                            }
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (snapshot.getKey().equals(ingre3)) {
                        for (DataSnapshot snapshot_3 : snapshot.getChildren()) {
                            recipe recipe3 = snapshot_3.getValue(recipe.class);
                            for (int i = 0; i < arrayList1.size(); i++) {
                                if (arrayList1.get(i).getid().equals(recipe3.getid())) {
                                    arrayList.add(recipe3);
                                }
                            }
                        }
                    }
                }
                //Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                adpater.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", String.valueOf(error.toException()));
            }
        });
        adpater = new com.example.tux0.recipeAdapter(arrayList, this);
        recyclerView.setAdapter(adpater);
    }
}


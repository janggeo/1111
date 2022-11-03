package com.example.tux0.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tux0.R;
import com.example.tux0.recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_recipe extends ListFragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adpater;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<recipe> arrayList;
    private ArrayList<recipe> arrayList0;
    private ArrayList<recipe> arrayList1;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private void ShowRecipe() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //arrayList초기화
                arrayList.clear();
                //snapshot을 찍어 입력받은 ingre와 일치하는 재료명이 있는지 확인
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
        adpater = new com.example.tux0.recipeAdapter(arrayList, getActivity());
        recyclerView.setAdapter(adpater);
    }


}

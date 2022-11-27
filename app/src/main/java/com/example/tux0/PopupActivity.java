package com.example.tux0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tux0.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PopupActivity extends Activity {
    private String name;
    private String cnt;
    private String date;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String uid;

    private int count; //저장한 재료 개수 count


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);

        //UI 객체생성
        //데이터 가져오기
//        Intent intent = getIntent();
//        String data = intent.getStringExtra("data");
//        txtText.setText(data);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
//        Intent intent = new Intent(PopupActivity.this,MainActivity.class);
//        intent.putExtra("name",name);
//        intent.putExtra("cnt",cnt);
//        intent.putExtra("date",date);
//        Log.d("PopActivity","success inputdata ");
//        //setResult(RESULT_OK, intent);
//        startActivity(intent);
        AddIngre();
        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
    protected void AddIngre(){
        EditText ingre_name = (EditText) findViewById(R.id.ingre_name);
        EditText ingre_cnt = (EditText) findViewById(R.id.ingre_count);
        EditText ingre_m = (EditText) findViewById(R.id.ingre_m);
        EditText ingre_d = (EditText) findViewById(R.id.ingre_d);

        name = ingre_name.getText().toString();
        cnt = ingre_cnt.getText().toString();
        date = ingre_m.getText().toString()+"/"+ingre_d.getText().toString();
        // getInstance()를 사용하여 데이터베이스의 인스턴스를 검색하고 쓰려는 위치를 참조합니다
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //firebaseAuth 인스턴스 현재 접속된 사용자로 초기화
        if (firebaseUser != null){ //접속한 사용자인스턴스가 제대로 불려와졌을 경우

            uid = firebaseUser.getUid(); //uid저장
            database = FirebaseDatabase.getInstance();

            //데이터베이스에서 데이터를 읽거나 쓰려면 DatabaseReference 인스턴스가 필요
            //데이터를 읽을 위치로 Users.uid.favorite(사용자가 즐겨찾기한 레시피가 저장되어있음)를 지정
            databaseReference = database.getReference("Users").child(uid).child("ingre");
            Log.d("Popup","ingre_name :" + name);
            Log.d("Popup","ingre_cnt :" + cnt);
            Log.d("Popup","ingre_date :" + date);
            databaseReference.child(name).child("name").setValue(name);
            databaseReference.child(name).child("cnt").setValue(cnt);
            databaseReference.child(name).child("date").setValue(date);

        }
    }
}

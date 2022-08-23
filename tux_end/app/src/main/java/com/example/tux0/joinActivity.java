package com.example.tux0;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tux0.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class joinActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText mEmailText, mPasswordText, mPasswordcheckText, mName;
    Button mregisterBtn;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("회원가입");

//        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
//        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        //firebaseAuth 인스턴스 초기화
        firebaseAuth =  FirebaseAuth.getInstance();


        mEmailText = findViewById(R.id.email); //email
        mPasswordText = findViewById(R.id.pw);  //password
        mPasswordcheckText = findViewById(R.id.pwCheck); //password check
        mregisterBtn = findViewById(R.id.button2);  //회원가입 버튼



        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        mregisterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //사용자가 입력한 정보 가져오기
                String email = mEmailText.getText().toString();
                String pwd = mPasswordText.getText().toString();
                String pwdcheck = mPasswordcheckText.getText().toString();

                if(pwd.equals(pwdcheck)) {
                    Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
                    final ProgressDialog mDialog = new ProgressDialog(joinActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();

                    //파이어베이스에 신규계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(joinActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //가입 성공시
                            if (task.isSuccessful()) {
                                mDialog.dismiss();

//
                                FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 접속한 사용자 정보 가져오기
                                String email1 = user.getEmail();    //user에서 email 가져오기
                                String uid = user.getUid();         //user에서 uid 가져오기
//

                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object,String> hashMap = new HashMap<>();

                                hashMap.put("uid",uid);
                                hashMap.put("email", email1);
                                hashMap.put("favorite","");
//

                                FirebaseDatabase database = FirebaseDatabase.getInstance(); //파이어베이스 instance 가져오기
                                //데이터를 쓸 위치로 User항목 지정
                                DatabaseReference reference = database.getReference("Users");
                                //Users.uid 밑에 hashMap(회원정보)을 저장
                                reference.child(uid).setValue(hashMap);


                                //가입이 이루어져을시 가입 화면을 빠져나감. -> 로그인 화면으로
                                Intent intent = new Intent(joinActivity.this, loginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(joinActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {// 가입 실패시
                                mDialog.dismiss();
                                Toast.makeText(joinActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.

                            }
                        }
                    });
                    //비밀번호 오류시
                }else{

                    Toast.makeText(joinActivity.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}
package com.example.tux0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity
{
    private static final String TAG = "loginActivity";
    private FirebaseAuth mAuth;
    private EditText memail;
    private EditText mpasswd;
    public String user_m;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        memail = findViewById(R.id.email);      //email
        mpasswd = findViewById(R.id.pw);        //password
        Button button_log = findViewById(R.id.log); //로그인 버튼
        Button button_join = findViewById(R.id.join); //가입 버튼

        button_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,joinActivity.class);
                startActivity(intent);
            }
        });
        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자가 입력한 정보 가져오기
                String email = memail.getText().toString();
                String password = mpasswd.getText().toString();
                user_m = email;
                Intent intent = new Intent(loginActivity.this, user_em.class);
                intent.putExtra("문자", user_m);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();// 현재 접속한 사용자 정보 가져오기
                                    //로그인 성공시 MainActivity로 전환

                                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(loginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}

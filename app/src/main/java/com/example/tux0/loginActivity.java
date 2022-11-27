package com.example.tux0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
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
    private Context Context_save;
    private CheckBox id_save;
    Animation anim;
    String id_s, pw_s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context_save = this;
        id_save = findViewById(R.id.id_save);
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

        TextView login_text1 = findViewById(R.id.login_text1);
        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        login_text1.startAnimation(anim);

        TextView login_text2 = findViewById(R.id.login_text2);
        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        login_text2.startAnimation(anim);


        boolean ch = Preference.getBoolean(Context_save, "check");
        if(ch){
            memail.setText(Preference.getString(Context_save, "id_s"));
            mpasswd.setText(Preference.getString(Context_save, "pw_s"));
            id_save.setChecked(true);
        }

        id_save.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v){
                if(((CheckBox)v).isChecked()){
                    Preference.setString(Context_save, "id_s", memail.getText().toString());
                    Preference.setString(Context_save, "pw_s", mpasswd.getText().toString());
                    Preference.setBoolean(Context_save, "check", id_save.isChecked());
                }
                else{
                    Preference.setBoolean(Context_save, "check", id_save.isChecked());
                    Preference.clear(Context_save);
                }
            }
        });



        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자가 입력한 정보 가져오기
                String email = memail.getText().toString();
                String password = mpasswd.getText().toString();

                Preference.setString(Context_save, "id_s", memail.getText().toString());
                Preference.setString(Context_save, "pw_s", mpasswd.getText().toString());

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


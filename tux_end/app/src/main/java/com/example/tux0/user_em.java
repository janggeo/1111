package com.example.tux0;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class user_em extends AppCompatActivity {

    public String user_m;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        user_m = intent.getStringExtra("문자");
        TextView textView_he = (TextView) findViewById(R.id.user_name);
        textView_he.setText("i lobe enkas akdsn");

    }
}

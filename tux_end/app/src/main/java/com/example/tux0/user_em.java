package com.example.tux0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class user_em extends AppCompatActivity {

    String user_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        TextView user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText("kkkk");
    }
}

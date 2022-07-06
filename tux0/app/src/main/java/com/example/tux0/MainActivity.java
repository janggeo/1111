package com.example.tux0;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import com.example.tux0.databinding.ActivityMainBinding;
import com.example.tux0.ui.SubActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int count = 0;

    Button button_plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        Button button_camera = (Button)findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(mIntent);
            }
        });

        Button button_manual = (Button)findViewById(R.id.button_manual);
        button_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(Intent);
            }
        });

        Button button_search = (Button)findViewById(R.id.button_search);

        button_plus = (Button) findViewById(R.id.button_plus);
        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2==0) {
                    button_manual.setVisibility(View.VISIBLE);
                    button_search.setVisibility(View.VISIBLE);
                    button_camera.setVisibility(View.VISIBLE);
                    count++;
                }
                else{
                    button_manual.setVisibility(View.INVISIBLE);
                    button_search.setVisibility(View.INVISIBLE);
                    button_camera.setVisibility(View.INVISIBLE);
                    count++;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

}

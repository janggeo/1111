package com.example.tux0;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tux0.databinding.ActivityMainBinding;
import com.example.tux0.ui.opensource_go;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private boolean button_plus_status = false;
    private ActivityMainBinding binding;
    private FloatingActionButton button_plus;
    private FloatingActionButton button_camera;
    private FloatingActionButton button_manual;
    private FloatingActionButton button_search;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseuser;
    private String uid;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private  String email;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /*
        logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        */

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view2);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opensource:
                        Intent Intent = new Intent(getApplicationContext(), opensource_go.class);
                        startActivity(Intent);
                        return true;

                    case R.id.logout:
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        Intent Intent2 = new Intent(getApplicationContext(), loginActivity.class);
                        Intent2.setFlags(Intent2.FLAG_ACTIVITY_NEW_TASK | Intent2.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(Intent2);
                        return true;
                }
                return false;
            }
        });

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseuser != null) {
            email = firebaseuser.getEmail();
        }


        View header = navigationView.getHeaderView(0);
        TextView user_name = (TextView) header.findViewById(R.id.user_name);
        user_name.setText(email);

        /*
        Button button_camera = (Button)findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(mIntent);
            }
        });
        */
        //카메라 기능 이동

        button_plus = findViewById(R.id.floating_button_plus);
        button_camera = findViewById(R.id.floating_button_camera);
        button_manual = findViewById(R.id.floating_button_manual);
        button_search = findViewById(R.id.floating_button_search);

        button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(MainActivity.this, Camara_cv.class);
                startActivity(mintent);
            }
        });
        button_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, manual.class);
                startActivity(intent);
            }
        });
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this ,SearchActivity.class );
                startActivity(intent);
            }
        });

    }

    public void toggleFab() {
        if (button_plus_status) {
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(button_camera, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(button_search, "translationY", 0f);
            fe_animation.start();
            ObjectAnimator fg_animation = ObjectAnimator.ofFloat(button_manual, "translationY", 0f);
            fg_animation.start();
            // button_plus.setImageResource(R.drawable.solid_button);
        } else {
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(button_camera, "translationY", -200f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(button_search, "translationY", -400f);
            fe_animation.start();
            ObjectAnimator fg_animation = ObjectAnimator.ofFloat(button_manual, "translationY", -600f);
            fg_animation.start();
            // button_plus.setImageResource();
        }
        button_plus_status = !button_plus_status;

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

 */
}

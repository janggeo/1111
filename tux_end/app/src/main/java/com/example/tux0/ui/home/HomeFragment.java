package com.example.tux0.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tux0.R;
import com.example.tux0.databinding.FragmentHomeBinding;
import com.example.tux0.ui.opensource_go;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int counting = 0;

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



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding. textDashboard;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        if(counting!=0){
            drawerLayout = getActivity().findViewById(R.id.drawer_layout);
            navigationView = getActivity().findViewById(R.id.nav_view2);
            drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.open, R.string.close);
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
            if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.opensource:
                            Intent Intent = new Intent( getActivity().getApplicationContext(), opensource_go.class);
                            startActivity(Intent);
                    }
                    return false;
                }
            });
        }

        counting++;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
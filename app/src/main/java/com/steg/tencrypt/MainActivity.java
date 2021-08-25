package com.steg.tencrypt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.steg.tencrypt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController nav =
                ((NavHostFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host))
                        .getNavController();

        AppBarConfiguration configuration =
                new AppBarConfiguration.Builder(nav.getGraph()).build();

        NavigationUI.setupWithNavController(null, nav,configuration);
    }
}
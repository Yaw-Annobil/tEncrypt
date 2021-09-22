package com.steg.tencrypt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.steg.tencrypt.utilities.EncryptsMotor;

public class MainActivity extends AppCompatActivity {

//    ActivityMainBinding binding;
    EncryptsMotor motor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));
        Intent intent = getIntent();

        NavController nav =
                ((NavHostFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host))
                        .getNavController();



        AppBarConfiguration configuration =
                new AppBarConfiguration.Builder(nav.getGraph()).build();
        NavigationUI.setupWithNavController(findViewById(R.id.toolbar),nav,configuration);
    }

    private void saveCrypt(Intent intent) {
        if(Intent.ACTION_SEND.equals(intent.getAction())){
            Uri uri = getIntent().getData();

            if(uri == null){
                uri = getIntent().getData();
            }

            if(uri !=null){
            }
        }

    }




}
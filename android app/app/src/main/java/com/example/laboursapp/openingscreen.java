package com.example.laboursapp;

import static com.example.laboursapp.databinding.ActivityOpeningscreenBinding.inflate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.laboursapp.databinding.ActivityLabourDashBoardBinding;
import com.example.laboursapp.databinding.ActivityOpeningscreenBinding;

public class openingscreen extends AppCompatActivity {
    ActivityOpeningscreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpeningscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        },3000);
    }
}
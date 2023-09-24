package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.laboursapp.databinding.ActivityLabourDashBoardBinding;

public class LabourDashBoardActivity extends AppCompatActivity {
    ActivityLabourDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLabourDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LabourProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.searchWorkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LabourSearchWorkActivity.class);
                startActivity(intent);
            }
        });

        binding.viewapplied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LabourAppliedJobs.class);
                startActivity(intent);
            }
        });

        binding.alreadyDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
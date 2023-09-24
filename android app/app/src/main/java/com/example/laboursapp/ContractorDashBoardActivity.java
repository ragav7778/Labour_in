package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.laboursapp.databinding.ActivityContractorDashBoardBinding;

public class ContractorDashBoardActivity extends AppCompatActivity {
    ActivityContractorDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContractorDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContractorProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.addWorkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContractorAddWorkActivity.class);
                startActivity(intent);
            }
        });

        binding.seeWorkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContractorSeeWorkActivity.class);
                startActivity(intent);
            }
        });

        binding.seeRequestsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContractorSeeRequestsActivity.class);
                startActivity(intent);
            }
        });
    }
}
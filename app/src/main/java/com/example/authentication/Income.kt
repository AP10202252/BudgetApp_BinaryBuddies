package com.example.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.authentication.databinding.ActivityIncomeBinding // Adjust based on your setup

class Income : AppCompatActivity() {

    private lateinit var binding: ActivityIncomeBinding // For View Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize View Binding
        binding = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for Income button
        binding.categoriesTab.setOnClickListener {
            val intent = Intent(this, categories::class.java)
            startActivity(intent)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Set click listener for Expenses button
        binding.expensesTab.setOnClickListener {

            val intent = Intent(this, expenses::class.java)
            startActivity(intent)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
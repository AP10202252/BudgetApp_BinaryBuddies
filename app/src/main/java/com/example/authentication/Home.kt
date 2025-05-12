package com.example.authentication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.authentication.R.id


class Home : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val incom: Button = findViewById(R.id.incomeTab)
        incom.setOnClickListener {
            val intent = Intent(this, Income::class.java)
            startActivity(intent)
        }

        val catego: Button = findViewById(R.id.categoriesTab)
        catego.setOnClickListener {
            val intent = Intent(this, expenses::class.java)
            startActivity(intent)
        }
    }
    }

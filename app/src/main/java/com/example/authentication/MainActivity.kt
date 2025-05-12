package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val CancelS: Button = findViewById(R.id.CancelS)
        CancelS.setOnClickListener {
            val intent = Intent(this, First::class.java)
            startActivity(intent)
        }
        val btnCon: Button = findViewById(R.id.btnCon)
        btnCon.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }
}
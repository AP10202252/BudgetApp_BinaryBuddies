
package com.example.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameField = findViewById<EditText>(R.id.editTextText)
        val passwordField = findViewById<EditText>(R.id.Password1)
        val confirmPasswordField = findViewById<EditText>(R.id.Password3)
        val btnContinue = findViewById<Button>(R.id.btnCon)
        val btnCancel = findViewById<Button>(R.id.CancelS)

        btnCancel.setOnClickListener {
            startActivity(Intent(this, First::class.java))
        }

        btnContinue.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // You can add your database or Firebase logic here
            Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Home::class.java))
        }
    }
}
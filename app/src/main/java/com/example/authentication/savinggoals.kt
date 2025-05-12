package com.example.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authentication.databinding.SavingGoalsBinding

class savinggoals : AppCompatActivity() {

    private lateinit var binding: SavingGoalsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SavingGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Income button click listener
        binding.incomeTab.setOnClickListener {
            startActivity(Intent(this, Income::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Categories button click listener
        binding.categoriesTab.setOnClickListener {
            startActivity(Intent(this, categories::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Expenses button click listener
        binding.expensesTab.setOnClickListener {
            startActivity(Intent(this, expenses::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Saving Goals button click listener (already on SavingGoalsActivity)
        binding.savingGoalsTab.setOnClickListener {
            // No action needed
        }

        // Add/Edit Goals FAB click listener
        binding.addGoalsFab.setOnClickListener {
            // Show the form (it's always visible, so FAB can trigger validation/submission)
            submitGoals()
        }

        // Submit button click listener (alternative to FAB)
        binding.submitGoalsButton.setOnClickListener {
            submitGoals()
        }
    }

    private fun submitGoals() {
        val minGoalText = binding.minSavingsEditText.text.toString()
        val maxGoalText = binding.maxSavingsEditText.text.toString()

        // Validate inputs
        if (minGoalText.isEmpty() || maxGoalText.isEmpty()) {
            Toast.makeText(this, "Please enter both minimum and maximum goals", Toast.LENGTH_SHORT).show()
            return
        }

        val minGoal = minGoalText.toDoubleOrNull()
        val maxGoal = maxGoalText.toDoubleOrNull()

        if (minGoal == null || maxGoal == null) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        if (minGoal < 0 || maxGoal < 0) {
            Toast.makeText(this, "Goals cannot be negative", Toast.LENGTH_SHORT).show()
            return
        }

        if (maxGoal < minGoal) {
            Toast.makeText(this, "Maximum goal must be at least the minimum goal", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Save goals (e.g., to Room database or SharedPreferences)
        // Example: Update UI with saved goals
        binding.currentMinGoalText.text = "R $minGoal"
        binding.currentMaxGoalText.text = "R $maxGoal"

        Toast.makeText(this, "Savings goals saved: Min R $minGoal, Max R $maxGoal", Toast.LENGTH_SHORT).show()
    }
}

package com.example.authentication

import android.content.Intent
import android.util.Logg
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.authentication.databinding.ExpensesBinding

class expenses : AppCompatActivity() {

    private lateinit var binding: ExpensesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to IncomeActivity when Income tab is clicked
        binding.incomeTab.setOnClickListener {
            startActivity(Intent(this, Income::class.java))
        }

        // Navigate to CategoriesActivity when Categories tab is clicked
        binding.categoriesTab.setOnClickListener {
            startActivity(Intent(this, categories::class.java))
        }
        binding.savingGoalsTab.setOnClickListener {
            Log.d("ExpensesActivity", "Saving Goals tab clicked")
            startActivity(Intent(this, savinggoals::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Navigate to AddExpenseActivity when Add Expense FAB is clicked
        binding.addExpenseFab.setOnClickListener {
            startActivity(Intent(this, addexpenses::class.java))
        }
    }
}
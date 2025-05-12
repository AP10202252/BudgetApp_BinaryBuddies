package com.example.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.authentication.databinding.CategoriesBinding

class categories : AppCompatActivity() {

    private lateinit var binding: CategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to IncomeActivity when Income tab is clicked
        binding.incomeTab.setOnClickListener {
            startActivity(Intent(this, Income::class.java))
        }

        // Navigate to ExpensesActivity when Expenses tab is clicked
        binding.Expensesbtn.setOnClickListener {
            startActivity(Intent(this, expenses::class.java))
        }

        // Navigate to AddCategoryActivity when Add Category FAB is clicked
        binding.addCategoryFab.setOnClickListener {
            startActivity(Intent(this, addcategory::class.java))
        }

        // Navigate to AddExpenseActivity when Add Expense FAB is clicked
        binding.addExpenseFab.setOnClickListener {
            startActivity(Intent(this, addexpenses::class.java))
        }
    }
}

package com.example.authentication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authentication.databinding.AddCategoryBinding

class addcategory : AppCompatActivity() {

    private lateinit var binding: AddCategoryBinding
    private val existingCategories = listOf("Food", "Transport", "School", "Entertainment", "Utilities", "Health", "Travel", "Clothing")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Populate the existing categories dropdown (for reference)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, existingCategories)
        binding.existingCategoriesDropdown.setAdapter(adapter)

        // Save button click listener
        binding.saveCategoryButton.setOnClickListener {
            val newCategory = binding.categoryNameInput.text.toString().trim()
            if (newCategory.isEmpty()) {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            } else if (existingCategories.contains(newCategory)) {
                Toast.makeText(this, "Category '$newCategory' already exists", Toast.LENGTH_SHORT).show()
            } else {
                // For now, just show a success message and close the activity
                // In a real app, you'd save the category to a database
                Toast.makeText(this, "Category '$newCategory' added", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Cancel button click listener
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }
}
package com.example.authentication

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.InputStream

class addexpenses : AppCompatActivity() {

    private lateinit var categoryDropdown: AutoCompleteTextView
    private lateinit var amountInput: TextInputEditText
    private lateinit var descriptionInput: TextInputEditText
    private lateinit var photoPreview: ImageView
    private lateinit var uploadPhotoButton: Button
    private lateinit var saveExpenseButton: Button
    private lateinit var cancelButton: Button

    private var selectedImageUri: Uri? = null

    // Image Picker Launcher
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data
                selectedImageUri?.let {
                    val inputStream: InputStream? = contentResolver.openInputStream(it)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    photoPreview.setImageBitmap(bitmap)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_expenses)

        // Initialize Views
        categoryDropdown = findViewById(R.id.categoryDropdown)
        amountInput = findViewById(R.id.amountInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        photoPreview = findViewById(R.id.photoPreview)
        uploadPhotoButton = findViewById(R.id.uploadPhotoButton)
        saveExpenseButton = findViewById(R.id.saveExpenseButton)
        cancelButton = findViewById(R.id.cancelButton)

        // Setup Categories
        val categories = arrayOf("Food", "Transport", "Entertainment", "Utilities", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        categoryDropdown.setAdapter(adapter)

        // Upload Photo Button Click
        uploadPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }

        // Save Button Click
        saveExpenseButton.setOnClickListener {
            val category = categoryDropdown.text.toString()
            val amount = amountInput.text.toString()
            val description = descriptionInput.text.toString()

            if (category.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Category and Amount are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Save to Room DB or pass to next activity
            Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Cancel Button Click
        cancelButton.setOnClickListener {
            finish()
        }
    }
}

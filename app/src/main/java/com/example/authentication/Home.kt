package com.example.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.button.MaterialButton
import androidx.appcompat.widget.PopupMenu
import android.view.MenuItem
import com.example.authentication.databinding.ActivityIncomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Home : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateText: TextView
    private lateinit var balanceText: TextView
    private lateinit var incomeText: TextView
    private lateinit var expensesText: TextView
    private lateinit var spendingChart: LineChart
    private lateinit var menuButton: MaterialButton
    private lateinit var incomeTab: MaterialButton
    private lateinit var categoriesTab: MaterialButton
    private lateinit var lookNowBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize views
        calendarView = findViewById(R.id.calendarView)
        selectedDateText = findViewById(R.id.selectedDateText)
        balanceText = findViewById(R.id.balanceText)
        incomeText = findViewById(R.id.incomeText)
        expensesText = findViewById(R.id.expensesText)
        spendingChart = findViewById(R.id.spendingChart)
        menuButton = findViewById(R.id.menuButton)
        incomeTab = findViewById(R.id.incomeTab)
        categoriesTab = findViewById(R.id.categoriesTab)
        lookNowBtn = findViewById(R.id.lookNowBtn)

        // Setup drop-down menu
        setupDropDownMenu()

        // Setup calendar
        setupCalendar()

        // Setup line chart with mock data
        setupLineChart()

        // Setup bottom navigation buttons
        setupBottomNavigation()

        // Setup Look Now button
        lookNowBtn.setOnClickListener {
            Toast.makeText(this, "Look Now clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDropDownMenu() {
        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.home_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.menu_calculate -> {
                        startActivity(Intent(this, calculate::class.java))
                        true
                    }
                    R.id.menu_data_export -> {
                        startActivity(Intent(this, DataExport::class.java))
                        true
                    }
                    R.id.menu_auto_save -> {
                        startActivity(Intent(this, Autos::class.java))
                        true
                    }
                    R.id.menu_settings -> {
                        startActivity(Intent(this, Settings::class.java))
                        true
                    }
                    R.id.menu_income -> {
                        startActivity(Intent(this, Income::class.java))
                        true
                    }
                    R.id.menu_expense -> {
                        startActivity(Intent(this, expenses::class.java))
                        true
                    }
                    R.id.menu_categories -> {
                        startActivity(Intent(this, categories::class.java))
                        true
                    }
                    R.id.menu_sounds -> {
                        startActivity(Intent(this, Sounds::class.java))
                        true
                    }
                    R.id.menu_saving_goals -> {
                        startActivity(Intent(this, savinggoals::class.java))
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                .format(Date(year - 1900, month, dayOfMonth))
            selectedDateText.text = "Selected: $selectedDate"
            Toast.makeText(this, "Selected date: $selectedDate", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupLineChart() {
        val entries = listOf(
            Entry(1f, 1200f), // Day 1
            Entry(2f, 800f),  // Day 2
            Entry(3f, 1500f), // Day 3
            Entry(4f, 600f),  // Day 4
            Entry(5f, 900f)   // Day 5
        )
        val dataSet = LineDataSet(entries, "Spending").apply {
            color = ContextCompat.getColor(this@Home, R.color.red)
            valueTextColor = Color.WHITE
            valueTextSize = 10f
            setDrawCircles(true)
            setCircleColor(ContextCompat.getColor(this@Home, R.color.green))
            setDrawValues(true)
        }
        spendingChart.data = LineData(dataSet)
        spendingChart.description.isEnabled = false
        spendingChart.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        spendingChart.invalidate()
    }

    private fun setupBottomNavigation() {
        incomeTab.setOnClickListener {
            startActivity(Intent(this, ActivityIncomeBinding::class.java))
        }
        categoriesTab.setOnClickListener {
            startActivity(Intent(this, expenses::class.java))
        }
    }
}
package com.example.authentication

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class expenses : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var timePeriodSpinner: Spinner
    private lateinit var spendingGoalsProgress: CircularProgressIndicator
    private lateinit var spendingGoalsStatus: TextView
    private lateinit var badgesContainer: LinearLayout

    // Hardcoded min/max goals for the prototype
    private val minGoal = 1000.0
    private val maxGoal = 5000.0

    // Mock expense data (based on XML CardViews)
    private val mockExpenses = listOf(
        Expense("Groceries", 800.0, "Food", getDateMillis(2025, 4, 11)), // May 11, 2025
        Expense("Bus Fare", 50.0, "Transport", getDateMillis(2025, 4, 11)),
        Expense("Textbooks", 1200.0, "School", getDateMillis(2025, 4, 10)),
        Expense("Movie Tickets", 300.0, "Entertainment", getDateMillis(2025, 4, 10)),
        Expense("Electricity Bill", 600.0, "Utilities", getDateMillis(2025, 4, 9)),
        Expense("Gym Membership", 450.0, "Health", getDateMillis(2025, 4, 8))
    )

    // Badge data model
    data class Badge(val id: Int, val name: String, val iconResId: Int, var earned: Boolean)

    // Mock badges
    private val badges = mutableListOf(
        Badge(1, "Budget Master", R.drawable.ic_badge_budget, false),
        Badge(2, "Consistent Logger", R.drawable.ic_badge_logger, false)
    )

    // Expense data class
    data class Expense(
        val description: String,
        val amount: Double,
        val category: String,
        val date: Long // Timestamp in milliseconds
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expenses)

        // Initialize views
        pieChart = findViewById(R.id.expensesPieChart)
        timePeriodSpinner = findViewById(R.id.timePeriodSpinner)
        spendingGoalsProgress = findViewById(R.id.spendingGoalsProgress)
        spendingGoalsStatus = findViewById(R.id.spendingGoalsStatus)
        badgesContainer = findViewById(R.id.badgesContainer)

        // Setup FAB click listener (for adding expenses, placeholder for prototype)
        findViewById<FloatingActionButton>(R.id.addExpenseFab).setOnClickListener {
            // For prototype, just refresh UI (in a real app, add expense logic here)
            updatePieChart(timePeriodSpinner.selectedItem?.toString() ?: "Last Month")
            updateSpendingGoals()
            updateBadges()
        }

        setupPieChart()
        setupSpinner()
        updateSpendingGoals()
        updateBadges()
    }

    private fun setupPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = true
        pieChart.setExtraOffsets(5f, 10f, 5f, 15f)
        pieChart.dragDecelerationFrictionCoef = 0.95f

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setTransparentCircleAlpha(110)

        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        val legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.textColor = Color.WHITE
        legend.textSize = 12f
    }

    private fun setupSpinner() {
        timePeriodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedPeriod = parent.getItemAtPosition(position).toString()
                if (selectedPeriod == "Custom") {
                    showCustomDateRangePicker()
                } else {
                    updatePieChart(selectedPeriod)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updatePieChart(period: String) {
        val (startDate, endDate) = getDateRange(period)
        val filteredExpenses = mockExpenses.filter { it.date in startDate..endDate }
        val categoryTotals = filteredExpenses.groupBy { it.category }
            .map { (category, expenses) -> CategoryTotal(category, expenses.sumOf { it.amount }) }

        val entries = categoryTotals.map { PieEntry(it.total.toFloat(), it.category) }
        val dataSet = PieDataSet(entries, "Expense Categories").apply {
            colors = listOf(
                Color.parseColor("#FF5722"), // Food
                Color.parseColor("#03A9F4"), // Transport
                Color.parseColor("#4CAF50"), // School
                Color.parseColor("#9C27B0"), // Entertainment
                Color.parseColor("#FFC107"), // Utilities
                Color.parseColor("#00BCD4")  // Health
            )
        }

        val data = PieData(dataSet).apply {
            setDrawValues(true)
            setValueTextSize(12f)
            setValueTextColor(Color.WHITE)
        }

        pieChart.data = data
        val totalSpent = categoryTotals.sumOf { it.total }
        pieChart.description.text = "Min: R $minGoal, Max: R $maxGoal, Spent: R $totalSpent"
        pieChart.invalidate()
    }

    private fun showCustomDateRangePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            val startDate = Calendar.getInstance().apply { set(year, month, day) }.timeInMillis
            DatePickerDialog(this, { _, year2, month2, day2 ->
                val endDate = Calendar.getInstance().apply { set(year2, month2, day2) }.timeInMillis
                updatePieChartWithCustomRange(startDate, endDate)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updatePieChartWithCustomRange(startDate: Long, endDate: Long) {
        val filteredExpenses = mockExpenses.filter { it.date in startDate..endDate }
        val categoryTotals = filteredExpenses.groupBy { it.category }
            .map { (category, expenses) -> CategoryTotal(category, expenses.sumOf { it.amount }) }

        val entries = categoryTotals.map { PieEntry(it.total.toFloat(), it.category) }
        val dataSet = PieDataSet(entries, "Expense Categories").apply {
            colors = listOf(
                Color.parseColor("#FF5722"), // Food
                Color.parseColor("#03A9F4"), // Transport
                Color.parseColor("#4CAF50"), // School
                Color.parseColor("#9C27B0"), // Entertainment
                Color.parseColor("#FFC107"), // Utilities
                Color.parseColor("#00BCD4")  // Health
            )
        }

        val data = PieData(dataSet).apply {
            setDrawValues(true)
            setValueTextSize(12f)
            setValueTextColor(Color.WHITE)
        }

        pieChart.data = data
        val totalSpent = categoryTotals.sumOf { it.total }
        pieChart.description.text = "Min: R $minGoal, Max: R $maxGoal, Spent: R $totalSpent"
        pieChart.invalidate()
    }

    private fun getDateRange(period: String): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        val endDate = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, when (period) {
            "Last Week" -> -7
            "Last Month" -> -30
            "Last 3 Months" -> -90
            else -> -30 // Default to last month
        })
        val startDate = calendar.timeInMillis
        return Pair(startDate, endDate)
    }

    private fun updateSpendingGoals() {
        val calendar = Calendar.getInstance()
        val endDate = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val startDate = calendar.timeInMillis

        val totalSpent = mockExpenses.filter { it.date in startDate..endDate }.sumOf { it.amount }

        val progress = when {
            totalSpent < minGoal -> 0
            totalSpent > maxGoal -> 100
            else -> ((totalSpent - minGoal) / (maxGoal - minGoal) * 100).toInt()
        }
        spendingGoalsProgress.progress = progress

        spendingGoalsStatus.text = when {
            totalSpent < minGoal -> "Below Minimum Goal"
            totalSpent > maxGoal -> "Over Maximum Goal"
            else -> "Within Goals!"
        }
        spendingGoalsStatus.setTextColor(ContextCompat.getColor(this, when {
            totalSpent < minGoal -> R.color.red
            totalSpent > maxGoal -> R.color.red
            else -> R.color.green
        }))
    }

    private fun updateBadges() {
        val calendar = Calendar.getInstance()
        val endDate = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val startDate = calendar.timeInMillis

        // Budget Master: Total spending within min/max goals for the past month
        val totalSpent = mockExpenses.filter { it.date in startDate..endDate }.sumOf { it.amount }
        val isWithinBudget = totalSpent in minGoal..maxGoal

        // Consistent Logger: At least 7 expenses logged in the past week
        calendar.add(Calendar.DAY_OF_YEAR, -7 + 30)
        val weekStart = calendar.timeInMillis
        val dailyLogs = mockExpenses.filter { it.date in weekStart..endDate }
            .groupBy { SimpleDateFormat("yyyy-MM-dd").format(Date(it.date)) }
        val isConsistentLogger = dailyLogs.size >= 4 // Lowered to 4 for mock data (since we have 6 expenses)

        // Update badge status
        badges[0] = badges[0].copy(earned = isWithinBudget)
        badges[1] = badges[1].copy(earned = isConsistentLogger)

        // Update UI
        badgesContainer.removeAllViews()
        badges.forEach { badge ->
            if (badge.earned) {
                val badgeView = layoutInflater.inflate(R.layout.badge_item, badgesContainer, false)
                badgeView.findViewById<ImageView>(R.id.badgeIcon).setImageResource(badge.iconResId)
                badgeView.findViewById<TextView>(R.id.badgeName).text = badge.name
                badgesContainer.addView(badgeView)
            }
        }
    }

    // Helper to create mock dates
    private fun getDateMillis(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.timeInMillis
    }

    // Data class for category totals
    data class CategoryTotal(val category: String, val total: Double)
}
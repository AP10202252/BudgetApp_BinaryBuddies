package com.example.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.authentication.databinding.ActivityIncomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class Income : AppCompatActivity() {

    private lateinit var binding: ActivityIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animate income cards sequentially
        val incomeCards = listOf(
            binding.incomeEntry1,
            binding.incomeEntry2,
            binding.incomeEntry3,
            binding.incomeEntry4,
            binding.incomeEntry5,
            binding.incomeEntry6,
            binding.incomeEntry7,
            binding.incomeEntry8
        )

        incomeCards.forEachIndexed { index, card ->
            val anim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left).apply {
                startOffset = (index * 150).toLong()
                duration = 400
            }
            card.startAnimation(anim)
        }

        // Setup the line chart inside the CardView with id incomeTrendCard
        val lineChart = LineChart(this)
        binding.incomeTrendCard.removeAllViews()
        binding.incomeTrendCard.addView(lineChart)

        setupLineChart(lineChart)

        // Navigation buttons
        binding.categoriesTab.setOnClickListener {
            startActivity(Intent(this, categories::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.expensesTab.setOnClickListener {
            startActivity(Intent(this, expenses::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun setupLineChart(chart: LineChart) {
        // Sample data points matching your incomes
        val entries = listOf(
            Entry(1f, 18500f), // incomeEntry1 (R18500)
            Entry(2f, 4200f),  // incomeEntry2 (R4200)
            Entry(3f, 3100f),  // incomeEntry3 (R3100)
            Entry(4f, 7500f),  // incomeEntry4 (R7500)
            Entry(5f, 2800f),  // incomeEntry5 (R2800)
            Entry(6f, 1200f),  // incomeEntry6 (R1200)
            Entry(7f, 3900f),  // incomeEntry7 (R3900)
            Entry(8f, 1500f)   // incomeEntry8 (R1500)
        )

        val dataSet = LineDataSet(entries, "Income Trend").apply {
            color = Color.CYAN
            setCircleColor(Color.WHITE)
            lineWidth = 2f
            circleRadius = 5f
            setDrawCircleHole(true)
            valueTextSize = 10f
            valueTextColor = Color.WHITE
            setDrawFilled(true)
            fillColor = Color.CYAN
            fillAlpha = 50
        }

        chart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.textColor = Color.WHITE
            xAxis.granularity = 1f
            xAxis.labelCount = entries.size

            axisLeft.textColor = Color.WHITE
            axisLeft.setDrawGridLines(false)

            setTouchEnabled(false)
            animateX(1000) // Animate chart drawing horizontally
            invalidate()
        }
    }
}

package com.sophia.saveandshowmyactivitytoday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import com.sophia.saveandshowmyactivitytoday.databinding.ActivitySettingGoalsBinding
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SettingGoalsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingGoalsBinding
    private lateinit var preferences: PreferenceManager
    private var dateClick: Boolean = true
    private lateinit var date: String

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private val now = Calendar.getInstance()
    private val time = System.currentTimeMillis()
    private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.KOREAN)

    private val yearFormat = now.get(Calendar.YEAR)
    private val monthFormat = now.get(Calendar.MONTH)
    private val dayFormat = now.get(Calendar.DAY_OF_MONTH)

    private val minValue = 1
    private val maxValue = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        year = yearFormat
        month = monthFormat
        day = dayFormat

        init()
        setOnClick()
    }

    private fun init() {
        preferences = PreferenceManager(applicationContext)
    }

    private fun setOnClick() {
        binding.saveBtn.setOnClickListener {
            val mygoal = binding.goalEt.text.toString()
            if (mygoal.isNotEmpty()) {
                preferences.putString("mygoal", mygoal)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.linear.setOnClickListener {
            getCalendarDate()
        }
    }

    private fun getCalendarDate() {
        if (dateClick) {
            binding.fragmentView.visibility = View.VISIBLE
            binding.datePickerActions.visibility = View.VISIBLE
            binding.view1.visibility = View.VISIBLE
            try {
                val datePicker = DatePicker.OnDateChangedListener { _, year, month, day ->

                    this.year = year
                    this.month = month + 1
                    this.day = day
                    date = "${this.year}${this.month}${this.day}"
                    dDay(date)

                }
                binding.datePickerActions.init(this.year, this.month, this.day, datePicker)
                binding.fragmentView.animate().setDuration(200).rotation(0f)
                dateClick = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            binding.fragmentView.visibility = View.GONE
            binding.datePickerActions.visibility = View.GONE
            binding.view1.visibility = View.GONE
            dateClick = true
        }
    }

    private fun dDay(date: String) {
        val startDate = dateFormat.parse("20211202").time
        val endDate = dateFormat.parse(date).time
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time

        val d_day = "${(today - endDate) / (60 * 60 * 24 * 1000)}"


//        Log.d("두 날짜간의 차이(일)", "${(endDate - startDate) / (24 * 60 * 60 * 1000)}")
//        Log.d("시작일 부터 경과 일", "${(today - startDate) / (24 * 60 * 60 * 1000)}")
//        Log.d("D_day", "${(today - endDate) / (60 * 60 * 24 * 1000)}")

    }
}
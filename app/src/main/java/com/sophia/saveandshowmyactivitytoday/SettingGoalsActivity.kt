package com.sophia.saveandshowmyactivitytoday

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.sophia.saveandshowmyactivitytoday.databinding.ActivitySettingGoalsBinding
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import java.text.SimpleDateFormat
import java.util.*

class SettingGoalsActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySettingGoalsBinding
    private lateinit var preferences: PreferenceManager
    private var dateClick: Boolean = true
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private val now = Calendar.getInstance()
//    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)

    private val yearFormat = now.get(Calendar.YEAR)
    private val monthFormat = now.get(Calendar.MONTH)
    private val dayFormat = now.get(Calendar.DAY_OF_MONTH)

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
            val mygoal =  binding.goalEt.text.toString()
            if (mygoal.isNotEmpty()) {
                preferences.putString("mygoal",mygoal)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.linear.setOnClickListener {
            if (dateClick) {
                binding.datePickerActions.visibility = View.VISIBLE
                val datePicker = DatePicker.OnDateChangedListener { _, i, i2, i3 ->
                    year = i
                    month = i2
                    day = i3
                }
                binding.datePickerActions.init(year,month,day,datePicker)
                dateClick = false
            } else {
                binding.datePickerActions.visibility = View.GONE
                dateClick = true
            }
        }
    }
}
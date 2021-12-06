package com.sophia.saveandshowmyactivitytoday

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sophia.saveandshowmyactivitytoday.adapter.CheckAdapter
import com.sophia.saveandshowmyactivitytoday.adapter.DetailPlanAdapter
import com.sophia.saveandshowmyactivitytoday.adapter.TodoAdapter
import com.sophia.saveandshowmyactivitytoday.database.TodoDatabase
import com.sophia.saveandshowmyactivitytoday.database.getTodoDatabase
import com.sophia.saveandshowmyactivitytoday.databinding.ActivitySettingGoalsBinding
import com.sophia.saveandshowmyactivitytoday.dialog.DetailedPlan
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan
import com.sophia.saveandshowmyactivitytoday.interfaced.PlanDialogInterface
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SettingGoalsActivity : AppCompatActivity(), PlanDialogInterface {

    private lateinit var binding: ActivitySettingGoalsBinding
    private lateinit var preferences: PreferenceManager
    private var dateClick: Boolean = true
    private lateinit var date: String
    private lateinit var detailPlanAdapter: DetailPlanAdapter
    private lateinit var detailPlanList: MutableList<DetailPlan>

    private val today = Calendar.getInstance()
    private val now = System.currentTimeMillis()
    private val dateFormat = SimpleDateFormat("yyyy년MM월dd일" +
            "", Locale.KOREAN).format(now)

    private var yearFormat = today.get(Calendar.YEAR)
    private var monthFormat = today.get(Calendar.MONTH)
    private var dayFormat = today.get(Calendar.DAY_OF_MONTH)

    private val viewmodel by viewModels<TodoViewModel> {
        TodoViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setOnClick()
        detailPlanRecyclerView()
        detailPlan()
    }

    private fun init() {
        preferences = PreferenceManager(applicationContext)
        detailPlanList = mutableListOf()
        binding.date.text = dateFormat
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

    private fun detailPlan() {
        binding.add.setOnClickListener {
            val detailedPlan = DetailedPlan(this)
            detailedPlan.show(supportFragmentManager, "detailedPlan")
        }
    }

    private fun detailPlanRecyclerView() {
        detailPlanAdapter = DetailPlanAdapter()
        binding.recyclerView.let {
            it.adapter = detailPlanAdapter
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            it.setHasFixedSize(true)
        }
        viewmodel.detailPlanLiveData().observe(this, {
            detailPlanAdapter.submitList(it)
        })
    }

    private fun getCalendarDate() {
        if (dateClick) {
            binding.fragmentView.visibility = View.VISIBLE
            binding.datePickerActions.visibility = View.VISIBLE
            binding.view1.visibility = View.VISIBLE
            try {
                val datePicker = DatePicker.OnDateChangedListener { _, year, month, day ->
                    yearFormat = year
                    monthFormat = month + 1
                    dayFormat = day

                    if (monthFormat < 10) {
                        dDay(yearFormat.toString(), "0$monthFormat", dayFormat.toString())
                    } else {
                        dDay(yearFormat.toString(), monthFormat.toString(), dayFormat.toString())
                    }
                }
                binding.datePickerActions.init(yearFormat, monthFormat, dayFormat, datePicker)
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

    private fun dDay(year: String, month: String, day: String) {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.KOREAN)
        date = "$year$month$day"
        //1일의 값 = 24시간 * 60분 * 60초 * 1000(1초값)
        val endDate = dateFormat.parse(date).time
        val time = Calendar.getInstance().apply {
            //시간,분,초 밀리초 제외
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time

        val dDay = "${(time - endDate) / (60 * 60 * 24 * 1000)}"
        preferences.putString("dDay", dDay)
//        Log.d("두 날짜간의 차이(일)", "${(endDate - startDate) / (24 * 60 * 60 * 1000)}")
//        Log.d("시작일 부터 경과 일", "${(today - startDate) / (24 * 60 * 60 * 1000)}")
//        Log.d("D_day", "${(today - endDate) / (60 * 60 * 24 * 1000)}")

    }

    override fun detailPlan(text: String) {
        viewmodel.addPlan(text)
    }
}
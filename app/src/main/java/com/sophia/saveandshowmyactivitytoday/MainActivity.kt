package com.sophia.saveandshowmyactivitytoday

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sophia.saveandshowmyactivitytoday.adapter.CheckAdapter
import com.sophia.saveandshowmyactivitytoday.adapter.TodoAdapter
import com.sophia.saveandshowmyactivitytoday.database.TodoDatabase
import com.sophia.saveandshowmyactivitytoday.database.getTodoDatabase
import com.sophia.saveandshowmyactivitytoday.databinding.ActivityMainBinding
import com.sophia.saveandshowmyactivitytoday.dialog.DialogTodo
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), CustomDialogInterface, CheckListData {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var db: TodoDatabase
    private lateinit var preferences: PreferenceManager
    private lateinit var progressList: ArrayList<Check>

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private val now = System.currentTimeMillis()
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).format(now)

    private val yearFormat = SimpleDateFormat("yyyy", Locale.KOREAN).format(now).toInt()
    private val monthFormat = SimpleDateFormat("MM", Locale.KOREAN).format(now).toInt()
    private val dayFormat = SimpleDateFormat("dd", Locale.KOREAN).format(now).toInt()


    private val viewmodel by viewModels<TodoViewModel> {
        TodoViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = getTodoDatabase(this)

        year = yearFormat
        month = monthFormat
        day = dayFormat

        init()
        todayObserver()
        initRecyclerview()
        bottomSheetButton()
        timeRemaining()
        progress()
    }


    private fun init() {
        binding.addMemo.setOnClickListener {
            val myCustomDialog = DialogTodo(this)
            myCustomDialog.show(supportFragmentManager, "DialogTodo")
        }
        binding.today.text = sdf
        binding.viewAll.setOnClickListener {
            flipTheScreen()
        }
        binding.ivEditGoal.setOnClickListener {
            startActivity(Intent(this, SettingGoalsActivity::class.java))
        }
        progressList = ArrayList()
    }

    @SuppressLint("SetTextI18n")
    private fun timeRemaining() {
        preferences = PreferenceManager(applicationContext)
        val mygoal = preferences.getString("mygoal")
        binding.goalTv.text = mygoal

        val dDayText = preferences.getString("dDay")
        binding.dDay.text = "D$dDayText"
    }

    private fun progress() {
        viewmodel.checkLiveData.observe(this, {
            val um = it.size / 10
            val um2 = it.size / 30
            val um3 = it.size / 50
            val um4 = it.size / 80
            val um5 = it.size / 100
            if (it.size / 10 == um) {
                binding.progressBar.incrementProgressBy(10)
            }
            Log.d("size", it.size.toString())
            Log.d("tag", um.toString())
        })
    }

    private fun todayObserver() {
        viewmodel.readDateData(year, month, day).observe(this, {
            (binding.recyclerView.adapter as TodoAdapter).submitList(it)
            binding.recyclerView.visibility = View.VISIBLE
            binding.noTaskToday.visibility = View.GONE
        })
    }

    private fun initRecyclerview() {
        adapter = TodoAdapter(viewmodel, this)
        binding.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(true)
        }
    }

    private fun bottomSheetButton() {
        binding.bottomSheet.setOnClickListener {
            val adapter = CheckAdapter()
            viewmodel.readCheckedDateData(year, month, day).observe(this, {
                adapter.submitList(it)
            })
            val bottomSheet = BottomSheet(adapter)
            bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun flipTheScreen() {
        val intent = Intent(this, ViewAllActivity::class.java)
        startActivity(intent)
    }

    override fun onOkButtonClicked(content: String) {
        viewmodel.addTodo(content, year, month, day, sdf)
    }

    override fun checkList(content: String) {
        viewmodel.addCheck(content, sdf, year, month, day)
    }
}

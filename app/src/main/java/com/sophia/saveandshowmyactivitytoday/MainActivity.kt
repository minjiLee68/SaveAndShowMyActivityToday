package com.sophia.saveandshowmyactivitytoday

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sophia.saveandshowmyactivitytoday.adapter.CheckAdapter
import com.sophia.saveandshowmyactivitytoday.adapter.TodoAdapter
import com.sophia.saveandshowmyactivitytoday.database.TodoDatabase
import com.sophia.saveandshowmyactivitytoday.database.getTodoDatabase
import com.sophia.saveandshowmyactivitytoday.databinding.ActivityMainBinding
import com.sophia.saveandshowmyactivitytoday.dialog.DialogTodo
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan
import com.sophia.saveandshowmyactivitytoday.interfaced.CheckListData
import com.sophia.saveandshowmyactivitytoday.register.PreferenceManager
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), TodoDialogInterface, CheckListData {

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
        progressBar()
        preference()

    }


    private fun init() {
        preferences = PreferenceManager(applicationContext)
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
        binding.detailBtn.setOnClickListener {
            popupMenu(it)
        }
    }

    private fun progressBar() {
        binding.progressBar.progress = 0
        binding.progressBar.max = 100

        viewmodel.planCheckLive.observe(this, { check ->
            viewmodel.detailPlanLiveData().observe(this, { plan ->
                binding.progressBar.progress =
                    ((check.size.toDouble() / plan.size.toDouble()) * 100).toInt()
                if (check.isNotEmpty() && check.size == plan.size) {
                    binding.progressBar.progress = 100
                }
            })
            if (check.isEmpty()) {
                binding.progressBar.progress = 0
            }
        })
    }

    private fun detailPlanText(detailPlan: String) {
        preferences.putString("planText", detailPlan)
        val detailPlanText = preferences.getString("planText")
        if (detailPlanText != null) {
            binding.detailPlan.text = detailPlanText
        }
    }

    private fun preference() {
        viewmodel.detailPlanLiveData().observe(this, {
            preferences.putInteger("detailSize", it.size)
        })
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun popupMenu(v: View) {
        val detailMenus = PopupMenu(applicationContext, v, R.style.MyPopupMenu)
        viewmodel.detailPlanLiveData().observe(this, { it ->
            try {
                for (i in it.indices) {
                    detailMenus.menu.add(0, i, 0, it[i].content)

                    val id = preferences.getInteger("itemId")
                    if (id == it[i].id) {
                        detailMenus.menu.removeItem(it[i].id)
                        Log.d("menu", detailMenus.menu[i].toString())
                    }
                }
                detailMenus.setOnMenuItemClickListener { item ->
                    val itemId = item.itemId
                    val planText = it[itemId].content
                    detailPlanText(planText)
                    return@setOnMenuItemClickListener true
                }
                detailMenus.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun timeRemaining() {
        val mygoal = preferences.getString("mygoal")
        val dDayText = preferences.getString("dDay")

        if (mygoal != null) {
            binding.goalTv.text = mygoal
        }

        if (dDayText != null) {
            binding.dDay.text = "D$dDayText"
        }
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

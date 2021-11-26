package com.sophia.saveandshowmyactivitytoday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sophia.saveandshowmyactivitytoday.adapter.CheckAdapter
import com.sophia.saveandshowmyactivitytoday.adapter.ImageSliderAdapter
import com.sophia.saveandshowmyactivitytoday.adapter.TodoAdapter
import com.sophia.saveandshowmyactivitytoday.database.TodoDatabase
import com.sophia.saveandshowmyactivitytoday.database.getTodoDatabase
import com.sophia.saveandshowmyactivitytoday.databinding.ActivityMainBinding
import com.sophia.saveandshowmyactivitytoday.dialog.DialogTodo
import com.sophia.saveandshowmyactivitytoday.entity.SliderImages
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CustomDialogInterface, CheckListData {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var db: TodoDatabase
    private lateinit var  indicatorsContainer: LinearLayout

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
        imageSliderViewPager()
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

    private val imageSliderAdapter = ImageSliderAdapter(
        listOf(
            SliderImages(
                R.drawable.image.toString()
            ),
            SliderImages(
                R.drawable.image2.toString()
            )
        )
    )

    private fun imageSliderViewPager() {
        indicatorsContainer = binding.indicatorsContainer
        binding.viewpager2.adapter = imageSliderAdapter

        val indicators = arrayOfNulls<ImageView>(imageSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.poster
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    override fun onOkButtonClicked(content: String) {
        viewmodel.addTodo(content, year, month, day, sdf)
    }

    override fun checkList(content: String) {
        viewmodel.addCheck(content, sdf, year, month, day)
    }
}

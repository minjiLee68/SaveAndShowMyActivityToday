package com.sophia.saveandshowmyactivitytoday

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sophia.saveandshowmyactivitytoday.adapter.TodoAdapter
import com.sophia.saveandshowmyactivitytoday.databinding.ActivityMainBinding
import com.sophia.saveandshowmyactivitytoday.databinding.LayoutBottomSheetBinding
import com.sophia.saveandshowmyactivitytoday.dialog.DialogTodo
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CustomDialogInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoAdapter

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

        addMemo()
        today()
        initRecyclerview()
        bottomSheetButton()
    }

    private fun addMemo() {
        binding.addMemo.setOnClickListener {
            val myCustomDialog = DialogTodo(this)
            myCustomDialog.show(supportFragmentManager, "DialogTodo")
        }
        binding.today.text = sdf
    }

    private fun today() {
        year = yearFormat
        month = monthFormat
        day = dayFormat

        viewmodel.readDateData(year, month, day).observe(this, {
            (binding.recyclerView.adapter as TodoAdapter).submitList(it)
            binding.recyclerView.visibility = View.VISIBLE
            binding.noTaskToday.visibility = View.GONE
        })
    }

    private fun initRecyclerview() {
        adapter = TodoAdapter(viewmodel)
        binding.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(true)
        }
    }

    private fun bottomSheetButton() {

        val bottomSheetDialog = BottomSheetDialog(
            this, R.style.BottomSheetDialogTheme
        )
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
//            R.layout.layout_bottom_sheet, findViewById<LinearLayout>(R.id.bottom_sheet)
//        )
        val bottomSheetView = inflater.inflate(
            R.layout.layout_bottom_sheet,
            findViewById<LinearLayout>(R.id.bottom_sheet),
            false
        )
        bottomSheetView.findViewById<View>(R.id.image_top).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        binding.bottomSheet.setOnClickListener {
            bottomSheetDialog.show()
        }

//        binding.bottomSheet.setOnClickListener {
//            val bottomSheetDialog = BottomSheetDialog(
//                this, R.style.BottomSheetStyle
//            )
//            val bottomSheetView: View = LayoutInflater.from(applicationContext)
//                .inflate(R.layout.layout_bottom_sheet, findViewById(R.id.bottom_sheet_container))
//            bottomSheetView.setOnClickListener {
//                Toast.makeText(this, "Share...", Toast.LENGTH_SHORT).show()
//                bottomSheetDialog.dismiss()
//            }
//            bottomSheetDialog.setContentView(bottomSheetView)
//            bottomSheetDialog.show()
//        }
    }

    override fun onOkButtonClicked(content: String) {
        viewmodel.addTodo(content, year, month, day, sdf)
    }
}

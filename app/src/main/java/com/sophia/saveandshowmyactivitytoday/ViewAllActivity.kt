package com.sophia.saveandshowmyactivitytoday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sophia.saveandshowmyactivitytoday.adapter.ViewAllAdapter
import com.sophia.saveandshowmyactivitytoday.databinding.ActivityViewAllBinding
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory

class ViewAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllBinding
    private lateinit var adapter: ViewAllAdapter

    private val viewmodel by viewModels<TodoViewModel> {
        TodoViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        setObserver()
    }

    private fun initRecyclerView() {
        adapter = ViewAllAdapter()
        binding.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(true)
        }
    }

    private fun setObserver() {
        viewmodel.checkLiveData.observe(this, {
            adapter.submitList(it)
        })
    }
}
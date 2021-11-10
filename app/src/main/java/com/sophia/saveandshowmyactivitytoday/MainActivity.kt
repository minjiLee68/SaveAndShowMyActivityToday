package com.sophia.saveandshowmyactivitytoday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.sophia.saveandshowmyactivitytoday.databinding.ActivityMainBinding
import com.sophia.saveandshowmyactivitytoday.dialog.DialogTodo
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModel
import com.sophia.saveandshowmyactivitytoday.viewmodel.TodoViewModelFactory

class MainActivity : AppCompatActivity(), CustomDialogInterface {

    private lateinit var binding: ActivityMainBinding

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private val viewmodel by viewModels<TodoViewModel> {
        TodoViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMemo.setOnClickListener {
            val myCustomDialog = DialogTodo(this, this)
            myCustomDialog.show()
        }
    }

    override fun onOkButtonClicked(content: String) {
        viewmodel.addTodo(content, year, month, day)
    }
}

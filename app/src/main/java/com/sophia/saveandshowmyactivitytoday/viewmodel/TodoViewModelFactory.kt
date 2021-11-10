package com.sophia.saveandshowmyactivitytoday.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sophia.saveandshowmyactivitytoday.repository.TodoRepository
import java.lang.IllegalArgumentException

class TodoViewModelFactory(application: Application): ViewModelProvider.Factory {

    private val repository = TodoRepository(application)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("")
    }
}
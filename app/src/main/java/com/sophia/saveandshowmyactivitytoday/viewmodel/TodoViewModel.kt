package com.sophia.saveandshowmyactivitytoday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity
import com.sophia.saveandshowmyactivitytoday.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class TodoViewModel(private val repository: TodoRepository): ViewModel() {

    val readAllData: LiveData<List<TodoEntity>> = repository.readAllData

    private var _currentData = MutableLiveData<TodoEntity>()
    val currentData: LiveData<TodoEntity>
        get() = _currentData

    fun addTodo(content: String, year: Int, month: Int, day: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val todo = TodoEntity(content,year, month, day)
            repository.addTodo(todo)
        }
    }

    fun updateTodo(content: String, year: Int, month: Int, day: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val todo = currentData.value?.apply {
                this.content = content
                this.year = year
                this.month = month
                this.day = day
            } ?: throw Exception("")
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun readDateData(year: Int, month: Int, day: Int): LiveData<List<TodoEntity>> =
        repository.readDateData(year, month, day)

}
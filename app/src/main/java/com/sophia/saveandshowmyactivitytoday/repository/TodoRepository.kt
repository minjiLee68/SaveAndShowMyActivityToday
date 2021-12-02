package com.sophia.saveandshowmyactivitytoday.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.sophia.saveandshowmyactivitytoday.database.TodoDatabase
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.entity.Goal
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

class TodoRepository(application: Application) {


    companion object {
        private const val DATABASE_NAME = "TodoDatabase"
    }

    private val db = Room.databaseBuilder(
        application, TodoDatabase::class.java, DATABASE_NAME
    ).build()

    private val todoDao = db.todoDao()
    private val checkDao = db.checkDao()

    val readAllData: LiveData<List<TodoEntity>> = todoDao.readAllData()
    val readDoneData: LiveData<List<TodoEntity>> = todoDao.readDoneData()
    val getAll: LiveData<List<TodoEntity>> = todoDao.getAll()

    fun readDateData(year: Int, month: Int, day: Int): LiveData<List<TodoEntity>> =
        todoDao.readDateData(year, month, day)

    fun addTodo(todo: TodoEntity) {
        todoDao.addTodo(todo)
    }

    fun updateTodo(todo: TodoEntity) {
        todoDao.updateTodo(todo)
    }

    fun deleteTodo(todo: TodoEntity) {
        todoDao.deleteTodo(todo)
    }

    val getCheck: LiveData<List<Check>> = checkDao.checkAllData()

    fun readCheckedDateData(year: Int, month: Int, day: Int): LiveData<List<Check>> =
        checkDao.readCheckedDateData(year, month, day)

    fun addCheck(check: Check) {
        checkDao.addCheck(check)
    }

}
package com.sophia.saveandshowmyactivitytoday.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.sophia.saveandshowmyactivitytoday.adapter.TodoAdapter
import com.sophia.saveandshowmyactivitytoday.database.TodoDatabase
import com.sophia.saveandshowmyactivitytoday.entity.CheckList
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

class TodoRepository(application: Application) {


    companion object {
        private const val DATABASE_NAME = "TodoDatabase"
    }

    private val db = Room.databaseBuilder(
        application, TodoDatabase::class.java, DATABASE_NAME
    ).build()

    private val todoDao = db.todoDao()

    val readAllData: LiveData<List<TodoEntity>> = todoDao.readAllData()
    val readDoneData: LiveData<List<TodoEntity>> = todoDao.readDoneData()
    val getAll: LiveData<List<TodoEntity>> = todoDao.getAll()

    fun readDateData(year: Int, month: Int, day: Int): LiveData<List<TodoEntity>> =
        todoDao.readDateData(year, month, day)

    fun checkTodo(todo: TodoEntity) {
        todoDao.checkTodo(todo)
    }

    fun checkList(content: String, year: Int, month: Int, day: Int, check: Boolean) {
        CheckList(content, year, month, day, check)
    }

    fun addTodo(todo: TodoEntity) {
        todoDao.addTodo(todo)
    }

    fun updateTodo(todo: TodoEntity) {
        todoDao.updateTodo(todo)
    }

    fun deleteTodo(todo: TodoEntity) {
        todoDao.deleteTodo(todo)
    }

}
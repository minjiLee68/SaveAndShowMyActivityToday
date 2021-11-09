package com.sophia.saveandshowmyactivitytoday.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo WHERE year =:year AND month =:month AND day =:day ORDER BY id DESC")
    fun readDateData(year: Int, month: Int, day: Int): LiveData<List<TodoEntity>>

    @Query("SELECT * FROM todo ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readAllData(): LiveData<List<TodoEntity>>

    @Insert
    fun addTodo(todo: TodoEntity)

    @Update
    fun updateTodo(todo: TodoEntity)

    @Delete
    fun deleteTodo(todo: TodoEntity)
}
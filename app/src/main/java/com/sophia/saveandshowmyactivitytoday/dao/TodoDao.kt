package com.sophia.saveandshowmyactivitytoday.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

@Dao
interface TodoDao {
    //해당하는 날짜 메모만 반환
    @Query("SELECT * FROM todo WHERE year =:year AND month =:month AND day =:day ORDER BY id DESC")
    fun readDateData(year: Int, month: Int, day: Int): LiveData<List<TodoEntity>>

    //큰 날짜부터 출력
    @Query("SELECT * FROM todo ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readAllData(): LiveData<List<TodoEntity>>

    //체크한 메모만 출력
    @Query("SELECT * FROM todo WHERE `check` = 1 ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readDoneData(): LiveData<List<TodoEntity>>

    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getAll(): LiveData<List<TodoEntity>>

    @Insert
    fun addTodo(todo: TodoEntity)

    @Update
    fun updateTodo(todo: TodoEntity)

    @Delete
    fun deleteTodo(todo: TodoEntity)
}
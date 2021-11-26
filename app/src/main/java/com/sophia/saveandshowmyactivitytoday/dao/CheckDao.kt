package com.sophia.saveandshowmyactivitytoday.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

@Dao
interface CheckDao {
    @Query("SELECT * FROM check_Entity ORDER BY id DESC")
    fun checkAllData(): LiveData<List<Check>>

    @Query("SELECT * FROM check_Entity WHERE year =:year AND month =:month AND day =:day ORDER BY id DESC ")
    fun readCheckedDateData(year: Int, month: Int, day: Int): LiveData<List<Check>>

    @Insert
    fun addCheck(check: Check)
}
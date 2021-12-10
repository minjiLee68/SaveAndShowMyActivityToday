package com.sophia.saveandshowmyactivitytoday.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlanCheck

@Dao
interface PlanCheckDao {
    @Query("SELECT * FROM detailPlanCheck ORDER BY id DESC")
    fun planCheckLiveData(): LiveData<List<DetailPlanCheck>>

    @Insert
    fun addCheck(plan: DetailPlanCheck)

    @Delete
    fun deleteCheck(plan: DetailPlanCheck)
}
package com.sophia.saveandshowmyactivitytoday.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan

@Dao
interface DetailPlanDao {
    @Query("SELECT * FROM detail ORDER BY id DESC")
    fun getDetailPlan(): LiveData<List<DetailPlan>>
    @Insert
    fun getPlanInsert(detailPlan: DetailPlan)
    @Update
    fun getPlanUpdate(detailPlan: DetailPlan)
    @Delete
    fun getPlanDelete(detailPlan: DetailPlan)
}
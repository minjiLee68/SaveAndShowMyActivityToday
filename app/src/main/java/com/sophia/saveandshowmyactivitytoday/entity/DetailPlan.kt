package com.sophia.saveandshowmyactivitytoday.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail")
data class DetailPlan(
    @ColumnInfo(name = "plan")
    var content: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

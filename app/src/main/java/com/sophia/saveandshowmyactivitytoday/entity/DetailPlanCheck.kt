package com.sophia.saveandshowmyactivitytoday.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailPlanCheck")
data class DetailPlanCheck(
    @ColumnInfo(name = "plan")
    var plan: String = "",
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)

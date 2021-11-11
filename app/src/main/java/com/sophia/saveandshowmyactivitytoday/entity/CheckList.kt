package com.sophia.saveandshowmyactivitytoday.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkList")
data class CheckList(
    @ColumnInfo(name = "content")
    var content: String = "",
    @ColumnInfo(name = "year")
    var year: Int = 0,
    @ColumnInfo(name = "month")
    var month: Int = 0,
    @ColumnInfo(name = "day")
    var day: Int = 0,
    @ColumnInfo(name = "check")
    var check: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

)

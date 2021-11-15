package com.sophia.saveandshowmyactivitytoday.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "check_Entity")
data class Check(
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "date")
    var date: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
): Serializable

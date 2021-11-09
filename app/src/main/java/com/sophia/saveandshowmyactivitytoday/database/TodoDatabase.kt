package com.sophia.saveandshowmyactivitytoday.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sophia.saveandshowmyactivitytoday.dao.TodoDao
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao

}
package com.sophia.saveandshowmyactivitytoday.database

import android.content.Context
import android.provider.Contacts.SettingsColumns.KEY
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sophia.saveandshowmyactivitytoday.dao.CheckDao
import com.sophia.saveandshowmyactivitytoday.dao.DetailPlanDao
import com.sophia.saveandshowmyactivitytoday.dao.TodoDao
import com.sophia.saveandshowmyactivitytoday.entity.Check
import com.sophia.saveandshowmyactivitytoday.entity.DetailPlan
import com.sophia.saveandshowmyactivitytoday.entity.TodoEntity

@Database(entities = [TodoEntity::class, Check::class, DetailPlan::class], version = 2)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    abstract fun checkDao(): CheckDao
    abstract fun detailDao(): DetailPlanDao

}

fun getTodoDatabase(context: Context): TodoDatabase {
    val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE 'check_Entity' ('id' INTEGER, 'content' TEXT, 'date' Text " + "PRIMARY KEY('id'))")
        }
    }
//
//    val migration_2_3 = object : Migration(2, 3) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE detail ADD COLUMN id INTEGER")
//        }
//    }

    return Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        "TodoDB"
    )
        .addMigrations(migration_1_2)
        .build()
}
package com.example.travelmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.travelmanager.dao.TravelDao
import com.example.travelmanager.dao.UserDao
import com.example.travelmanager.entity.Travel
import com.example.travelmanager.entity.User
import com.example.travelmanager.typeConverters.LocalDateConverter
import com.example.travelmanager.typeConverters.TravelPurposeConverter

@Database(
    entities = [User::class, Travel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    TravelPurposeConverter::class,
    LocalDateConverter::class
)


abstract class AppDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun travelDao():TravelDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
//                context.deleteDatabase("item_database")
                Room.databaseBuilder(context, AppDatabase::class.java, "item_database").build().also {
                    Instance = it
                }
            }
        }
    }
}
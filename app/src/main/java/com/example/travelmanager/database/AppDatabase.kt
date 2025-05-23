package com.example.travelmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.travelmanager.dao.TravelDao
import com.example.travelmanager.dao.UserDao
import com.example.travelmanager.entity.Travel
import com.example.travelmanager.entity.User
import com.example.travelmanager.typeConverters.LocalDateConverter
import com.example.travelmanager.typeConverters.TravelPurposeConverter

@Database(
    entities = [User::class, Travel::class],
    version = 2,
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
                Room.databaseBuilder(context, AppDatabase::class.java, "item_database").addMigrations(MIGRATION_1_2).build().also {
                    Instance = it
                }
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE `Travel` ADD COLUMN `roteiro` VARCHAR DEFAULT NULL")
    }
}
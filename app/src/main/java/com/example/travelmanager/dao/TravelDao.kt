package com.example.travelmanager.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.travelmanager.entity.Travel
import com.example.travelmanager.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelDao {

    @Insert
    suspend fun insert(travel: Travel)

    @Update
    suspend fun update(travel: Travel)

    @Upsert
    suspend fun upsert(travel: Travel)

    @Delete
    suspend fun delete(travel: Travel)

    @Query("select * from Travel t where t.id =:id")
    suspend fun findById(id:Int) : Travel?

    @Query("select * from Travel t")
    fun findAll(): Flow<List<Travel>>


}
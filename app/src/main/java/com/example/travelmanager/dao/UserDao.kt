package com.example.travelmanager.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.travelmanager.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user:User)

    @Update
    suspend fun update(user: User)

    @Upsert
    suspend fun upsert(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("select * from User u where u.id =:id")
    suspend fun findById(id:Int) : User?

    @Query("select * from User u order by u.name")
    fun findAll():Flow<List<User>>

    @Query("select * from User u where u.login =:login")
    suspend fun findByLogin(login:String):User
}



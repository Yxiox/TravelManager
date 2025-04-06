package com.example.travelmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val login:String="",
    val name:String="",
    val senha:String="",

)

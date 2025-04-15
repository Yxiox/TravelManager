package com.example.travelmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Travel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destino : String,
    val inicio : String,
    val fim : String?,
    val finalidade : String,
    val orcamento : Float,
    val userId : Int
)

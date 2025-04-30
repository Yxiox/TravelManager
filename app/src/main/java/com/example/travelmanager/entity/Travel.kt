package com.example.travelmanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.travelmanager.data.TravelPurposeEnum
import java.time.LocalDateTime

@Entity
data class Travel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destino : String,
    val inicio : LocalDateTime,
    val fim : LocalDateTime?,
    val finalidade : TravelPurposeEnum,
    val orcamento : Float,
    val userId : Int
)

package com.example.travelmanager.typeConverters

import androidx.room.TypeConverter
import com.example.travelmanager.data.TravelPurposeEnum

class TravelPurposeConverter {
    @TypeConverter
    fun fromString(purpose: TravelPurposeEnum): String {
        return purpose.name
    }

    @TypeConverter
    fun toEnum(purpose: String): TravelPurposeEnum {
        return TravelPurposeEnum.valueOf(purpose)
    }

}
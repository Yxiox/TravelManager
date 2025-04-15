package com.example.travelmanager.data

import androidx.lifecycle.ViewModel
import com.example.travelmanager.dao.TravelDao
import com.example.travelmanager.dao.UserDao
import com.example.travelmanager.entity.Travel
import kotlinx.coroutines.flow.Flow


class TravelViewModel (
    private val travelDao: TravelDao
) : ViewModel() {

    val travels:Flow<List<Travel>> = travelDao.findAll()

}
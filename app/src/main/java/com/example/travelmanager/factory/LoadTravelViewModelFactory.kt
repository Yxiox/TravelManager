package com.example.travelmanager.factory

import androidx.lifecycle.ViewModel
import com.example.travelmanager.dao.TravelDao
import com.example.travelmanager.data.EditTravelViewModel
import com.example.travelmanager.data.TravelViewModel

data class LoadTravelViewModelFactory(
    private val travelDao: TravelDao
):androidx.lifecycle.ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TravelViewModel(travelDao) as T
    }
}

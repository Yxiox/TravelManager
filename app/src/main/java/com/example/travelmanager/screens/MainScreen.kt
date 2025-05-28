package com.example.travelmanager.screens

import android.annotation.SuppressLint
import android.os.Build
import android.provider.ContactsContract.Data
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelmanager.database.AppDatabase

import com.example.travelmanager.components.TravelCard
import com.example.travelmanager.data.DataManager
import com.example.travelmanager.data.LoginUserViewModel
import com.example.travelmanager.data.TravelViewModel
import com.example.travelmanager.factory.LoadTravelViewModelFactory
import com.example.travelmanager.factory.LoginUserViewModelFactory


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(onEdit: (Int?) -> Unit,
               onCreateJournal: (Int) -> Unit,
               dataManager: DataManager){

    val ctx = LocalContext.current
    val travelDao = AppDatabase.getDatabase(ctx).travelDao()
    val userDao = AppDatabase.getDatabase(ctx).userDao()

    val travelViewModel:TravelViewModel = viewModel(
        factory = LoadTravelViewModelFactory(travelDao = travelDao)
    )

    val loginUserViewModel:LoginUserViewModel = viewModel(
        factory = LoginUserViewModelFactory(userDao = userDao, dataManager = dataManager)
    )

    val travelsState = travelViewModel.travels.collectAsState(
        emptyList()
    )

    Scaffold (containerColor = Color(red = 50, green = 50, blue = 50))
    {
        Column () {
            LazyColumn {
                items(travelsState.value) { travel ->
                    if (travel.userId == loginUserViewModel.dataManager.getUserId()!!.toInt()){
                    TravelCard(travel, onEdit = {
                        onEdit(it)
                    },
                        onCreateJournal= {onCreateJournal(it)}
                        )
                    }
                }
            }
        }
    }
}

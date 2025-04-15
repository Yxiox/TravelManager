package com.example.travelmanager.screens

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.travelmanager.database.AppDatabase
import com.example.travelmanager.entity.User

import com.example.travelmanager.components.TravelCard
import com.example.travelmanager.entity.Travel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onRegisterTrip:()->Unit,
    onEditTrip:()->Unit){

    val ctx = LocalContext.current
    val travelDao = AppDatabase.getDatabase(ctx).travelDao()

    val travels = listOf( Travel(id = 1, destino = "bahamas", fim = LocalDate.now().toString(), inicio = LocalDate.now().toString(), userId = 1, orcamento = 1f, finalidade = "lazer"))

    Scaffold (containerColor = Color(red = 50, green = 50, blue = 50))
    {
        Column () {
            LazyColumn {
                items(travels) { travel ->
                    TravelCard(travel)
                }
            }
        }
    }
}

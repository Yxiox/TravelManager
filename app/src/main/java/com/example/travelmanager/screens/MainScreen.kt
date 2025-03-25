package com.example.travelmanager.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onRegisterTrip:()->Unit,
    onEditTrip:()->Unit,){
    Scaffold (containerColor = Color(red = 50, green = 50, blue = 50)
        ,topBar = { TopAppBar(title = { Text("Travel Manager") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,titleContentColor = Color.Black)) })
    {
        Column (Modifier.padding(0.dp)) {

        }
    }


}

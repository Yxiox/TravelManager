package com.example.travelmanager.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myregistry.components.MyTextField
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onRegisterTrip:()->Unit,
    onEditTrip:()->Unit,){
    Scaffold (containerColor = Color(red = 50, green = 50, blue = 50)
        ,topBar = { TopAppBar(title = { Text("Travel Manager", fontWeight = FontWeight.W900, fontSize = 25.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray,titleContentColor = Color.White))},
       )
    {
        Column ( modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true)) {


        }

    }


}

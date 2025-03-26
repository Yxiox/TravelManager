package com.example.travelmanager.screens

import android.icu.text.DateFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myregistry.components.MyTextField
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelForm(){
    var selectedOption by remember { mutableStateOf("") }
    Column (modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true)) {
        MyTextField(value = "", onValueChange = {""}, label = "Destino", required = true)

        Row (verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedOption == "lazer",
                onClick = { selectedOption = "lazer" }
            )
            Text(text = "À lazer", modifier = Modifier.padding(start = 8.dp))
            RadioButton(
                selected = selectedOption == "negocios",
                onClick = { selectedOption = "negocios" }
            )
            Text(text = "À negócios", modifier = Modifier.padding(start = 8.dp))
        }

        Box(){
            DatePicker(state = DatePickerState(locale = Locale.ENGLISH), modifier = Modifier.padding(horizontal = 50.dp), headline = { Text("Data de início")})
            DatePicker(state = DatePickerState(locale = Locale.ENGLISH), modifier = Modifier.padding(horizontal =  50.dp), headline = { Text("Data de retorno")})
        }

        MyTextField(value = "", onValueChange = {""}, label = "Orçamento", required = true)

    }
}


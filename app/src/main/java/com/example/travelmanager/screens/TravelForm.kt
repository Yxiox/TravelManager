package com.example.travelmanager.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myregistry.components.MyTextField
import com.example.travelmanager.data.EditTravelViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelForm(id:Int?){

    Log.d("onEdit", "Id ${id}")

    val editTravelViewModel : EditTravelViewModel = viewModel()
    var travel = editTravelViewModel.uiState.collectAsState()
    val ctx = LocalContext.current

    var selectedOption by remember { mutableStateOf("") }
    Column (modifier = Modifier.verticalScroll(state = rememberScrollState(), enabled = true).padding(25.dp).fillMaxSize()) {
        MyTextField(value = "", onValueChange = {""}, label = "Destino", required = true)

        Row (verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = travel.value.finalidade == "lazer",
                onClick = { editTravelViewModel.onFinalidadeChange("lazer") }
            )
            Text(text = "À lazer", modifier = Modifier.padding(start = 8.dp))
            RadioButton(
                selected = travel.value.finalidade == "negocios",
                onClick = { editTravelViewModel.onFinalidadeChange("negocios") }
            )
            Text(text = "À negócios", modifier = Modifier.padding(start = 8.dp))
        }

        val context = LocalContext.current
        var showDatePickerInicio by remember { mutableStateOf(false) }
        var dataFim by remember { mutableStateOf(LocalDate.now()) }
        var showDatePickerFim by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = LocalDate.now().toEpochDay() * 24 * 60 * 60 * 1000
        )

        Row (Modifier.border(width = 0.2.dp, color = Color.White, shape = RoundedCornerShape(5.dp)).fillMaxWidth().padding(horizontal = 5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Data de início: ${
                travel.value.inicio!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}", color = Color.White, fontWeight = FontWeight.W900)
            OutlinedButton(onClick = {showDatePickerInicio = true}, colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White), border = BorderStroke(0.2.dp, Color.White), shape = RoundedCornerShape(5.dp)) { Text("Alterar Data") }
        }


        if (showDatePickerInicio) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerInicio = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                                editTravelViewModel.onInicioChange(Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate())
                        }
                        showDatePickerInicio = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePickerInicio = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        if (showDatePickerFim) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerFim = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            dataFim = Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
                        showDatePickerFim = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePickerFim = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        MyTextField(value = "", onValueChange = {""}, label = "Orçamento", required = true)

    }
}



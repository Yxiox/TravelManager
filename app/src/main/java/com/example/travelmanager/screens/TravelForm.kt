package com.example.travelmanager.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myregistry.components.DecimalTextField
import com.example.myregistry.components.MyTextField
import com.example.travelmanager.data.DataManager
import com.example.travelmanager.data.EditTravelViewModel
import com.example.travelmanager.data.LoginUserViewModel
import com.example.travelmanager.data.TravelPurposeEnum
import com.example.travelmanager.database.AppDatabase
import com.example.travelmanager.factory.EditTravelViewModelFactory
import com.example.travelmanager.factory.LoginUserViewModelFactory
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelForm(
    backToMain: () -> Unit,
    id: Int?,
    dataManager: DataManager
) {
    val ctx = LocalContext.current

    val travelDao = AppDatabase.getDatabase(ctx).travelDao()
    val userDao = AppDatabase.getDatabase(ctx).userDao()

    Log.d("onEdit", "Id ${id}")

    val loginUserViewModel: LoginUserViewModel = viewModel(
        factory = LoginUserViewModelFactory(userDao, dataManager)
    )
    val editTravelViewModel: EditTravelViewModel = viewModel(
        factory = EditTravelViewModelFactory(id, travelDao, loginUserViewModel)
    )
    var travel = editTravelViewModel.uiState.collectAsState()

    var showDatePickerInicio by remember { mutableStateOf(false) }
    var showDatePickerFim by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = LocalDate.now().toEpochDay() * 24 * 60 * 60 * 1000
    )
    var selectedOption by remember { mutableStateOf(TravelPurposeEnum.lazer) }
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState(), enabled = true)
            .padding(25.dp)
            .fillMaxSize()
    ) {

        MyTextField(
            value = travel.value.destino,
            onValueChange = { editTravelViewModel.onDestinoChange(it) },
            label = "Destino",
            required = true
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            val statuses = TravelPurposeEnum.values()
            statuses.forEach { status ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = travel.value.finalidade == status,
                        onClick = { editTravelViewModel.onFinalidadeChange(status) }
                    )
                    Text(
                        text = status.name.toUpperCase(),
                        color = Color.White,
                        modifier = Modifier.clickable {
                            editTravelViewModel.onFinalidadeChange(
                                status
                            )
                        }
                    )
                }
            }
        }


        Row(Modifier.padding(vertical = 10.dp)) {
            Row(
                Modifier
                    .border(
                        width = 0.5.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Data de início: ${if(travel.value.inicio != null){
                        travel.value.inicio?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    } else {
                        "Não preenchido"
                    }
                    }", color = Color.White, fontWeight = FontWeight.W900
                )
                OutlinedButton(
                    onClick = { showDatePickerInicio = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(0.2.dp, Color.White),
                    shape = RoundedCornerShape(5.dp)
                ) { Text("Alterar Data") }
            }
        }
        Row(Modifier.padding(vertical = 10.dp)) {

            Row(
                Modifier
                    .border(
                        width = 0.5.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Data de fim: ${if(travel.value.fim != null){
                        travel.value.fim?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    } else {
                        "Não preenchido"   
                    }
                    }", color = Color.White, fontWeight = FontWeight.W900
                )
                OutlinedButton(
                    onClick = { showDatePickerFim = true },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(0.2.dp, Color.White),
                    shape = RoundedCornerShape(5.dp)
                ) { Text("Alterar Data") }
            }
        }

        if (showDatePickerInicio) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerInicio = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            editTravelViewModel.onInicioChange(
                                Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                            )
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
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = { showDatePickerFim = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            editTravelViewModel.onFimChange(
                                Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                            )
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

        var orcamento by remember { mutableStateOf("") }
        DecimalTextField(
            value = orcamento,
            onValueChange = { new ->
                orcamento = new
                val parsed = new.toFloatOrNull() ?: 0f
                editTravelViewModel.onOrcamentoChange(parsed)
            },
            label = "Orçamento",
            required = true
        )

        OutlinedButton(
            onClick = { editTravelViewModel.cadastrarViagem() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black,
                containerColor = Color.White
            ),
            border = BorderStroke(0.2.dp, Color.White),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) { Text("Cadastrar viagem") }

        LaunchedEffect(travel.value.isSaved) {
            if (travel.value.isSaved) {
                Toast.makeText(ctx, "Travel registered", Toast.LENGTH_SHORT).show()
                editTravelViewModel.cleanValidationValues()
                backToMain()
            } else {
            }
        }


    }
}

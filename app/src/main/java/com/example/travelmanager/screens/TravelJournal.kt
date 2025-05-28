package com.example.travelmanager.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelmanager.data.DataManager
import com.example.travelmanager.data.EditTravelViewModel
import com.example.travelmanager.data.LoginUserViewModel
import com.example.travelmanager.database.AppDatabase
import com.example.travelmanager.factory.EditTravelViewModelFactory
import com.example.travelmanager.factory.LoginUserViewModelFactory

@Composable
fun TravelJournal(
    backToMain: () -> Unit,
    id: Int?,
    dataManager: DataManager
) {
    val ctx = LocalContext.current

    val travelDao = AppDatabase.getDatabase(ctx).travelDao()
    val userDao = AppDatabase.getDatabase(ctx).userDao()

    Log.d("onEdit", "Id $id")

    val loginUserViewModel: LoginUserViewModel = viewModel(
        factory = LoginUserViewModelFactory(userDao, dataManager)
    )
    val editTravelViewModel: EditTravelViewModel = viewModel(
        factory = EditTravelViewModelFactory(id, travelDao, loginUserViewModel)
    )
    val travel = editTravelViewModel.uiState.collectAsState()

    val showDialog = remember { mutableStateOf(false) }
    val newChanges = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = travel.value.roteiro ?: "",
            onValueChange = { editTravelViewModel.onRoteiroChange(it) },
            label = { Text("O roteiro aparecerá aqui...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )
        Row(modifier = Modifier.padding(10.dp)) {
            if (travel.value.roteiro.isNullOrEmpty()) {
                OutlinedButton(
                    onClick = {
                        editTravelViewModel.sendPrompt("Estou fazendo uma viagem à ${travel.value.finalidade} para ${travel.value.destino}, minha saída será em ${travel.value.inicio} e voltarei em ${travel.value.fim}. Preciso de um roteiro para minha viagem. Meu orçamento é de ${travel.value.orcamento}, cite o que couber no orçamento, caso seja impossível de fazer todos os essenciais com este orçamento, quero que me diga o que falta e de sugestões. Vou lhe passar algumas considerações, caso esteja vazio daqui em diante, desconsidere esta frase: $newChanges")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text("Gerar Roteiro")
                }
            } else {
                OutlinedButton(
                    onClick = {
                        showDialog.value = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Text("Alterar Roteiro")
                }

                OutlinedButton(
                    onClick = {editTravelViewModel.cadastrarViagem()},
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Text("Guardar Roteiro")
                }

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Text("Refazer Roteiro")
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Editar Roteiro") },
            text = {
                OutlinedTextField(
                    value = newChanges.value,
                    onValueChange = { newChanges.value = it },
                    label = { Text("Novo texto do roteiro") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    editTravelViewModel.sendPrompt("Estou fazendo uma viagem à ${travel.value.finalidade} para ${travel.value.destino}, minha saída será em ${travel.value.inicio} e voltarei em ${travel.value.fim}. Preciso de um roteiro para minha viagem. Meu orçamento é de ${travel.value.orcamento}, cite o que couber no orçamento, caso seja impossível de fazer todos os essenciais com este orçamento, quero que me diga o que falta e de sugestões. Vou lhe passar algumas considerações, caso esteja vazio daqui em diante, desconsidere esta frase: $newChanges")
                    showDialog.value = false
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
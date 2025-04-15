package com.example.travelmanager.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

data class Travel(
    val destino : String = "",
    val inicio : LocalDate?,
    val fim : LocalDate?,
    val finalidade : String = "",
    val orcamento : Float = 0f,
    val userId : Int = 0,
    val errorMessage : String = ""
){
    fun validateFields(){
        if (destino.isBlank()){
            throw Exception("Destino é obrigatório")
        }
        if (inicio == null){
            throw Exception("Data de início é obrigatório")
        }
        if (fim == null){
            throw Exception("Data de retorno é obrigatório")
        }
    }

}

class EditTravelViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(Travel(
        destino = "",
        inicio = null,
        fim = null,
        finalidade = "",
        orcamento = 0f,
        errorMessage = ""
    ))
    val uiState : StateFlow<Travel> = _uiState.asStateFlow()

    fun onDestinoChange(destino: String){
        _uiState.value = _uiState.value.copy(destino = destino)

    }

    fun onInicioChange(inicio: LocalDate){
        _uiState.value = _uiState.value.copy(inicio = inicio)

    }

    fun onFimChange(fim: LocalDate){
        _uiState.value = _uiState.value.copy(fim = fim)

    }

    fun onFinalidadeChange(finalidade: String){
        _uiState.value = _uiState.value.copy(finalidade = finalidade)

    }

    fun onOrcamentoChange(orcamento: Float){
        _uiState.value = _uiState.value.copy(orcamento = orcamento)
    }

    fun cadastrarViagem():Boolean{
        try {
            _uiState.value.validateFields()

            return true
        }catch (e:Exception){
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
            return false
        }
    }
}
package com.example.travelmanager.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmanager.dao.TravelDao
import com.example.travelmanager.dao.UserDao
import com.example.travelmanager.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

data class Travel(
    val id:Int = 0,
    val destino : String = "",
    val inicio : LocalDateTime?,
    val fim : LocalDateTime?,
    val finalidade : TravelPurposeEnum?,
    val orcamento : Float = 0f,
    val roteiro: String?,
    val userId : Int = 0,
    val errorMessage : String = "",
    val isSaved:Boolean = false
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
    fun toTravel(userId: Int):com.example.travelmanager.entity.Travel{

        return com.example.travelmanager.entity.Travel(
            id = id,
            destino = destino,
            inicio = inicio!!,
            fim = fim!!,
            finalidade = finalidade!!,
            orcamento = orcamento,
            userId = userId,
            roteiro = roteiro!!,
        )
    }

}

class EditTravelViewModel (
    id:Int?,
    private val travelDao: TravelDao,
    private val loginUserViewModel: LoginUserViewModel
) : ViewModel(){
    private val _uiState = MutableStateFlow(Travel(
        destino = "",
        inicio = null,
        fim = null,
        finalidade = null,
        orcamento = 0f,
        errorMessage = "",
        roteiro = ""
    ))
    val uiState : StateFlow<Travel> = _uiState.asStateFlow()

    init{
        id?.let {id->
            viewModelScope.launch {
                travelDao.findById(id)?.let {travel ->
                    _uiState.value = _uiState.value.copy(
                        id = travel.id,
                        destino = travel.destino,
                        finalidade = travel.finalidade,
                        inicio = travel.inicio,
                        fim = travel.fim,
                        orcamento = travel.orcamento
                    )
                }
            }
        }
    }

    fun onDestinoChange(destino: String){
        _uiState.value = _uiState.value.copy(destino = destino)

    }

    fun onInicioChange(inicio: LocalDateTime){
        _uiState.value = _uiState.value.copy(inicio = inicio)

    }

    fun onFimChange(fim: LocalDateTime){
        _uiState.value = _uiState.value.copy(fim = fim)

    }

    fun onFinalidadeChange(finalidade: TravelPurposeEnum){
        _uiState.value = _uiState.value.copy(finalidade = finalidade)

    }

    fun onOrcamentoChange(orcamento: Float){
        _uiState.value = _uiState.value.copy(orcamento = orcamento)
    }

    fun cadastrarViagem():Boolean{
        try {
            _uiState.value.validateFields()
            viewModelScope.launch {
                    try {
                        travelDao.upsert(_uiState.value.toTravel(loginUserViewModel.dataManager.getUserId()!!.toInt()))
                        _uiState.value = _uiState.value.copy(isSaved = true)
                    }
                    catch (e: Exception){
                        _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
                    }
            }
            return true
        }catch (e:Exception){
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
            return false
        }
    }

    fun cleanValidationValues() {
        _uiState.value = _uiState.value.copy(errorMessage = "", isSaved = false)
    }
}
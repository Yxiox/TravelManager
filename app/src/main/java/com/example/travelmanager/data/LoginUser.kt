package com.example.travelmanager.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.log

data class LoginUser(
    val login : String = "",
    val senha : String = "",
    val errorMessage : String = ""
) {
    fun validateAllFields() {
        if (login.isBlank()){
            throw Exception("Login é obrigatório")
        }
        if (senha.isBlank()){
            throw Exception("Senha é obrigatório")
        }

    }
}

class LoginUserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUser())
    val uiState : StateFlow<LoginUser> = _uiState.asStateFlow()


    fun onLoginChange(login: String){
        _uiState.value = _uiState.value.copy(login = login)

    }

    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)

    }


    fun login():Boolean {
        try {
            _uiState.value.validateAllFields()
            return true
        }
        catch (e: Exception)
        {
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
            return false

        }
    }

    fun cleanErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }

}
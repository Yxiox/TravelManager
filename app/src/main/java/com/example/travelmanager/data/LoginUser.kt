package com.example.travelmanager.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmanager.dao.UserDao
import com.example.travelmanager.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

data class LoginUser(
    val login : String = "",
    val senha : String = "",
    val errorMessage : String = "",
    val logged:Boolean = false
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

class LoginUserViewModel (
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUser())
    val uiState : StateFlow<LoginUser> = _uiState.asStateFlow()


    fun onLoginChange(login: String){
        _uiState.value = _uiState.value.copy(login = login)

    }

    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)

    }


    fun login(login: String, senha: String):Boolean {
        try {
            _uiState.value.validateAllFields()

            viewModelScope.launch {
                val u:User? = userDao.findByLogin(login)
                try {
                    if (u != null){
                        if (u.senha == senha){
                            _uiState.value = _uiState.value.copy(logged = true)
                        }
                        else{
                            throw Exception("Login ou senha inválidos")
                        }
                    }
                    else{
                        throw Exception("Login ou senha inválidos")
                    }
                }
                catch (e: Exception){
                    _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
                }
            }
            return true
        }
        catch (e: Exception)
        {
            _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
            return false

        }
    }

    fun cleanValidationValues() {
        _uiState.value = _uiState.value.copy(errorMessage = "", logged = false)

    }

}
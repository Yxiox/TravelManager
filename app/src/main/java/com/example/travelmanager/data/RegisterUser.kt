package com.example.travelmanager.data

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmanager.dao.UserDao
import com.example.travelmanager.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.jvm.Throws

data class RegisterUser(
    val login : String = "",
    val name : String = "",
    val email : String = "",
    val senha : String = "",
    val confirmarsenha : String = "",
    val errorMessage : String = "",
    val isSaved : Boolean = false
) {
    fun validateAllFields() {
        if (name.isBlank()){
            throw Exception("Nome é obrigatório")
        }
        if (email.isBlank()){
            throw Exception("E-mail é obrigatório")
        }
        if (login.isBlank()){
            throw Exception("Login é obrigatório")
        }
        if (senha.isBlank()){
            throw Exception("Senha é obrigatório")
        }
        if (confirmarsenha.isBlank()) {
            throw Exception("Confirme sua senha")
        }
        else if (!confirmarsenha.equals(senha)){
            throw Exception("As senhas não conferem")
        }

    }
    fun toUser(): User {
        return User(
            login = login,
            name = name,
            senha = senha,
            email = email
        )
    }
}

class RegisterUserViewModel(
    private val userDao: UserDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUser())
    val uiState : StateFlow<RegisterUser> = _uiState.asStateFlow()

    fun onEmailChange(email:String){
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onNameChange(name:String){
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onLoginChange(login: String){
        _uiState.value = _uiState.value.copy(login = login)

    }

    fun onSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(senha = senha)

    }

    fun onConfirmarSenhaChange(senha:String){
        _uiState.value = _uiState.value.copy(confirmarsenha = senha)

    }

    fun register():Boolean {
        try {
            _uiState.value.validateAllFields()

            viewModelScope.launch {
                if (_uiState.value.login != null){
                    val u:User? = userDao.findByLogin(_uiState.value.login)

                    try {
                        if (u == null){
                            val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
                            if (_uiState.value.email.matches(emailRegex)){
                                userDao.insert(_uiState.value.toUser())
                                _uiState.value = _uiState.value.copy(isSaved = true)
                            }
                            else{
                                throw Exception("Formatação de e-mail incorreta")
                            }
                        }
                        else{
                            throw Exception("Login já em uso")
                        }
                    }
                    catch (e: Exception){
                        _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Unknown error")
                    }
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
        _uiState.value = _uiState.value.copy(errorMessage = "", isSaved = false)
    }

}
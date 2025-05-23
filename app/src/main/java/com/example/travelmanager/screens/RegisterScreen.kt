package com.example.travelmanager.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myregistry.components.ErrorDialog
import com.example.myregistry.components.MyPasswordField
import com.example.myregistry.components.MyTextField
import com.example.travelmanager.R
import com.example.travelmanager.data.RegisterUserViewModel
import com.example.travelmanager.database.AppDatabase
import com.example.travelmanager.factory.RegisterUserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onRegister:(String)->Unit,
                   backToLogin:()->Unit)
{
    val ctx = LocalContext.current
    val userDao = AppDatabase.getDatabase(ctx).userDao()

    val registerUserViewModel : RegisterUserViewModel = viewModel(
        factory = RegisterUserViewModelFactory(userDao)
    )
    var registerUser = registerUserViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(65.dp).verticalScroll(scrollState)) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = "Logo", modifier = Modifier.size(200.dp))
        MyTextField(value=registerUser.value.name, onValueChange = {registerUserViewModel.onNameChange(it)}, "Nome", true)
        MyTextField(value=registerUser.value.email, onValueChange = {registerUserViewModel.onEmailChange(it)}, "E-mail", true)
        MyTextField(value=registerUser.value.login, onValueChange = {registerUserViewModel.onLoginChange(it)}, "Login", true)
        MyPasswordField(value=registerUser.value.senha, onValueChange = {registerUserViewModel.onSenhaChange(it)}, "Senha", true)
        MyPasswordField(value=registerUser.value.confirmarsenha, confirmValue = registerUser.value.senha, onValueChange = {registerUserViewModel.onConfirmarSenhaChange(it)}, "Confirmar Senha", true)
        OutlinedButton(onClick = {
          registerUserViewModel.register()

        },
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(60.dp), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text(text = "Registrar") }

        if (registerUser.value.errorMessage.isNotBlank()){
            ErrorDialog(
                error = registerUser.value.errorMessage,
                onDismissRequest = {
                    registerUserViewModel.cleanValidationValues()
                })
        }

        LaunchedEffect(registerUser.value.isSaved) {
            if (registerUser.value.isSaved){
                Toast.makeText(ctx, "User registered", Toast.LENGTH_SHORT).show()
                registerUserViewModel.cleanValidationValues()
                backToLogin()
            }
            else{
            }
        }

        TextButton(onClick = backToLogin) { Text(text = "Já possui cadastro?") }
    }

}




package com.example.travelmanager.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.example.travelmanager.data.DataManager
import com.example.travelmanager.data.LoginUserViewModel
import com.example.travelmanager.database.AppDatabase
import com.example.travelmanager.factory.LoginUserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin:()->Unit,
    onRegister:()->Unit,
    dataManager: DataManager
    ){

    val ctx = LocalContext.current
    val userDao = AppDatabase.getDatabase(ctx).userDao()

    val loginUserViewModel : LoginUserViewModel = viewModel(
        factory = LoginUserViewModelFactory(userDao,dataManager)
    )
    var loginUser = loginUserViewModel.uiState.collectAsState()

    Column (verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(65.dp)) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = "Logo", modifier = Modifier.size(200.dp))
        MyTextField(value=loginUser.value.login, onValueChange = {loginUserViewModel.onLoginChange(it)}, "Login", true)
        MyPasswordField(value=loginUser.value.senha, onValueChange = {loginUserViewModel.onSenhaChange(it)}, "Senha", true)
        OutlinedButton(onClick = {
            loginUserViewModel.login(loginUser.value.login, loginUser.value.senha)
            },

            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(60.dp), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text(text = "Login") }

            if (loginUser.value.errorMessage.isNotBlank()){
                ErrorDialog(error = loginUser.value.errorMessage,
                    onDismissRequest = {
                        loginUserViewModel.cleanValidationValues()
                    })
            }

        LaunchedEffect(loginUser.value.logged) {
            if (loginUser.value.logged){
                Toast.makeText(ctx, "Login Succesfull ID:${loginUserViewModel.dataManager.getUserId()}", Toast.LENGTH_SHORT).show()
                loginUserViewModel.cleanValidationValues()
                onLogin()
            }
            else{
            }

        }

        OutlinedButton(onClick = onRegister,
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(60.dp), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color.DarkGray)) { Text(text = "Registrar-se") }
    }

}

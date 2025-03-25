package com.example.travelmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelmanager.screens.LoginScreen
import com.example.travelmanager.screens.MainScreen
import com.example.travelmanager.screens.RegisterScreen
import com.example.travelmanager.ui.theme.TravelManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TravelManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    Column (Modifier.padding(innerPadding)) {
                        NavHost (
                            navController = navController,
                            startDestination = "LoginScreen"){

                            composable(route="LoginScreen"){
                                LoginScreen(onLogin={navController.navigate("MainScreen")}, onRegister =  {navController.navigate("RegisterScreen")})
                            }

                            composable(route="RegisterScreen"){
                                RegisterScreen (onRegister = {navController.navigate(it)}, backToLogin =  {navController.navigateUp()})
                            }
                            composable(route="MainScreen"){
                                MainScreen(onEditTrip = {}, onRegisterTrip = {})
                            }
//                            composable(route="ScreenC"){
//                            }

                        }
                    }
                }
            }
        }
    }
}




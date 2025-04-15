package com.example.travelmanager

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelmanager.screens.LoginScreen
import com.example.travelmanager.screens.MainScreen
import com.example.travelmanager.screens.RegisterScreen
import com.example.travelmanager.screens.TravelForm
import com.example.travelmanager.ui.theme.TravelManagerTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry = navController.currentBackStackEntryFlow.collectAsState(initial = null)
            TravelManagerTheme {
                //onMainScreen
                //onAddTravel
                //onAbout

                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color(red = 50, green = 50, blue = 50),
                    topBar = { if (currentBackStackEntry.value?.destination?.route != "LoginScreen" && currentBackStackEntry.value?.destination?.route != "RegisterScreen") {TopAppBar(title = { Text("Travel Manager", fontWeight = FontWeight.W900, fontSize = 25.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray,titleContentColor = Color.White))}},
                    bottomBar = {if (currentBackStackEntry.value?.destination?.route != "LoginScreen" && currentBackStackEntry.value?.destination?.route != "RegisterScreen") {
                        BottomNavigation (backgroundColor = Color.LightGray) {
                            val backStack = navController.currentBackStackEntryAsState()
                            val currentDestination = backStack.value?.destination
                            BottomNavigationItem(
                                selected =
                                currentDestination?.hierarchy?.any {
                                    it.route == "MainScreen"
                                } == true,
                                onClick = { navController.navigate("MainScreen") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Main Screen",
                                        tint = Color.White
                                    )
                                }
                            )
                            BottomNavigationItem(
                                selected =
                                currentDestination?.hierarchy?.any {
                                    it.route == "TravelForm"
                                } == true,
                                onClick = { navController.navigate("TravelForm") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add new travel",
                                        tint = Color.White
                                    )
                                }
                            )
                            BottomNavigationItem(
                                selected =
                                currentDestination?.hierarchy?.any {
                                    it.route == "About"
                                } == true,
                                onClick = { navController.navigate("About") },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "About",
                                        tint = Color.White
                                    )
                                }
                            )
                        }
                        }
                    }
                    )
                { innerPadding ->
                    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
                    Column(Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = "LoginScreen"
                        ) {

                            composable(route = "LoginScreen") {
                                LoginScreen(
                                    onLogin = { navController.navigate("MainScreen") },
                                    onRegister = { navController.navigate("RegisterScreen") })
                            }

                            composable(route = "RegisterScreen") {
                                RegisterScreen(
                                    onRegister = { navController.navigate(it) },
                                    backToLogin = { navController.navigateUp() })
                            }
                            composable(route = "MainScreen") {
                                MainScreen(onEditTrip = {
                                    navController.navigate("TravelForm/${it}")
                                }, onRegisterTrip = {})
                            }
                            composable(route = "TravelForm") {
                                TravelForm(null)
                            }
                            composable(route = "TravelForm/{id}", arguments = listOf(navArgument("id"){type=NavType.IntType})) { backStackEntry ->
                                val id = backStackEntry.arguments?.getInt("id")
                                TravelForm(id)
                            }
                        }
                    }
                }
            }
        }
    }
}







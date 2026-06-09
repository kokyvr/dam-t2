package com.example.networkingapisrest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.networkingapisrest.data.repository.AppDatabase
import com.example.networkingapisrest.data.repository.LoginViewModel
import com.example.networkingapisrest.data.repository.RegisterViewModel
import com.example.networkingapisrest.data.room.User
import com.example.networkingapisrest.ui.screen.LoginScreen
import com.example.networkingapisrest.ui.screen.RegisterScreen
import com.example.networkingapisrest.ui.screen.StartScreen
import com.example.networkingapisrest.ui.screen.UserScreen
import com.example.networkingapisrest.ui.theme.NetworkingApisRestTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(this)
        val registerViewModel = RegisterViewModel(
            db.userDao()
        )
        val loginViewModel = LoginViewModel(
            db.userDao()
        )
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "inicio"
            ) {

                composable("inicio") {
                    StartScreen(navController)
                }

                composable("login") {
                    LoginScreen(navController, loginViewModel)
                }

                composable("register") {
                    RegisterScreen(registerViewModel, navController)
                }

                composable("home") {
                    UserScreen()
                }

            }
        }
    }
}


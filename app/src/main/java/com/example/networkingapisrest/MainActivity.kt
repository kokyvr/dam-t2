package com.example.networkingapisrest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.networkingapisrest.data.repository.AppDatabase
import com.example.networkingapisrest.data.repository.LoginViewModel
import com.example.networkingapisrest.data.repository.RegisterViewModel
import com.example.networkingapisrest.ui.screen.LoginScreen
import com.example.networkingapisrest.ui.screen.RegisterScreen
import com.example.networkingapisrest.ui.screen.UserScreen
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.example.networkingapisrest.data.repository.LocalUserViewModel
import com.example.networkingapisrest.ui.screen.LocalUsersScreen
import com.example.networkingapisrest.ui.theme.NetworkingApisRestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = AppDatabase.getDatabase(this)
        val registerViewModel = RegisterViewModel(db.userDao())
        val loginViewModel = LoginViewModel(db.userDao())
        val localUserViewModel = LocalUserViewModel(db.userDao())

        setContent {
            NetworkingApisRestTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "login",
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable(
                        route = "login",
                        enterTransition = {
                            if (initialState.destination.route == "register") {
                                slideInHorizontally(
                                    initialOffsetX = { -it },
                                    animationSpec = tween(300)
                                )
                            } else EnterTransition.None
                        },
                        exitTransition = {
                            if (targetState.destination.route == "register") {
                                slideOutHorizontally(
                                    targetOffsetX = { it },
                                    animationSpec = tween(300)
                                )
                            } else ExitTransition.None
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(300)
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(300)
                            )
                        }
                    ) {
                        LoginScreen(navController, loginViewModel)
                    }

                    composable(
                        route = "register",
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(300)
                            )
                        },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(300)
                            )
                        }
                    ) {
                        RegisterScreen(registerViewModel, navController)
                    }

                    composable("home") {
                        UserScreen(
                            navController = navController,
                            localUserViewModel = localUserViewModel,
                            onNavigateToLocales = { navController.navigate("locales") }
                        )
                    }

                    composable("locales") {
                        LocalUsersScreen(
                            viewModel = localUserViewModel,
                            onNavigateToHome = { navController.navigate("home") },
                            onCerrarSesion = {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
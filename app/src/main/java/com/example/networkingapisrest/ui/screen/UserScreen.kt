package com.example.networkingapisrest.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.networkingapisrest.data.remote.RetrofitClient
import com.example.networkingapisrest.data.repository.LocalUserViewModel
import com.example.networkingapisrest.data.repository.UserRepository
import com.example.networkingapisrest.data.repository.UserRepositoryImpl
import com.example.networkingapisrest.ui.components.UserCard
import com.example.networkingapisrest.viewmodel.UserViewModel
import com.example.networkingapisrest.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch

val FondoPrincipal = Color(0xFF252925)
val FondoTarjeta = Color(0xFF2F342F)
val VerdeUI = Color(0xFF7DD400)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navController: NavController,
    localUserViewModel: LocalUserViewModel,
    onNavigateToLocales: () -> Unit = {}
) {

    val repository: UserRepository = UserRepositoryImpl(
        apiService = RetrofitClient.apiService
    )

    val viewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(repository)
    )

    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var searchText by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val filteredUsers = users.filter { user ->
        user.name.contains(searchText, ignoreCase = true) ||
                user.email.contains(searchText, ignoreCase = true) ||
                user.username.contains(searchText, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = FondoTarjeta,
                modifier = Modifier.fillMaxHeight()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                HorizontalDivider(color = VerdeUI.copy(alpha = 0.3f))

                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = VerdeUI
                        )
                    },
                    label = {
                        Text(
                            text = "Cerrar sesión",
                            color = VerdeUI,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) {
        Scaffold(
            containerColor = FondoPrincipal,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Usuarios desde API",
                            fontWeight = FontWeight.Bold,
                            color = VerdeUI
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = VerdeUI
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = FondoPrincipal
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = FondoTarjeta,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null
                            )
                        },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VerdeUI,
                            selectedTextColor = VerdeUI,
                            indicatorColor = FondoPrincipal,
                            unselectedIconColor = VerdeUI.copy(alpha = 0.5f),
                            unselectedTextColor = VerdeUI.copy(alpha = 0.5f)
                        )
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToLocales,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Storage,
                                contentDescription = null
                            )
                        },
                        label = { Text("Locales") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VerdeUI,
                            selectedTextColor = VerdeUI,
                            indicatorColor = FondoPrincipal,
                            unselectedIconColor = VerdeUI.copy(alpha = 0.5f),
                            unselectedTextColor = VerdeUI.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FondoPrincipal)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            ) {

                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Buscar usuario", color = VerdeUI.copy(alpha = 0.5f))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = VerdeUI
                        )
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VerdeUI,
                        unfocusedBorderColor = VerdeUI.copy(alpha = 0.5f),
                        focusedTextColor = VerdeUI,
                        unfocusedTextColor = VerdeUI,
                        cursorColor = VerdeUI
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = VerdeUI)
                        }
                    }

                    errorMessage != null -> {
                        Text(
                            text = errorMessage ?: "",
                            color = VerdeUI,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filteredUsers) { user ->
                                UserCard(
                                    user = user,
                                    onDetailClick = { }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
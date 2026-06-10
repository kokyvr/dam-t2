package com.example.networkingapisrest.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.networkingapisrest.data.repository.LocalUserViewModel
import com.example.networkingapisrest.data.room.User
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalUsersScreen(
    viewModel: LocalUserViewModel,
    onNavigateToHome: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    val usuarios by viewModel.usuarios.collectAsState()
    var usuarioAEliminar by remember { mutableStateOf<User?>(null) }
    var usuarioAEditar by remember { mutableStateOf<User?>(null) }
    var mostrarDialogoNuevo by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val usuariosFiltrados = usuarios.filter { user ->
        user.usuario.contains(searchText, ignoreCase = true) ||
                user.correo.contains(searchText, ignoreCase = true)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    usuarioAEliminar?.let { user ->
        AlertDialog(
            onDismissRequest = { usuarioAEliminar = null },
            containerColor = FondoOscuro,
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color.Red, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Eliminar usuario",
                        color = VerdeAcento,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            text = {
                Text(
                    "¿Estás seguro de que deseas eliminar este usuario?\nEsta acción no se puede deshacer.",
                    color = VerdeAcento.copy(alpha = 0.80f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { usuarioAEliminar = null },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VerdeAcento,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancelar", fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            viewModel.eliminar(user)
                            usuarioAEliminar = null
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Eliminar", fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {}
        )
    }

    usuarioAEditar?.let { user ->
        DialogoUsuario(
            titulo = "Editar usuario",
            usuarioInicial = user.usuario,
            correoInicial = user.correo,
            passwordInicial = user.password,
            onConfirmar = { nuevoUsuario, nuevoCorreo, nuevaPassword ->
                viewModel.actualizar(
                    user.copy(
                        usuario = nuevoUsuario,
                        correo = nuevoCorreo,
                        password = nuevaPassword
                    )
                )
                usuarioAEditar = null
            },
            onCancelar = { usuarioAEditar = null }
        )
    }

    if (mostrarDialogoNuevo) {
        DialogoUsuario(
            titulo = "Nuevo usuario",
            onConfirmar = { nuevoUsuario, nuevoCorreo, nuevaPassword ->
                viewModel.insertar(
                    User(usuario = nuevoUsuario, correo = nuevoCorreo, password = nuevaPassword)
                )
                mostrarDialogoNuevo = false
            },
            onCancelar = { mostrarDialogoNuevo = false }
        )
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
                HorizontalDivider(color = VerdeAcento.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = VerdeAcento
                        )
                    },
                    label = {
                        Text(
                            "Cerrar sesión",
                            color = VerdeAcento,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onCerrarSesion()
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
            containerColor = FondoOscuro,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Usuarios locales",
                            color = VerdeAcento,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = VerdeAcento
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = FondoOscuro
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = FondoTarjeta,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        selected = false,
                        onClick = onNavigateToHome,
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = null)
                        },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VerdeAcento,
                            selectedTextColor = VerdeAcento,
                            indicatorColor = FondoOscuro,
                            unselectedIconColor = VerdeAcento.copy(alpha = 0.5f),
                            unselectedTextColor = VerdeAcento.copy(alpha = 0.5f)
                        )
                    )
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(Icons.Default.Storage, contentDescription = null)
                        },
                        label = { Text("Locales") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VerdeAcento,
                            selectedTextColor = VerdeAcento,
                            indicatorColor = FondoOscuro,
                            unselectedIconColor = VerdeAcento.copy(alpha = 0.5f),
                            unselectedTextColor = VerdeAcento.copy(alpha = 0.5f)
                        )
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { mostrarDialogoNuevo = true },
                    containerColor = VerdeAcento,
                    contentColor = FondoOscuro,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Nuevo usuario")
                }
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FondoOscuro)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Buscar usuario", color = VerdeAcento.copy(alpha = 0.5f))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = VerdeAcento
                        )
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VerdeAcento,
                        unfocusedBorderColor = VerdeAcento.copy(alpha = 0.5f),
                        focusedTextColor = VerdeAcento,
                        unfocusedTextColor = VerdeAcento,
                        cursorColor = VerdeAcento
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (usuariosFiltrados.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (usuarios.isEmpty()) "No hay usuarios registrados"
                            else "No se encontraron resultados",
                            color = VerdeAcento.copy(alpha = 0.5f),
                            fontSize = 16.sp
                        )
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(usuariosFiltrados) { user ->
                            UserLocalCard(
                                user = user,
                                onEditar = { usuarioAEditar = user },
                                onEliminar = { usuarioAEliminar = user }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserLocalCard(
    user: User,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    var expandedMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = FondoTarjeta)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .background(VerdeAcento, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = FondoOscuro,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.usuario,
                    color = VerdeAcento,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = user.correo,
                    color = VerdeAcento.copy(alpha = 0.72f),
                    fontSize = 13.sp
                )
            }

            Box {
                IconButton(onClick = { expandedMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones",
                        tint = VerdeAcento
                    )
                }

                DropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false },
                    containerColor = FondoTarjeta
                ) {
                    DropdownMenuItem(
                        text = {
                            Text("Editar", color = VerdeAcento)
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = VerdeAcento
                            )
                        },
                        onClick = {
                            expandedMenu = false
                            onEditar()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Eliminar", color = Color.Red.copy(alpha = 0.8f))
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red.copy(alpha = 0.8f)
                            )
                        },
                        onClick = {
                            expandedMenu = false
                            onEliminar()
                        }
                    )
                }
            }
        }
    }
}
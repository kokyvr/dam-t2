package com.example.networkingapisrest.ui.screen
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.networkingapisrest.data.repository.AppDatabase
import com.example.networkingapisrest.data.repository.RegisterViewModel
import com.example.networkingapisrest.data.repository.UserDAO
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen( viewModel: RegisterViewModel,navController: NavController) {

    var usuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(viewModel.mensaje) {

        viewModel.mensaje?.let {

            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()

            viewModel.limpiarMensaje()
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Crear cuenta")
                } ,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(6.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Regístrate",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = "Completa tus datos para crear una cuenta",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar contraseña") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {

                            var valUser = validateIfNotNull(usuario,"Usuario");
                            var valPassword = validateIfNotNull(password,"Password");
                            var valEmail = validateIfNotNull(password,"Password");
                            var valConfirmPassword = validateIfNotNull(password,"Confirmar Password");
                            listOf(
                                valUser,
                                valEmail,
                                valPassword,
                                valConfirmPassword
                            ).firstOrNull { it.isNotEmpty() }?.let { error ->

                                Toast.makeText(
                                    context,
                                    error,
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@Button
                            }



                            if (password != confirmPassword) {
                                Toast.makeText(
                                    context,
                                    "Las contraseñas no coinciden",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@Button
                            }
                            viewModel.registrar(
                                usuario,
                                correo,
                                password
                            )

                            navController.navigate("login")

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Text("Registrarme")
                    }
                }
            }
        }
    }
}

fun validateIfNotNull(
    value: String,
    nameValue: String
): String {

    return if (value.isBlank()) {
        "El campo $nameValue es requerido."
    } else {
        ""
    }
}
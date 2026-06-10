package com.example.networkingapisrest.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.networkingapisrest.data.repository.RegisterViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation

val FondoOscuro = Color(0xFF252925)
val VerdeAcento = Color(0xFF7DD400)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavController) {

    var usuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(viewModel.mensaje) {
        viewModel.mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = VerdeAcento
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FondoOscuro
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoOscuro)
                .padding(paddingValues)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .background(VerdeAcento, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = null,
                    tint = FondoOscuro,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Crear cuenta",
                color = VerdeAcento,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Completa tus datos para crear una cuenta",
                color = VerdeAcento.copy(alpha = 0.72f),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = VerdeAcento)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeAcento,
                    unfocusedBorderColor = VerdeAcento,
                    focusedLabelColor = VerdeAcento,
                    unfocusedLabelColor = VerdeAcento,
                    focusedTextColor = VerdeAcento,
                    unfocusedTextColor = VerdeAcento,
                    cursorColor = VerdeAcento
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null, tint = VerdeAcento)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeAcento,
                    unfocusedBorderColor = VerdeAcento,
                    focusedLabelColor = VerdeAcento,
                    unfocusedLabelColor = VerdeAcento,
                    focusedTextColor = VerdeAcento,
                    unfocusedTextColor = VerdeAcento,
                    cursorColor = VerdeAcento
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = VerdeAcento)
                },
                trailingIcon = {
                    if (password.isNotEmpty()) {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = VerdeAcento
                            )
                        }
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeAcento,
                    unfocusedBorderColor = VerdeAcento,
                    focusedLabelColor = VerdeAcento,
                    unfocusedLabelColor = VerdeAcento,
                    focusedTextColor = VerdeAcento,
                    unfocusedTextColor = VerdeAcento,
                    cursorColor = VerdeAcento
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = VerdeAcento)
                },
                trailingIcon = {
                    if (confirmPassword.isNotEmpty()) {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null,
                                tint = VerdeAcento
                            )
                        }
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdeAcento,
                    unfocusedBorderColor = VerdeAcento,
                    focusedLabelColor = VerdeAcento,
                    unfocusedLabelColor = VerdeAcento,
                    focusedTextColor = VerdeAcento,
                    unfocusedTextColor = VerdeAcento,
                    cursorColor = VerdeAcento
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    val valUser = validateIfNotNull(usuario, "Usuario")
                    val valEmail = validateIfNotNull(correo, "Correo electrónico")
                    val valPassword = validateIfNotNull(password, "Contraseña")
                    val valConfirmPassword = validateIfNotNull(confirmPassword, "Confirmar contraseña")

                    listOf(valUser, valEmail, valPassword, valConfirmPassword)
                        .firstOrNull { it.isNotEmpty() }?.let { error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                    if (password != confirmPassword) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModel.registrar(usuario, correo, password)
                    navController.navigate("login")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeAcento,
                    contentColor = FondoOscuro
                )
            ) {
                Text("Registrarme", fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun validateIfNotNull(value: String, nameValue: String): String {
    return if (value.isBlank()) "El campo $nameValue es requerido." else ""
}
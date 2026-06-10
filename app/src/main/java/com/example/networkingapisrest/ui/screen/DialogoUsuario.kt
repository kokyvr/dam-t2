package com.example.networkingapisrest.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoUsuario(
    titulo: String,
    usuarioInicial: String = "",
    correoInicial: String = "",
    passwordInicial: String = "",
    onConfirmar: (String, String, String) -> Unit,
    onCancelar: () -> Unit
) {
    var usuario by remember { mutableStateOf(usuarioInicial) }
    var correo by remember { mutableStateOf(correoInicial) }
    var password by remember { mutableStateOf(passwordInicial) }
    var passwordVisible by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = FondoOscuro,
        title = {
            Text(titulo, color = VerdeAcento, fontWeight = FontWeight.Bold)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = VerdeAcento)
                    },
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
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = VerdeAcento)
                    },
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
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = VerdeAcento)
                    },
                    trailingIcon = {
                        if (password.isNotEmpty()) {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Default.VisibilityOff
                                    else
                                        Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = VerdeAcento
                                )
                            }
                        }
                    },
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
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (usuario.isNotBlank() && correo.isNotBlank() && password.isNotBlank()) {
                    onConfirmar(usuario, correo, password)
                }
            }) {
                Text("Guardar", color = VerdeAcento, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar", color = VerdeAcento.copy(alpha = 0.7f))
            }
        }
    )
}
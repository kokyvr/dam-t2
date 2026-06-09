package com.example.networkingapisrest.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userDAO: UserDAO
) : ViewModel() {

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso = _loginExitoso.asStateFlow()

    private val _mensajeError = MutableStateFlow("")
    val mensajeError = _mensajeError.asStateFlow()

    fun iniciarSesion(
        usuario: String,
        password: String
    ) {
        viewModelScope.launch {
            val usuarioEncontrado =
                userDAO.login(usuario, password)
            if (usuarioEncontrado != null) {
                _loginExitoso.value = true
            } else {
                _mensajeError.value =
                    "Usuario o contraseña incorrectos"
            }
        }
    }
}
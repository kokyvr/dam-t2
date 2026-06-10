package com.example.networkingapisrest.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LoginViewModel(
    private val userDAO: UserDAO
) : ViewModel() {

    private val _loginExitoso = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val loginExitoso = _loginExitoso.asSharedFlow()

    private val _mensajeError = MutableStateFlow("")
    val mensajeError = _mensajeError.asStateFlow()

    fun iniciarSesion(usuario: String, password: String) {
        viewModelScope.launch {
            val user = userDAO.login(usuario, password)
            if (user != null) {
                _loginExitoso.emit(Unit)
            } else {
                _mensajeError.value = "Usuario o contraseña incorrectos"
            }
        }
    }

    fun resetearEstado() {
        _mensajeError.value = ""
    }

}
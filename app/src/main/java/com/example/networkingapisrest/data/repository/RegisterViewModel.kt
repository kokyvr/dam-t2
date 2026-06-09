package com.example.networkingapisrest.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkingapisrest.data.room.User
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDao: UserDAO
) : ViewModel(){
    var mensaje by mutableStateOf<String?>(null)
        private set

    fun registrar(
        usuario: String,
        correo: String,
        password: String
    ) {

        viewModelScope.launch {

            val usuarioExistente =
                userDao.obtenerPorUsuario(usuario)

            val emailExistente =
                userDao.obtenerPorUsuario(usuario)

            if (usuarioExistente != null) {
                mensaje = "El usuario ya existe"
                return@launch
            }
            if (emailExistente != null) {
                mensaje = "El usuario ya existe"
                return@launch
            }
            userDao.insertarUsuario(
                User(
                    usuario = usuario,
                    correo = correo,
                    password = password
                )
            )

            mensaje = "Usuario registrado correctamente"


        }
    }

    fun limpiarMensaje() {
        mensaje = null
    }

}
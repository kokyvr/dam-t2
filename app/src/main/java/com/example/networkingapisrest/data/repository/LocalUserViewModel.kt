package com.example.networkingapisrest.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkingapisrest.data.room.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LocalUserViewModel(
    private val userDAO: UserDAO
) : ViewModel() {

    val usuarios = userDAO.obtenerTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun eliminar(user: User) {
        viewModelScope.launch {
            userDAO.eliminar(user)
        }
    }

    fun actualizar(user: User) {
        viewModelScope.launch {
            userDAO.actualizar(user)
        }
    }

    fun insertar(user: User) {
        viewModelScope.launch {
            userDAO.insertarUsuario(user)
        }
    }
}
package com.example.networkingapisrest.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocalUserViewModelFactory(
    private val userDAO: UserDAO
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalUserViewModel::class.java)) {
            return LocalUserViewModel(userDAO) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
package com.example.networkingapisrest.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.networkingapisrest.data.room.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insertarUsuario(user: User)

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario LIMIT 1")
    suspend fun obtenerPorUsuario(usuario: String): User?

    @Query("SELECT * FROM usuarios WHERE correo = :email LIMIT 1")
    suspend fun obtenerEmail(email: String): User?

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND password = :password LIMIT 1")
    suspend fun login(usuario: String, password: String): User?

}
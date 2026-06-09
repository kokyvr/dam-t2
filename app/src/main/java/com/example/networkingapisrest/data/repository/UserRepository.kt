package com.example.networkingapisrest.data.repository
import com.example.networkingapisrest.data.model.User

interface UserRepository {

    suspend fun getUsers(): List<User>
}

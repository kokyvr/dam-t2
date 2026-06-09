package com.example.networkingapisrest.data.repository
import com.example.networkingapisrest.data.model.User
import com.example.networkingapisrest.data.remote.ApiService

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}

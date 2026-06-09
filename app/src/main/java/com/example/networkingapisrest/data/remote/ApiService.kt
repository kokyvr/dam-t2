package com.example.networkingapisrest.data.remote
import com.example.networkingapisrest.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}

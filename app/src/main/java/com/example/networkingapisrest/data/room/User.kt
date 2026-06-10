package com.example.networkingapisrest.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class User(  @PrimaryKey(autoGenerate = true)
                  val id: Int = 0,

                  val usuario: String,
                  val correo: String,
                  val password: String)
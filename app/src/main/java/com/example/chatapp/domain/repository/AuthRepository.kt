package com.example.chatapp.domain.repository

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun register(email: String, password: String, username: String): Result<Unit>

    fun getCurrentUserId(): String?
}
package com.example.chatapp.domain.repository

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<Unit>

    fun getCurrentUserId(): String?
}
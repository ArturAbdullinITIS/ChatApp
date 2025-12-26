package com.example.chatapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface PresenceRepository {

    suspend fun setOnline()
    suspend fun setOffline()
    fun observePresence(userId: String?): Flow<Boolean>
}
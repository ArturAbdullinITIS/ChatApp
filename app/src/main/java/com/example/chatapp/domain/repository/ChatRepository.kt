package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun getChats(): Flow<List<Chat>>

    fun getMessages(receiverId: String): Flow<List<Message>>

    suspend fun sendMessage(receiverId: String, text: String): Result<Unit>

    fun getPresence(receiverId: String): Flow<Boolean>
}
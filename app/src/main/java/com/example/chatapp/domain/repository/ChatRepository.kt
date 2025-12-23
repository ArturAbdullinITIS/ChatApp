package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun getMessageFlow(): Flow<List<Message>>

    suspend fun sendMessage(chatId: String, text: Message, senderId: String)
}
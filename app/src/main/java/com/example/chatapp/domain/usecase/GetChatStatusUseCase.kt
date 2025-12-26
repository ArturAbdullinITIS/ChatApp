package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatStatusUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(receiverId: String): Flow<Boolean> {
        return chatRepository.getPresence(receiverId)
    }
}
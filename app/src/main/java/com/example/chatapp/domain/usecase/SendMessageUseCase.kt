package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    suspend operator fun invoke(receiverId: String, messageText: String): Result<Unit> {
        return chatRepository.sendMessage(receiverId, messageText)
    }
}
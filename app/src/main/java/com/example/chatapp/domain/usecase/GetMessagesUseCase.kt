package com.example.chatapp.domain.usecase

import com.example.chatapp.data.firebase.chat.FirebaseChatRepository
import com.example.chatapp.domain.repository.ChatRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(receiverId: String) =
        chatRepository.getMessages(receiverId)
}
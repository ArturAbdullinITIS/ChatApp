package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatListUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {

    operator fun invoke(): Flow<List<Chat>> = chatRepository.getChats()
}
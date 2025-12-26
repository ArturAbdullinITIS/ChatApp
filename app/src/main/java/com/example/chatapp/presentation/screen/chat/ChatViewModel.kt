package com.example.chatapp.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.usecase.GetCurrentUserUseCase
import com.example.chatapp.domain.usecase.GetMessagesUseCase
import com.example.chatapp.domain.usecase.ObserveUserStatusUseCase
import com.example.chatapp.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val observeUserStatusUseCase: ObserveUserStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChatScreenState())
    val state = _state.asStateFlow()

    private var receiverId: String? = null
    private var chatName: String? = null

    val currentUserId: String?
        get() = getCurrentUserUseCase()


    fun init(receiverId: String, chatName: String) {
        if (this.receiverId != null && this.chatName != null) return


        this.receiverId = receiverId
        this.chatName = chatName
        viewModelScope.launch {
            getMessagesUseCase(receiverId)
                .collect { messages ->
                    _state.update { it.copy(messages = messages, isLoading = false) }
                }
        }
        _state.update {
            it.copy(title = chatName)
        }

        viewModelScope.launch {
            viewModelScope.launch {
                observeUserStatusUseCase(receiverId)
                    .collect { status ->
                        _state.update { state ->
                            state.copy(
                                status = status
                            )
                        }
                    }
            }
        }
    }

    fun processCommand(command: ChatCommand) {
        when (command) {
            is ChatCommand.InputMessage -> {
                _state.update { state ->
                    state.copy(
                        message = command.message
                    )
                }
            }

            ChatCommand.SendMessage -> {
                val message = _state.value.message.trim()
                val receiverId = receiverId
                _state.update { it.copy(message = "") }
                viewModelScope.launch {
                    if (receiverId != null) {
                        val result =
                            sendMessageUseCase(receiverId = receiverId, messageText = message)
                        if (!result.isSuccess) {
                            _state.update { state ->
                                state.copy(
                                    error = "Failed to send message"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed interface ChatCommand {
    data class InputMessage(val message: String) : ChatCommand
    data object SendMessage : ChatCommand
}

data class ChatScreenState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = true,
    val message: String = "",
    val error: String? = null,
    val title: String = "",
    val status: Boolean = false
) {
    val sendButtonEnabled: Boolean
        get() = message.isNotBlank()

}
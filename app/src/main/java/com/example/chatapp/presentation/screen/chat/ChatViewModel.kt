package com.example.chatapp.presentation.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.usecase.GetMessagesUseCase
import com.example.chatapp.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ChatScreenState())
    val state = _state.asStateFlow()

    private var chatId: String? = null

    fun init(chatId: String) {
        if (this.chatId != null) return

        this.chatId = chatId
        viewModelScope.launch {
            getMessagesUseCase(chatId)
                .collect { messages ->
                    _state.update { it.copy(messages = messages, isLoading = false) }
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
                viewModelScope.launch {
                    val message = _state.value.message
                    val receiverId = chatId
                    if (receiverId != null) {
                        val result = sendMessageUseCase(receiverId = receiverId, messageText = message)
                        if(result.isSuccess) {
                            _state.update { state ->
                                state.copy(
                                    message = ""
                                )
                            }
                        } else {
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
    val error: String? = null
) {
    val sendButtonEnabled: Boolean
        get() = message.isNotBlank()
}
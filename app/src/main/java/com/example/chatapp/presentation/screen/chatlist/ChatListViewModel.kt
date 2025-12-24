package com.example.chatapp.presentation.screen.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.usecase.GetChatListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ChatListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getChatListUseCase().collect { chats ->
                _state.update {state ->
                    state.copy(
                        chats = chats,
                        isLoading = false
                    )
                }
            }
        }
    }
}



data class ChatListState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false
)
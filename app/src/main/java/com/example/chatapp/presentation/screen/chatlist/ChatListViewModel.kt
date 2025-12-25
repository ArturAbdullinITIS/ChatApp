package com.example.chatapp.presentation.screen.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.usecase.GetChatListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
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
    @OptIn(FlowPreview::class)
    val filteredChats = _state
        .debounce(300)
        .map { state ->
            state.chats.filter { chat ->
                val query = state.query.trim()
                if (query.isEmpty()) true
                else {
                    chat.name.contains(query, ignoreCase = true) ||
                            chat.lastMessage.contains(query, ignoreCase = true)
                }
            }
        }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun processCommand(command: ChatListCommand) {
        when(command) {
            is ChatListCommand.SearchQueryChanged -> {
                _state.update { state ->
                    state.copy(
                        query = command.query
                    )
                }
            }
        }
    }
}


sealed interface ChatListCommand {
    data class SearchQueryChanged(val query: String): ChatListCommand
}

data class ChatListState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false,
    val query: String = ""
)
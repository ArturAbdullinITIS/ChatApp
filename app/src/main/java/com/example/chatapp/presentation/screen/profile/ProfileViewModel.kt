package com.example.chatapp.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.usecase.GetCurrentUserDisplayName
import com.example.chatapp.domain.usecase.GetCurrentUserUseCase
import com.example.chatapp.domain.usecase.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getCurrentUserDisplayName: GetCurrentUserDisplayName
): ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            val displayName = getCurrentUserDisplayName() ?: "Guest"
            _state.update {
                it.copy(username = displayName)
            }
        }
    }

    fun processCommand(command: ProfileCommand) {
        when (command) {
            is ProfileCommand.LogOut -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(isLoggedIn = false)
                    }
                    logOutUseCase()
                }
            }
        }
    }
}




sealed interface ProfileCommand {
    object LogOut: ProfileCommand
}


data class ProfileState(
    val username: String = "",
    val email: String = "",
    val isLoggedIn: Boolean = true
)
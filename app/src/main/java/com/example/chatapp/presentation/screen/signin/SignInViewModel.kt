package com.example.chatapp.presentation.screen.signin

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(

): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun processCommand(command: SignInCommand) {
        when(command) {
            is SignInCommand.InputEmail -> {
                _state.update { state ->
                    state.copy(
                        email = command.email,
                        emailError = ""
                    )
                }
            }
            is SignInCommand.InputPassword -> {
                _state.update { state ->
                    state.copy(
                        password = command.password,
                        passwordError = ""
                    )
                }
            }

            SignInCommand.ChangePasswordVisibility -> {
                _state.update { state ->
                    state.copy(
                        isPasswordVisible = !state.isPasswordVisible
                    )
                }
            }

            is SignInCommand.InputUsername -> {
                _state.update { state ->
                    state.copy(
                        username = command.username,
                        usernameError = ""
                    )
                }
            }
        }
    }
}




sealed interface SignInCommand {
    data class InputEmail(val email: String): SignInCommand
    data class InputPassword(val password: String): SignInCommand
    data class InputUsername(val username: String): SignInCommand
    data object ChangePasswordVisibility: SignInCommand
}

data class SignInState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val usernameError: String = "",
    val isPasswordVisible: Boolean = false,
    val isSuccess: Boolean = false
)
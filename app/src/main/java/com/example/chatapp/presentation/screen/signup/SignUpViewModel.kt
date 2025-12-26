package com.example.chatapp.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.usecase.SetOnlineUseCase
import com.example.chatapp.domain.usecase.SignUpUseCase
import com.example.chatapp.presentation.util.ErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val errorParser: ErrorParser,
    private val setOnlineUseCase: SetOnlineUseCase
): ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()


    fun processCommand(command: SignUpCommand) {
        when(command) {
            SignUpCommand.ChangePasswordVisibility -> {
                _state.update { state ->
                    state.copy(
                        isPasswordVisible = !state.isPasswordVisible
                    )
                }
            }
            is SignUpCommand.InputEmail -> {
                _state.update { state ->
                    state.copy(
                        email = command.email,
                        emailError = ""
                    )
                }
            }
            is SignUpCommand.InputPassword -> {
                _state.update { state ->
                    state.copy(
                        password = command.password,
                        passwordError = ""
                    )
                }
            }
            is SignUpCommand.InputUsername -> {
                _state.update { state ->
                    state.copy(
                        username = command.username,
                        usernameError = ""
                    )
                }
            }
            SignUpCommand.Register -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val currentEmail = _state.value.email
            val currentPassword = _state.value.password
            val currentUsername = _state.value.username

            val result = signUpUseCase(currentEmail, currentPassword, currentUsername)
            if (result.isDataValid && result.emailError == null &&
                result.passwordError == null && result.usernameError == null) {
                setOnlineUseCase()
            }
            _state.update { state ->
                state.copy(
                    isLoading = false,
                    isSuccess = result.isDataValid,
                    emailError = errorParser.parseErrorMessage(result.emailError),
                    passwordError = errorParser.parseErrorMessage(result.passwordError),
                    usernameError = errorParser.parseErrorMessage(result.usernameError)
                )
            }
        }
    }
}



sealed interface SignUpCommand {
    data class InputUsername(val username: String): SignUpCommand
    data class InputEmail(val email: String): SignUpCommand
    data class InputPassword(val password: String): SignUpCommand
    data object ChangePasswordVisibility: SignUpCommand
    data object Register: SignUpCommand
}

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val usernameError: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isPasswordVisible: Boolean = false
)
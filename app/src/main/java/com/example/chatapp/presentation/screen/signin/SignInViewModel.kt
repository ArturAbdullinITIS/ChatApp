package com.example.chatapp.presentation.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.usecase.LoginUseCase
import com.example.chatapp.presentation.util.ErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val errorParser: ErrorParser
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

            SignInCommand.Login -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val email = _state.value.email
            val password = _state.value.password
            val result = loginUseCase(email, password)

            _state.update { state ->
                state.copy(
                    isLoading = false,
                    isSuccess = result.isDataValid,
                    emailError = errorParser.parseErrorMessage(result.emailError),
                    passwordError = errorParser.parseErrorMessage(result.passwordError)
                )
            }
        }
    }
}




sealed interface SignInCommand {
    data class InputEmail(val email: String): SignInCommand
    data class InputPassword(val password: String): SignInCommand
    data object ChangePasswordVisibility: SignInCommand
    data object Login: SignInCommand
}

data class SignInState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = "",
    val passwordError: String? = "",
    val isPasswordVisible: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false
)
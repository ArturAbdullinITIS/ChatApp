package com.example.chatapp.domain.usecase

import android.util.Patterns
import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        username: String
    ): SignUpValidationResult{
        val emailError = when {
            email.isBlank() -> ValidationError.EMAIL_BLANK
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationError.INVALID_EMAIL_FORMAT
            else -> null
        }
        val passwordError = when {
            password.isBlank() -> ValidationError.PASSWORD_BLANK
            password.length < 6 -> ValidationError.PASSWORD_TOO_SHORT
            else -> null
        }
        val usernameError = when {
            username.isBlank() -> ValidationError.USERNAME_BLANK
            username.length < 3 -> ValidationError.USERNAME_TOO_SHORT
            username.length > 20 -> ValidationError.USERNAME_TOO_LONG
            else -> null
        }
        return if (emailError == null && passwordError == null && usernameError == null) {
            try {
                authRepository.register(email, password, username)
                SignUpValidationResult(
                    isDataValid = true
                )
            } catch (e: Exception) {
                SignUpValidationResult(
                    isDataValid = false,
                    emailError = ValidationError.FIREBASE_ERROR
                )
            }
        }
        else {
            SignUpValidationResult(
                emailError = emailError,
                passwordError = passwordError,
                usernameError = usernameError,
                isDataValid = false
            )
        }
    }
}
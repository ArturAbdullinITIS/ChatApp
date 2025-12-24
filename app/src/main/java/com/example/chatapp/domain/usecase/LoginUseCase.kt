package com.example.chatapp.domain.usecase

import android.R.attr.password
import android.util.Patterns
import com.example.chatapp.R
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.presentation.utils.ResourceProvider
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(email: String, password: String): ValidationResult {
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
        return if (emailError == null && passwordError == null) {
            try {
                authRepository.login(email, password)
                ValidationResult(
                    isDataValid = true
                )
            } catch (e: Exception) {
                ValidationResult(
                    isDataValid = false,
                    emailError = ValidationError.FIREBASE_ERROR
                )
            }
        }
        else {
            ValidationResult(
                emailError = emailError,
                passwordError = passwordError,
                isDataValid = false
            )
        }
    }
}
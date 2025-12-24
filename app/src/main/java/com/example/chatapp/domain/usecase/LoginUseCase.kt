package com.example.chatapp.domain.usecase

import android.util.Patterns
import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): SignInValidationResult {

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
                val result = authRepository.login(email, password)

                if (result.isSuccess) {
                    SignInValidationResult(isDataValid = true)
                } else {
                    SignInValidationResult(
                        isDataValid = false,
                        emailError = ValidationError.FIREBASE_ERROR
                    )
                }
            } catch (e: Exception) {
                SignInValidationResult(
                    isDataValid = false,
                    emailError = ValidationError.FIREBASE_ERROR
                )
            }
        } else {
            SignInValidationResult(
                emailError = emailError,
                passwordError = passwordError,
                isDataValid = false
            )
        }
    }
}

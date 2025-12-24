package com.example.chatapp.domain.usecase


data class SignInValidationResult(
    val emailError: ValidationError? = null,
    val passwordError: ValidationError? = null,
    val isDataValid: Boolean = false
)

enum class ValidationError {
    EMAIL_BLANK,
    INVALID_EMAIL_FORMAT,
    PASSWORD_BLANK,
    PASSWORD_TOO_SHORT,
    USERNAME_BLANK,
    USERNAME_TOO_SHORT,
    USERNAME_TOO_LONG,
    FIREBASE_ERROR
}


data class SignUpValidationResult(
    val emailError: ValidationError? = null,
    val passwordError: ValidationError? = null,
    val usernameError: ValidationError? = null,
    val isDataValid: Boolean = false
)

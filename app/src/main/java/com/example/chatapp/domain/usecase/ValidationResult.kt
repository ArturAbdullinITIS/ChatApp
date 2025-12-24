package com.example.chatapp.domain.usecase


data class ValidationResult(
    val emailError: ValidationError? = null,
    val passwordError: ValidationError? = null,
    val isDataValid: Boolean = false
)

enum class ValidationError {
    EMAIL_BLANK,
    INVALID_EMAIL_FORMAT,
    PASSWORD_BLANK,
    PASSWORD_TOO_SHORT,
    FIREBASE_ERROR
}
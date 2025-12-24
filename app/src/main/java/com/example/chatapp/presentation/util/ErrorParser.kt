package com.example.chatapp.presentation.util

import com.example.chatapp.R
import com.example.chatapp.domain.usecase.ValidationError



class ErrorParser(
    private val resourceProvider: ResourceProvider
) {
    fun parseErrorMessage(error: ValidationError?): String {
        return when(error) {
            ValidationError.EMAIL_BLANK -> resourceProvider.getString(R.string.email_cannot_be_blank_error)
            ValidationError.INVALID_EMAIL_FORMAT -> resourceProvider.getString(R.string.invalid_email_format_error)
            ValidationError.PASSWORD_BLANK -> resourceProvider.getString(R.string.password_cannot_be_blank_error)
            ValidationError.PASSWORD_TOO_SHORT -> resourceProvider.getString(R.string.password_must_be_at_least_6_characters_error)
            ValidationError.USERNAME_BLANK -> resourceProvider.getString(R.string.username_cannot_be_blank_error)
            ValidationError.USERNAME_TOO_SHORT -> resourceProvider.getString(R.string.username_must_be_at_least_3_characters_error)
            ValidationError.USERNAME_TOO_LONG -> resourceProvider.getString(R.string.username_must_be_at_most_20_characters_error)
            ValidationError.FIREBASE_ERROR -> resourceProvider.getString(R.string.authentication_error_occurred_error)
            null -> ""
        }
    }
}

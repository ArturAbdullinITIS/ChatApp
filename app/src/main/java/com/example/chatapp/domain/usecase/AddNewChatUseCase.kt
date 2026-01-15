package com.example.chatapp.domain.usecase

import android.util.Patterns
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.ChatRepository
import javax.inject.Inject

class AddNewChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): NewChatValidationResult {
        val receiverId = authRepository.getUserIdByEmail(email)
        val senderId = authRepository.getCurrentUserId()

        val emailError = when {
            senderId == receiverId -> ValidationError.CANNOT_CHAT_WITH_YOURSELF
            email.isBlank() -> ValidationError.EMAIL_BLANK
            receiverId.isBlank() -> ValidationError.NO_SUCH_USER
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationError.INVALID_EMAIL_FORMAT
            else -> null
        }
        return if(emailError == null) {
            val result = chatRepository.createChat(receiverId)
            if(result.isSuccess) {
                NewChatValidationResult(isDataValid = true)
            } else {
                NewChatValidationResult(
                    isDataValid = false,
                    emailError = ValidationError.FIREBASE_ERROR
                )
            }
        } else {
            NewChatValidationResult(
                isDataValid = false,
                emailError = emailError,
            )
        }
    }
}
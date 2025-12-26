package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.getCurrentUserId() != null
    }
}
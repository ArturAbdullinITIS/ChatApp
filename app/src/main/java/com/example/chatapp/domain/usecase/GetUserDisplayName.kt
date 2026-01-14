package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserDisplayName @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String? {
        return authRepository.getCurrentUserDisplayName()
    }
}
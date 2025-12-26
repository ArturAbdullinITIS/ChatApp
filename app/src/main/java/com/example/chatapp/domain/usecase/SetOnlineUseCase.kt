package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.PresenceRepository
import javax.inject.Inject

class SetOnlineUseCase @Inject constructor(
    private val presenceRepository: PresenceRepository
) {
    suspend operator fun invoke() = presenceRepository.setOnline()
}
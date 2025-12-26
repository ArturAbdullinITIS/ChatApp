package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.PresenceRepository
import javax.inject.Inject

class ObserveUserStatusUseCase @Inject constructor(
    private val presenceRepository: PresenceRepository
){
    operator fun invoke(userId: String?) = presenceRepository.observePresence(userId)
}
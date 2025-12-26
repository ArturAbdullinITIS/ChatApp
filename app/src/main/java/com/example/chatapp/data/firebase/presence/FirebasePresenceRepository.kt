package com.example.chatapp.data.firebase.presence

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.PresenceRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebasePresenceRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : PresenceRepository {
    override suspend fun setOnline() {
        val userId = authRepository.getCurrentUserId() ?: return
        firestore.collection("users")
            .document(userId)
            .update(
                mapOf(
                    "isOnline" to true,
                    "lastSeen" to System.currentTimeMillis()
                )
            )
    }

    override suspend fun setOffline() {
        val userId = authRepository.getCurrentUserId() ?: return
        firestore.collection("users")
            .document(userId)
            .update(
                mapOf(
                    "isOnline" to false,
                    "lastSeen" to System.currentTimeMillis()
                )
            )
    }

    override fun observePresence(userId: String?): Flow<Boolean> = callbackFlow{
        if(userId != null) {
            val listener = firestore.collection("users")
                .document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }
                    val isOnline = snapshot?.getBoolean("isOnline") ?: false
                    trySend(isOnline)
                }
            awaitClose { listener.remove() }
        }

    }

}
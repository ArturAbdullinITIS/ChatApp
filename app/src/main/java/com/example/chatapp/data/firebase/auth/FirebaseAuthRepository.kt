package com.example.chatapp.data.firebase.auth

import androidx.compose.ui.graphics.RectangleShape
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.presentation.screen.signup.SignUpCommand
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        username: String
    ): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.currentUser?.updateProfile(
                userProfileChangeRequest {
                    displayName = username
                }
            )?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}
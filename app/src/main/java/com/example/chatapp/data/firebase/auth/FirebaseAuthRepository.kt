package com.example.chatapp.data.firebase.auth

import androidx.compose.ui.graphics.RectangleShape
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.presentation.screen.signup.SignUpCommand
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            val user = auth.currentUser ?: return Result.failure(Exception("No user"))
            val userId = user.uid

            val userDoc = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            if (!userDoc.exists()) {
                val userData = mapOf(
                    "displayName" to (user.displayName ?: email),
                    "email" to email,
                    "isOnline" to true,
                    "lastSeen" to System.currentTimeMillis()
                )
                firestore.collection("users")
                    .document(userId)
                    .set(userData)
                    .await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun register(
        email: String,
        password: String,
        username: String
    ): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser ?: return Result.failure(Exception("No user"))
            val userId = user.uid
            auth.currentUser?.updateProfile(
                userProfileChangeRequest {
                    displayName = username
                }
            )?.await()
            val userData = mapOf(
                "displayName" to username,
                "email" to email,
                "isOnline" to true,
                "lastSeen" to System.currentTimeMillis()
            )
            firestore.collection("users")
                .document(userId)
                .set(userData)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override suspend fun logOut(): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val userStatusUpdate = mapOf(
                    "isOnline" to false,
                    "lastSeen" to System.currentTimeMillis()
                )
                firestore.collection("users")
                    .document(userId)
                    .update(userStatusUpdate)
                    .await()
            }
            auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
package com.example.chatapp.data.firebase.chat

import com.example.chatapp.domain.model.Chat
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.ChatRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseChatRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : ChatRepository {
    override fun getChats(): Flow<List<Chat>> = callbackFlow {
        val userId = authRepository.getCurrentUserId()!!

        firestore.collection("chats")
            .document(userId)
            .collection("chats")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val chats = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Chat::class.java)?.copy(id = doc.id)

                } ?: emptyList()
                trySend(chats)
            }
        awaitClose()
    }

    override fun getMessages(receiverId: String): Flow<List<Message>> = callbackFlow {
        val userId = authRepository.getCurrentUserId() ?: return@callbackFlow

        firestore.collection("chats")
            .document(userId)
            .collection("chats")
            .document(receiverId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val messages = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Message::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(messages)
            }
        awaitClose()
    }

    override suspend fun sendMessage(
        receiverId: String,
        text: String
    ): Result<Unit> {
        val senderId =
            authRepository.getCurrentUserId() ?: return Result.failure(Exception("No user"))

        val chatUpdate = hashMapOf(
            "name" to getUserDisplayName(userId = receiverId),
            "lastMessage" to text,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("chats")
            .document(senderId)
            .collection("chats")
            .document(receiverId)
            .set(chatUpdate)
            .await()

        val message = hashMapOf(
            "senderId" to senderId,
            "text" to text,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("chats")
            .document(senderId)
            .collection("chats")
            .document(receiverId)
            .collection("messages")
            .add(message)
            .await()

        return Result.success(Unit)

    }

    private suspend fun getUserDisplayName(userId: String): String {
        return try {
            firestore.collection("users")
                .document(userId)
                .get()
                .await()
                .getString("displayName") ?: userId
        } catch (e: Exception) {
            authRepository.getCurrentUserId()?.let { currentUserId ->
                if (userId == currentUserId) "Me" else userId
            } ?: userId
        }
    }


    override fun getPresence(receiverId: String): Flow<Boolean> = callbackFlow {
        val userId = authRepository.getCurrentUserId() ?: return@callbackFlow
        val listener = firestore.collection("chats")
            .document(userId)
            .collection("chats")
            .document(receiverId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                snapshot?.getBoolean("presence")?.let { trySend(it) }
            }

        awaitClose { listener.remove() }
    }

}
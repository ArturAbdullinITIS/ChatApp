package com.example.chatapp.di

import com.example.chatapp.data.firebase.auth.FirebaseAuthRepository
import com.example.chatapp.data.firebase.chat.FirebaseChatRepository
import com.example.chatapp.data.firebase.presence.FirebasePresenceRepository
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.ChatRepository
import com.example.chatapp.domain.repository.PresenceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface FirebaseModule {

    @Binds
    @Singleton
    fun bindFirebaseAuthRepository(
        impl: FirebaseAuthRepository
    ): AuthRepository

    @Binds
    @Singleton
    fun bindFirebaseChatRepository(
        impl: FirebaseChatRepository
    ): ChatRepository

    @Binds
    @Singleton
    fun bindFirebasePresenceRepository(
        impl: FirebasePresenceRepository
    ): PresenceRepository

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @Singleton
        fun provideFirebaseFirestore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }

}
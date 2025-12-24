package com.example.chatapp.domain.model

data class Chat(
    val id: String = "",
    val name: String = "",
    val lastMessage: String = "",
    val timestamp: Long = 0L
)
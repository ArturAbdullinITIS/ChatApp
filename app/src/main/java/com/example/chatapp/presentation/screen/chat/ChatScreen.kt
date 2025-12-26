package com.example.chatapp.presentation.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.chatapp.presentation.util.formatDate


@Composable
fun ChatScreen(
    chatId: String,
    chatName: String,
    onNavigateBack: () -> Unit
) {
    ChatContent(chatId = chatId, chatName = chatName, onNavigateBack = onNavigateBack)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatContent(
    modifier: Modifier = Modifier,
    chatId: String,
    chatName: String,
    onNavigateBack: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    LaunchedEffect(chatId) {
        viewModel.init(chatId, chatName)
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 8.dp,
                tonalElevation = 8.dp
            ) {
                TopAppBar(
                    title = {
                        CustomTitle(
                            chatName = state.title,
                            status = state.status
                        )
                    },
                    navigationIcon = {
                        GoBackToChatList(
                            onNavigateBack = onNavigateBack
                        )
                    },
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                if (state.messages.isNotEmpty()) {
                    LazyColumn(
                        state = listState
                    ) {
                        items(items = state.messages) { item ->
                            val isSender = item.senderId == viewModel.currentUserId
                            val timestamp = formatDate(item.timestamp)
                            MessageBox(
                                message = item.text,
                                isSender = isSender,
                                timestamp = timestamp
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No messages yet. Start the conversation!",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                shadowElevation = 16.dp,
                tonalElevation = 16.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier.padding(top = 8.dp)
                ) {
                    SendMessageBox(
                        value = state.message,
                        onValueChange = { viewModel.processCommand(ChatCommand.InputMessage(it)) },
                        onClick = { viewModel.processCommand(ChatCommand.SendMessage) },
                        isSendEnabled = state.sendButtonEnabled
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
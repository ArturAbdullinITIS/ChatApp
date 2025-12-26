package com.example.chatapp.presentation.screen.chatlist

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.ui.theme.LightGrey
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ChatListScreen(
    onChatClick: (Chat) -> Unit,
    onNavigateToSignIn: () -> Unit
) {
    ChatListContent(
        onChatClick = onChatClick,
        onNavigateToSignIn = onNavigateToSignIn
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListContent(
    onNavigateToSignIn: () -> Unit,
    modifier: Modifier = Modifier,
    onChatClick: (Chat) -> Unit,
    viewModel: ChatListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val filteredChats by viewModel.filteredChats.collectAsState()

    // test
    LaunchedEffect(Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        Log.d("UserId", "CURRENT USER ID: $userId")
    }
    LaunchedEffect(state.isLoggedIn) {
        if(!state.isLoggedIn) {
            onNavigateToSignIn
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = {
                    SearchChatBar(
                        value = state.query,
                        onValueChange = {
                            viewModel.processCommand(ChatListCommand.SearchQueryChanged(it))
                        }
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        Icon(
                            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Chat"
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .padding(horizontal = 4.dp)
        ) {
            HorizontalDivider(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp))
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(
                    items = filteredChats,
                    key = { _, chat -> chat.id }
                ) { _, chat ->
                    ChatCard(
                        onChatClick = onChatClick,
                        chat = chat
                    )
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.processCommand(ChatListCommand.Logout)
                }
            ) {
                Text(
                    "Log Out"
                )
            }

        }


    }
}
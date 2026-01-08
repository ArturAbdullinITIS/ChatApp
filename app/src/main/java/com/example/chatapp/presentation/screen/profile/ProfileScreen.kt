package com.example.chatapp.presentation.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.chatapp.presentation.screen.chatlist.ChatListCommand


@Composable
fun ProfileScreen(
    onLogOut: () -> Unit
) {
    ProfileContent(onLogOut)
}


@Composable
fun ProfileContent(
    onLogOut: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.isLoggedIn) {
        if(!state.isLoggedIn) {
            onLogOut()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier,
                text = state.username
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.processCommand(ProfileCommand.LogOut)
                }
            ) {
                Text(
                    "Log Out"
                )
            }
        }

    }
}
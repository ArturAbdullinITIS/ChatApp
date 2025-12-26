package com.example.chatapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.chatapp.domain.usecase.IsUserLoggedInUseCase
import com.example.chatapp.domain.usecase.SetOfflineUseCase
import com.example.chatapp.domain.usecase.SetOnlineUseCase
import com.example.chatapp.presentation.navigation.NavGraph
import com.example.chatapp.presentation.screen.chatlist.ChatListContent
import com.example.chatapp.presentation.screen.signin.SignInScreen
import com.example.chatapp.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var isUserLoggedInUseCase: IsUserLoggedInUseCase

    @Inject
    lateinit var setOfflineUseCase: SetOfflineUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val isUserLoggedIn = isUserLoggedInUseCase()
        setContent {
            ChatAppTheme {
                NavGraph(isUserLoggedIn)
            }
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            setOfflineUseCase()
        }
    }
}

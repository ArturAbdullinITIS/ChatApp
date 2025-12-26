package com.example.chatapp.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.presentation.screen.chat.ChatScreen
import com.example.chatapp.presentation.screen.chatlist.ChatListScreen
import com.example.chatapp.presentation.screen.signin.SignInScreen
import com.example.chatapp.presentation.screen.signup.SignUpScreen


@Composable
fun NavGraph(
    isUserLoggedIn: Boolean
) {
    val navController = rememberNavController()
    val startDestination = if(isUserLoggedIn) Screen.ChatList.route else Screen.SignIn.route
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.SignIn.route) {
            SignInScreen(
                onNavigateToChatList = {
                    navController.navigate(Screen.ChatList.route) {
                        popUpTo(Screen.SignIn.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onNavigateToChatList = {
                    navController.navigate(Screen.ChatList.route) {
                        popUpTo(Screen.SignUp.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignIn = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.ChatList.route) {
            ChatListScreen(
                onChatClick = { chat ->
                    navController.navigate(Screen.Chat.createRoute(chat))
                },
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.ChatList.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            Screen.Chat.route,
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType},
                navArgument("chatName") { type = NavType.StringType}
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val chatName = backStackEntry.arguments?.getString("chatName") ?: ""
            ChatScreen(
                chatId = chatId,
                chatName = chatName,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


sealed class Screen(val route: String) {
    object SignIn: Screen("sign_in")
    object SignUp: Screen("sign_up")
    object ChatList: Screen("chat_list")

    object Chat: Screen("chat/{chatId}/{chatName}") {
        fun createRoute(chat: com.example.chatapp.domain.model.Chat) = "chat/${chat.id}/${Uri.encode(chat.name)}"

    }
}
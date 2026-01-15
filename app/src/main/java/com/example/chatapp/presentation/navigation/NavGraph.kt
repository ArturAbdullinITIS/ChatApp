package com.example.chatapp.presentation.navigation

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.presentation.screen.chat.ChatScreen
import com.example.chatapp.presentation.screen.chatlist.ChatListScreen
import com.example.chatapp.presentation.screen.profile.ProfileScreen
import com.example.chatapp.presentation.screen.signin.SignInScreen
import com.example.chatapp.presentation.screen.signup.SignUpScreen
import com.example.chatapp.ui.theme.Chat


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(
    isUserLoggedIn: Boolean
) {
    val navController = rememberNavController()
    val startDestination = Screen.SignIn.route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf(Screen.ChatList.route, Screen.Profile.route)

    Scaffold(
        bottomBar = {
            if(showBottomBar) {
                NavigationBar(navController)
            }
        }
    ) {
        NavHost(
            modifier = Modifier.padding(),
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
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onLogOut = {
                        navController.navigate(Screen.SignIn.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }

}


@Composable
fun NavigationBar(navController: NavController) {
    val screens = listOf(
        Screen.ChatList,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface),
        tonalElevation = 8.dp,
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Profile -> Icons.Default.Person
                            Screen.ChatList -> Chat
                            else -> Icons.Default.Person
                        },
                        contentDescription = screen.route
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route)
                },
                label = { Text(getScreenTitle(screen)) }
            )
        }
    }
}
@Composable
private fun getScreenTitle(screen: Screen): String {
    return when(screen) {
        Screen.Profile -> "profile"
        Screen.ChatList -> "chats"
        else -> ""
    }
}


sealed class Screen(val route: String) {
    object SignIn: Screen("sign_in")
    object SignUp: Screen("sign_up")
    object ChatList: Screen("chat_list")
    object Profile: Screen("profile")

    object Chat: Screen("chat/{chatId}/{chatName}") {
        fun createRoute(chat: com.example.chatapp.domain.model.Chat) = "chat/${chat.id}/${Uri.encode(chat.name)}"

    }
}
package br.com.corecode.wtcchallenge.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    object Main : Screen("main_screen")
    object Chats : Screen("chats_screen", "Conversas", Icons.Filled.ChatBubbleOutline)
    object Campaigns : Screen("campaigns_screen", "Campanhas", Icons.Default.Campaign)
    object Profile : Screen("profile_screen", "Perfil", Icons.Default.Person)

    object Conversation : Screen("conversation_screen/{chatId}"){
        fun createRoute(chatId: String) = "conversation_screen/$chatId"
    }
}

val bottomNavItems = listOf(
    Screen.Chats,
    Screen.Campaigns,
    Screen.Profile
)
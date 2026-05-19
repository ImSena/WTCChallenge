package br.com.corecode.wtcchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import br.com.corecode.wtcchallenge.ui.screens.auth.LoginScreen
import br.com.corecode.wtcchallenge.ui.screens.conversation.ConversationScreen
import br.com.corecode.wtcchallenge.ui.screens.main.MainScreen
import br.com.corecode.wtcchallenge.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val rootNavController = rememberNavController()
    val sessionRepository: SessionRepository = SessionRepository(LocalContext.current)
    val role by sessionRepository.activeUserRole.collectAsState(initial = null)
    val isOperator = role == "OPERADOR"

    NavHost(
        navController = rootNavController,
        startDestination = Screen.Splash.route
    ){
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigationToLogin = {
                    rootNavController.navigate(Screen.Login.route){
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },

                onNavigationToMain = {
                    rootNavController.navigate(Screen.Main.route){
                        popUpTo(Screen.Splash.route) {inclusive = true}
                    }
                }
            )
        }

        composable(
            Screen.Login.route
        ) {
            LoginScreen(
                onLoginSuccess = {
                    rootNavController.navigate(Screen.Main.route){
                        popUpTo(Screen.Login.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable (Screen.Main.route) {
            MainScreen(
                rootNavController = rootNavController,
                isOperator
            )
        }

        composable(
            route = Screen.Conversation.route,
            arguments = listOf(
                navArgument("chatId"){type = NavType.StringType},
                navArgument("contactName"){type = NavType.StringType}
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            val contactName = backStackEntry.arguments?.getString("contactName")?.let {
                java.net.URLDecoder.decode(it, "UTF-8")
            }
            ConversationScreen(
                chatId = chatId,
                contactName = contactName ?: "Contato",
                onNavigateBack = {
                    rootNavController.popBackStack()
                }
            )

        }
    }
}
package br.com.corecode.wtcchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.corecode.wtcchallenge.ui.screens.auth.LoginScreen
import br.com.corecode.wtcchallenge.ui.screens.conversation.ConversationScreen
import br.com.corecode.wtcchallenge.ui.screens.main.MainScreen
import br.com.corecode.wtcchallenge.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val rootNavController = rememberNavController()

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
                        rootNavController.navigate(Screen.Main.route){
                            popUpTo(Screen.Splash.route) {inclusive = true}
                        }
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
            MainScreen(rootNavController = rootNavController)
        }

        composable(
            route = Screen.Conversation.route,
            arguments = listOf(navArgument("chatId"){type = NavType.StringType})
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            ConversationScreen(chatId = chatId)

        }
    }
}
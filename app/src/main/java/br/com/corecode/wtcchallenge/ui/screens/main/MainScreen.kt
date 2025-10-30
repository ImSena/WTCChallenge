package br.com.corecode.wtcchallenge.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.corecode.wtcchallenge.ui.navigation.Screen
import br.com.corecode.wtcchallenge.ui.navigation.bottomNavItems
import br.com.corecode.wtcchallenge.ui.screens.campaings.CampaignsScreen
import br.com.corecode.wtcchallenge.ui.screens.chats.ChatsScreen
import br.com.corecode.wtcchallenge.ui.screens.profile.ProfileScreen

@Composable
fun MainScreen(
    rootNavController: NavController,
    isOperator: Boolean
) {
    val tabsNavController = rememberNavController()

    val bottomScreens = if (isOperator) {
        listOf(Screen.Chats, Screen.Campaigns, Screen.Profile)
    } else {
        listOf(Screen.Chats, Screen.Profile)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by tabsNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomScreens.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            tabsNavController.navigate(screen.route) {
                                popUpTo(tabsNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(screen.title!!) },
                        icon = { Icon(screen.icon!!, contentDescription = screen.title!!) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = tabsNavController,
            startDestination = Screen.Chats.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Chats.route) {
                ChatsScreen(onChatClick = { chatId, contactName ->
                    rootNavController.navigate(Screen.Conversation.createRoute(chatId, contactName))
                }, isOperator)
            }

            composable(Screen.Campaigns.route) { CampaignsScreen() }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    onLoggedOut = {
                        rootNavController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

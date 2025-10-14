package br.com.corecode.wtcchallenge.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import br.com.corecode.wtcchallenge.data.repository.SessionRepository

@Composable
fun SplashScreen(
    onNavigationToLogin: () -> Unit,
    onNavigationToMain: () -> Unit
) {
    val sessionRepository = SessionRepository(LocalContext.current)

    val sessionUidState by sessionRepository.activeSessionUid.collectAsState(initial = Unit)

    LaunchedEffect(sessionUidState) {
        when(val uid = sessionUidState){
            is Unit -> {

            }
            is String -> {
                onNavigationToMain()
            }
            null -> {
                onNavigationToLogin()
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
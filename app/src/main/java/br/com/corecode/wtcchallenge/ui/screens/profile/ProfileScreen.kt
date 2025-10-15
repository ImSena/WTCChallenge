package br.com.corecode.wtcchallenge.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel(),
    onLoggedOut: () -> Unit
) {
    val isLoggingOut by profileViewModel.isLoggingOut.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Perfil do Usuário",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
           profileViewModel.logout { success ->
               if(success){
                   onLoggedOut()
               }
           }
        }) {
            if (isLoggingOut) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("SAIR (LOGOUT)")
            }
        }
    }
}

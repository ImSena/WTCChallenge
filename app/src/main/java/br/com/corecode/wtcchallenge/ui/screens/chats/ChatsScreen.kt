package br.com.corecode.wtcchallenge.ui.screens.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.ui.theme.WTCChallengeTheme

@Composable
fun ChatsScreen(onChatClick: (chatId: String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Conversas",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        items(5) { index ->
            val sampleChatId = "chatId_$index"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onChatClick(sampleChatId) },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = "Nome do Contato ${index + 1}", style = MaterialTheme.typography.headlineMedium)
                        Text(text = "Última mensagem recebida...", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatsScreenPreview() {
    WTCChallengeTheme{
        ChatsScreen(onChatClick = {})
    }
}
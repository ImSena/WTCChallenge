package br.com.corecode.wtcchallenge.ui.screens.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.ui.theme.WTCChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(chatId: String?) {
    var messageText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conversa com Contato") },
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Digite uma mensagem...") }
                )
                IconButton(onClick = { /* TODO: Lógica de envio de mensagem */ }) {
                    Icon(Icons.Filled.Send, contentDescription = "Enviar")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Histórico da conversa para o ID:")
            Text(chatId ?: "ID não encontrado", style = MaterialTheme.typography.headlineMedium)
            // TODO: Aqui viria a lista de mensagens (LazyColumn)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConversationScreenPreview() {
    WTCChallengeTheme {
        ConversationScreen(chatId = "preview_chat_123")
    }
}
package br.com.corecode.wtcchallenge.ui.screens.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.ui.screens.conversation.components.Bubble
import br.com.corecode.wtcchallenge.ui.screens.conversation.components.MessageInputBar

data class Message(
    val id: String,
    val text: String,
    val timestamp: String,
    val senderId: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    chatId: String?,
    contactName: String?,
    onNavigateBack: () -> Unit
) {
    val currentUserId = "me"

    val sampleMessages = remember {
        mutableStateListOf(
            Message("1", "Olá! Como posso ajudar?", "10:00", "contact"),
            Message("2", "Gostaria de confirmar a reunião de amanhã.", "10:01", "me"),
            Message("3", "Claro. A reunião das 10h está confirmada. A sala já foi reservada.", "10:02", "contact"),
            Message("4", "Perfeito, obrigado!", "10:03", "me")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(contactName ?: "Conversa")},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Icone Seta voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            MessageInputBar { newMessageText ->
                sampleMessages.add(
                    Message(
                        id = (sampleMessages.size + 1).toString(),
                        text = newMessageText,
                        timestamp = "10:05",
                        senderId = currentUserId
                    )
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding((innerPadding))
                .padding(horizontal = 8.dp),
            reverseLayout = true
        ) {
            items(items = sampleMessages.reversed()){ message ->
                Bubble(
                    message = message,
                    isSentByCurrentUser = message.senderId == currentUserId
                )
            }
        }
    }
}

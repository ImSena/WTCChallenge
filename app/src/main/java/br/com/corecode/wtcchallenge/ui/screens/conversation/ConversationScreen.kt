package br.com.corecode.wtcchallenge.ui.screens.conversation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import br.com.corecode.wtcchallenge.domain.model.RichMessage
import br.com.corecode.wtcchallenge.ui.screens.chats.ChatViewModel
import br.com.corecode.wtcchallenge.ui.screens.chats.ChatViewModelFactory
import br.com.corecode.wtcchallenge.ui.screens.conversation.components.Bubble
import br.com.corecode.wtcchallenge.ui.screens.conversation.components.MessageInputBar

data class Message(
    val id: String,
    val text: String,
    val timestamp: Long,
    val senderId: String,
    val type: String,
    val richMessage: RichMessage?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(
    chatId: String?,
    contactName: String?,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val sessionRepo = SessionRepository(context)
    val viewModel: ChatViewModel = viewModel(
        factory = ChatViewModelFactory(sessionRepo)
    )

    val currentUserId = sessionRepo.activeSessionUid.collectAsState(initial = "").value
    val messagesState by viewModel.messages.collectAsState()
    val messages = messagesState.getOrDefault(emptyList())

    LaunchedEffect(chatId) {
        viewModel.selectChat(chatId.orEmpty())
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
                viewModel.sendMessage(newMessageText)
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
            items(items = messages.reversed()){ message ->
                Bubble(
                    message = Message(
                        id = message.id,
                        text = message.text,
                        timestamp = message.timestamp,
                        senderId = message.senderId,
                        type = message.type,
                        richMessage = message.richMessage
                    ),
                    isSentByCurrentUser = message.senderId == currentUserId
                )
            }
        }
    }
}

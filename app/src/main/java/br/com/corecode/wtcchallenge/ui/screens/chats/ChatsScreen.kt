package br.com.corecode.wtcchallenge.ui.screens.chats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import br.com.corecode.wtcchallenge.ui.screens.chats.components.Chat
import java.sql.Timestamp

data class Chat(
    val id: String,
    val contactName: String,
    val lastMessage: String,
    val timestamp: Long,
    val unreadCount: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(onChatClick: (chatId: String, contactName: String) -> Unit, isOperator: Boolean) {
    val context = LocalContext.current
    val sessionRepo = SessionRepository(context)
    val viewModel: ChatViewModel = viewModel(
        factory = ChatViewModelFactory(context, sessionRepo)
    )

    val currentUserId by sessionRepo.activeSessionUid.collectAsState(initial = null)

    val chatState by viewModel.chatList.collectAsState()
    val chats = chatState.getOrDefault(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Conversas")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
//        floatingActionButton = {
//            if(isOperator){
//                FloatingActionButton(
//                    onClick = { Unit},
//                    containerColor = MaterialTheme.colorScheme.secondary,
//                    contentColor = MaterialTheme.colorScheme.onSecondary
//                ) {
//                    Icon(Icons.Filled.Add, contentDescription = "Nova Conversa")
//                }
//            }
//
//        }
    ) {innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            items(items = chats){chat->
                val otherParticipantInfo = chat.participantsDetails
                    .filterKeys { it != currentUserId }
                    .values
                    .firstOrNull()
                Chat(
                    chat = Chat(
                        id = chat.id.ifEmpty { chat.id },
                        contactName = otherParticipantInfo?.name ?: "Contato",
                        lastMessage = chat.lastMessage,
                        timestamp = chat.lastMessageTimestamp
                    ),
                    onChatClick = {chatId, contactName ->
                        onChatClick(chatId, contactName)
                    }
                )
            }
        }

    }
}

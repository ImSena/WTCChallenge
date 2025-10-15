package br.com.corecode.wtcchallenge.ui.screens.chats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.corecode.wtcchallenge.ui.screens.chats.components.Chat
import br.com.corecode.wtcchallenge.ui.theme.WTCChallengeTheme

data class Chat(
    val id: String,
    val contactName: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(onChatClick: (chatId: String, contactName: String) -> Unit) {
    val sampleChats = listOf(
        Chat("chat_1", "Ana Silva - ACME Corp", "Claro, envio a proposta até o final do dia.", "18:32", 2),
        Chat("chat_2", "Carlos Pereira", "Reunião confirmada para amanhã às 10h.", "17:55", 0),
        Chat("chat_3", "WTC Eventos", "Não perca nosso próximo Innovation Talk!", "Ontem", 1),
        Chat("chat_4", "Juliana Costa", "Obrigada pelo feedback!", "Ontem", 0),
        Chat("chat_5", "Equipe de Suporte", "Sua solicitação foi recebida e está sendo processada.", "Sexta", 0),
        Chat("chat_6", "Ricardo Mendes - Innovatech", "O contrato já foi assinado e enviado.", "Sexta", 0),
        Chat("chat_7", "WTC Business Club", "Lembrete: Almoço de networking na terça-feira.", "Quinta", 1),
        Chat("chat_8", "Fernanda Lima", "Você poderia revisar o relatório financeiro?", "Quarta", 0),
        Chat("chat_9", "Marcos Andrade", "Excelente apresentação hoje!", "Quarta", 0),
        Chat("chat_10", "Laura Martins", "Vamos marcar um café para discutir o projeto.", "Terça", 3),
        Chat("chat_11", "Lucas Gomes - Tech Solutions", "A nova versão da API está pronta para testes.", "Terça", 0),
        Chat("chat_12", "Serviços Financeiros WTC", "Sua fatura de Outubro já está disponível.", "10/10/2025", 1),
        Chat("chat_13", "Beatriz Santos", "O material da palestra ficou ótimo, parabéns!", "09/10/2025", 0),
        Chat("chat_14", "Gustavo Ribeiro", "Estou um pouco atrasado para a reunião, peço desculpas.", "09/10/2025", 0),
        Chat("chat_15", "Camila Ferreira", "Podemos reagendar nossa conversa para a próxima semana?", "08/10/2025", 0),
        Chat("chat_16", "Time de Marketing", "Nova campanha no ar! Confira os resultados.", "07/10/2025", 5),
        Chat("chat_17", "Daniel Almeida", "Obrigado pela indicação.", "07/10/2025", 0),
        Chat("chat_18", "Patrícia Souza - RH", "Pesquisa de clima organizacional disponível.", "06/10/2025", 0),
        Chat("chat_19", "Rodrigo Costa", "A documentação técnica foi atualizada.", "06/10/2025", 0),
        Chat("chat_20", "Aline Barros", "Vamos em frente com essa estratégia.", "05/10/2025", 0),
        Chat("chat_21", "Felipe Martins", "Recebeu meu e-mail sobre o Q3?", "03/10/2025", 1),
        Chat("chat_22", "WTC Concierge", "Sua reserva no restaurante foi confirmada.", "02/10/2025", 0),
        Chat("chat_23", "Bruno Oliveira", "O link para a videoconferência está no convite.", "01/10/2025", 0),
        Chat("chat_24", "Vanessa Moraes", "Anexei os slides da apresentação.", "30/09/2025", 0),
        Chat("chat_25", "International Desk", "Oportunidades de negócio na delegação alemã.", "29/09/2025", 1)
    )

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { Unit},
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Nova Conversa")
            }
        }
    ) {innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            items(items = sampleChats){chat->
                Chat(
                    chat = chat,
                    onChatClick = {chatId, contactName ->
                        onChatClick(chatId, contactName)
                    }
                    )
            }
        }

    }
}

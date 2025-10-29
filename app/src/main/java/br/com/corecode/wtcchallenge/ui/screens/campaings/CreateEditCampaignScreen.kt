package br.com.corecode.wtcchallenge.ui.screens.campaings

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.domain.model.Campaign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditCampaignScreen(
    campaignId: String?,
    onNavigateBack: () -> Unit,
    onSaveCampaign: (Campaign) -> Unit
) {
    val isEditing = campaignId != null

    var segment by remember { mutableStateOf(if (isEditing) "CEOs" else "") }
    var title by remember { mutableStateOf(if (isEditing) "Campanha Especial WTC" else "") }
    var message by remember { mutableStateOf(if (isEditing) "Participe do nosso evento exclusivo!" else "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Campanha" else "Criar Campanha") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título da Campanha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Mensagem") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = segment,
                onValueChange = { segment = it },
                label = { Text("Segmento") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    val campaign = Campaign(
                        id = campaignId ?: java.util.UUID.randomUUID().toString(),
                        title = title,
                        body = message,
                        segment = segment
                    )
                    onSaveCampaign(campaign)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    text = if (isEditing) "SALVAR ALTERAÇÕES" else "DISPARAR CAMPANHA",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

    }
}
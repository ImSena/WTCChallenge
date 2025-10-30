package br.com.corecode.wtcchallenge.ui.screens.campaings.views.operator

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.ui.screens.campaings.components.OperatorCampaignCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperatorCampaignsListView(
    campaigns: List<Campaign>,
    onAddClick: () -> Unit,
    onEditClick: (campaignId: String) -> Unit,
    onDeleteClick: (campaignId: String) -> Unit,
    onDispararClick: (campaign: Campaign) -> Unit
) {
    val context = LocalContext.current
    var campaignToDelete by remember { mutableStateOf<Campaign?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerenciar Campanhas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Campanha")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(campaigns) { campaign ->
                OperatorCampaignCard(
                    campaign = campaign,
                    onEditClick = { onEditClick(campaign.id) },
                    onDeleteClick = { campaignToDelete = campaign },
                    onDispararClick = {onDispararClick(campaign)}
                )
            }
        }

        if(campaignToDelete != null){
            AlertDialog(
                onDismissRequest = {campaignToDelete = null},
                title = {Text("Confirmar Exclusão")},
                text = {Text("Deseja realmente excluir a campanha \"${campaignToDelete?.title}\"?")},
                confirmButton = {
                    TextButton(
                        onClick = {
                            campaignToDelete?.let { onDeleteClick(it.id) }
                            campaignToDelete = null
                        }
                    ) {
                        Text("Excluir")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {campaignToDelete = null}
                    ) {
                        Text("Cancelar")
                    }
                }

            )
        }
    }
}
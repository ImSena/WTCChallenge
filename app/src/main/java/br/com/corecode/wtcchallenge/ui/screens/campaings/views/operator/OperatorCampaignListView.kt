package br.com.corecode.wtcchallenge.ui.screens.campaings.views.operator

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.domain.model.Campaign
import br.com.corecode.wtcchallenge.ui.screens.campaings.components.OperatorCompaignCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperatorCampaignsListView(
    campaigns: List<Campaign>,
    onAddClick: () -> Unit,
    onEditClick: (campaignId: String) -> Unit
) {
    val context = LocalContext.current

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
                OperatorCompaignCard(
                    campaign = campaign,
                    onEditClick = { onEditClick(campaign.id) },
                    onDeleteClick = {
                        Toast.makeText(context, "Excluir: ${campaign.title}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
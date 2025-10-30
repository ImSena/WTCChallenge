package br.com.corecode.wtcchallenge.ui.screens.campaings.components

import androidx.compose.foundation.layout.* // Mude para import *
import androidx.compose.material3.Button // ADICIONE
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton // ADICIONE
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton // PODE REMOVER, MAS NÃO PRECISA
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.domain.model.Campaign

@Composable
fun ClientCampaignCard(
    campaign: Campaign,
    onActionClick: (String) -> Unit = {}
) {
//    ElevatedCard(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = campaign.title,
//                style = MaterialTheme.typography.headlineMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = campaign.body,
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.onSurface
//            )
//
//            val actionTitles = campaign.actions.values.toList()
//
//            if (actionTitles.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(16.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    if (actionTitles.size > 1) {
//                        OutlinedButton(
//                            onClick = { onActionClick(actionTitles[1]) }
//                        ) {
//                            Text(actionTitles[1].uppercase())
//                        }
//                        Spacer(modifier = Modifier.width(8.dp))
//                    }
//
//                    Button(
//                        onClick = { onActionClick(actionTitles[0]) }
//                    ) {
//                        Text(actionTitles[0].uppercase())
//                    }
//                }
//            }
//        }
//    }
}
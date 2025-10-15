package br.com.corecode.wtcchallenge.ui.screens.campaings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.corecode.wtcchallenge.ui.theme.WTCChallengeTheme

@Composable
fun CampaignsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Campanhas e Eventos",
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = "Aqui serão exibidos os convites para eventos exclusivos e campanhas do WTC.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CampaignsScreenPreview() {
    WTCChallengeTheme {
        CampaignsScreen()
    }
}
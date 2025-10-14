package br.com.corecode.wtcchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.corecode.wtcchallenge.ui.navigation.AppNavigation
import br.com.corecode.wtcchallenge.ui.theme.WTCChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WTCChallengeTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}

/*@Composable
fun ThemeTestScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "World Trade Center Business Club",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Uma plataforma de comunicação exclusiva para conectar oportunidades de negócio.", // [cite: 557]
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Ação do botão */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "Acessar Plataforma",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Próximo Evento",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Innovation Talks - 2025",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Light Mode Preview")
@Composable
fun ThemeTestScreenLightPreview() {
    WTCChallengeTheme(darkTheme = false) {
        ThemeTestScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode Preview")
@Composable
fun ThemeTestScreenDarkPreview() {
    WTCChallengeTheme(darkTheme = true) {
        ThemeTestScreen()
    }
}
 */
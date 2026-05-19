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
    viewModel: CampaignsViewModel
) {
    val context = LocalContext.current
    val isEditing = !campaignId.isNullOrEmpty()

    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var mainUrl by remember { mutableStateOf("") }
    var btn1Title by remember { mutableStateOf("") }
    var btn1Url by remember { mutableStateOf("") }
    var btn2Title by remember { mutableStateOf("") }
    var btn2Url by remember { mutableStateOf("") }

    val campaignState by viewModel.selectedCampaign.collectAsState()

    LaunchedEffect(campaignId) {
        if (isEditing) {
            viewModel.getCampaignById(campaignId!!)
        } else {
            viewModel.clearSelectedCampaign()
        }
    }

    LaunchedEffect(campaignState) {
        campaignState.getOrNull()?.let { campaign ->
            title = campaign.title ?: ""
            body = campaign.body ?: ""
            mainUrl = campaign.mainUrl ?: ""
            btn1Title = campaign.btn1Title ?: ""
            btn1Url = campaign.btn1Url ?: ""
            btn2Title = campaign.btn2Title ?: ""
            btn2Url = campaign.btn2Url ?: ""
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSelectedCampaign()
        }
    }

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

            Text("Conteúdo Principal", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título da Campanha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Corpo da Mensagem") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = mainUrl,
                onValueChange = { mainUrl = it },
                label = { Text("URL Principal (clique no card)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(Modifier.height(24.dp))

            Text("Ação 1 (Opcional)", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = btn1Title,
                onValueChange = { btn1Title = it },
                label = { Text("Título Botão 1 (ex: Inscrever-se)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = btn1Url,
                onValueChange = { btn1Url = it },
                label = { Text("URL Botão 1") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            Text("Ação 2 (Opcional)", style = MaterialTheme.typography.titleMedium, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = btn2Title,
                onValueChange = { btn2Title = it },
                label = { Text("Título Botão 2 (ex: Saiba Mais)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = btn2Url,
                onValueChange = { btn2Url = it },
                label = { Text("URL Botão 2") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    val campaign = Campaign(
                        id = campaignId ?: "",
                        title = title,
                        body = body,
                        mainUrl = mainUrl,
                        btn1Title = btn1Title,
                        btn1Url = btn1Url,
                        btn2Title = btn2Title,
                        btn2Url = btn2Url
                    )

                    viewModel.saveCampaign(campaign) { result ->
                        if (result.isSuccess) {
                            Toast.makeText(context, "Campanha salva com sucesso!", Toast.LENGTH_SHORT).show()
                            onNavigateBack()
                        } else {
                            Toast.makeText(context, "Erro ao salvar: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    text = if (isEditing) "SALVAR ALTERAÇÕES" else "CRIAR CAMPANHA",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Atenção: 'Disparar' a campanha é feito na tela de lista, após salvar.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
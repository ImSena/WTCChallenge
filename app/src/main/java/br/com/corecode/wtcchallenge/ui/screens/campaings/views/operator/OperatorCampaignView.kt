package br.com.corecode.wtcchallenge.ui.screens.campaings.views.operator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperatorCompaignView() {
    var segment by remember { mutableStateOf("") }
    var title by remember {mutableStateOf("")}
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campanhas Express") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
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
        ){
            Text(
                text = "Criar Nova Campanha",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = segment,
                onValueChange = {segment = it},
                modifier = Modifier.fillMaxWidth(),
                label = {Text("Segmento (Ex: CEOs, CFOs, Vendas)")},
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {title = it},
                modifier = Modifier.fillMaxWidth(),
                label = {Text("Titulo da Campanha")},
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = message,
                onValueChange = {message = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                label = {Text("Mensagem")}
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { Unit },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ){
                Text("DISPARAR CAMPANHA", style = MaterialTheme.typography.labelLarge)
            }
        }

    }
}
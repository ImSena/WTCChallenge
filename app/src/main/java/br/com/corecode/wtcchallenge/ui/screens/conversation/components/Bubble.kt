package br.com.corecode.wtcchallenge.ui.screens.conversation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import br.com.corecode.wtcchallenge.domain.model.RichMessage
import br.com.corecode.wtcchallenge.ui.screens.conversation.Message
import br.com.corecode.wtcchallenge.ui.utils.formatBubbleTimestamp

@Composable
fun Bubble(message: Message, isSentByCurrentUser: Boolean) {

    if (message.type == "rich" && message.richMessage != null) {
        RichMessageBubble(message = message.richMessage)
    } else {
        TextBubble(message = message, isSentByCurrentUser = isSentByCurrentUser)
    }
}

@Composable
fun TextBubble(message: Message, isSentByCurrentUser: Boolean) {
    val bubbleColor = if(isSentByCurrentUser){
        MaterialTheme.colorScheme.primary
    }else{
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (isSentByCurrentUser) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val bubbleAlignment = if (isSentByCurrentUser) Alignment.CenterEnd else Alignment.CenterStart

    val bubbleShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if(isSentByCurrentUser) 16.dp else 0.dp,
        bottomEnd = if(isSentByCurrentUser) 0.dp else 16.dp
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = bubbleAlignment
    ){
        Column(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(bubbleShape)
                .background(bubbleColor)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ){
            Text(
                text = message.text,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = formatBubbleTimestamp(message.timestamp),
                color = textColor.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun RichMessageBubble(message: RichMessage) {
    val context = LocalContext.current

    val openUrl = { url: String ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable {
                if (message.url.isNotBlank()) openUrl(message.url)
            },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = message.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                message.actions.forEach { action ->
                    val actionUrl = message.actionUrls[action.action]
                    if (actionUrl != null) {
                        TextButton(onClick = { openUrl(actionUrl) }) {
                            Text(action.title)
                        }
                    }
                }
            }
        }
    }
}
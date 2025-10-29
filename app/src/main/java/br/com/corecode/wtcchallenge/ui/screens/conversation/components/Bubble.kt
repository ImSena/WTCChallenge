package br.com.corecode.wtcchallenge.ui.screens.conversation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import br.com.corecode.wtcchallenge.ui.screens.conversation.Message

@Composable
fun Bubble(message: Message, isSentByCurrentUser: Boolean) {
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
                text = message.timestamp.toString(),
                color = textColor.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }

}
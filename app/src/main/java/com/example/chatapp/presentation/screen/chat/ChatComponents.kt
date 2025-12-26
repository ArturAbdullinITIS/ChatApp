package com.example.chatapp.presentation.screen.chat

import android.R.attr.fontFamily
import android.R.attr.fontWeight
import android.R.attr.text
import android.R.attr.textStyle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.ui.theme.ArrowBack
import com.example.chatapp.ui.theme.PoppinsFontFamily
import com.example.chatapp.ui.theme.Send
import com.google.firebase.Timestamp


@Composable
fun GoBackToChatList(
    onNavigateBack: () -> Unit,
) {
    IconButton(
        onClick = onNavigateBack
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = stringResource(R.string.back_to_chat_list_button)
        )
    }
}


@Composable
fun CustomTitle(
    chatName: String,
    status: Boolean
) {
    Row(
        modifier = Modifier.height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(52.dp),
            imageVector = Icons.Default.AccountCircle,
            contentDescription = stringResource(R.string.user_icon_chat)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = chatName,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 14.sp
            )
            Text(
                text = if (status) "Online" else "Offline",
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 14.sp

            )
        }
    }
}

@Composable
fun MessageBox(
    message: String,
    isSender: Boolean,
    timestamp: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if(isSender) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.padding(8.dp)
                .widthIn(max = 260.dp)
            ,
            shape = if (isSender) RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 4.dp
            ) else RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 4.dp,
                bottomEnd = 16.dp),

            border = if(!isSender) BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            ) else null,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if(isSender) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                horizontalAlignment = if(isSender) Alignment.End else Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = message,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color =  if (isSender) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    modifier = Modifier.padding(end = 8.dp, bottom = 4.dp, start = 4.dp),
                    textAlign = TextAlign.End,
                    text = timestamp,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 11.sp,
                    color =  if (isSender) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}


@Composable
fun SendMessageBox(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    isSendEnabled: Boolean
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    text = "Type a message",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
            },
            maxLines = 4
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = onClick,
            enabled = isSendEnabled
        ) {
            Icon(
                imageVector = Send,
                contentDescription = "Send message icon"
            )
        }
    }
}

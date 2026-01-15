package com.example.chatapp.presentation.screen.chatlist

import android.R.attr.enabled
import android.R.attr.fontFamily
import android.R.attr.fontWeight
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.chatapp.R
import com.example.chatapp.domain.model.Chat
import com.example.chatapp.presentation.util.formatDate
import com.example.chatapp.ui.theme.CustomSearch
import com.example.chatapp.ui.theme.LightGrey
import com.example.chatapp.ui.theme.PoppinsFontFamily



@Composable
fun ChatCard(
    onChatClick: (Chat) -> Unit,
    chat: Chat
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onChatClick(chat)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(24.dp)

    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                modifier = Modifier.size(52.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Chat Avatar",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = chat.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily
                    )
                    Text(
                        text = formatDate(chat.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = PoppinsFontFamily,
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = chat.lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = PoppinsFontFamily,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}


@Composable
fun SearchChatBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = if (isFocused)
                    MaterialTheme.colorScheme.primary
                else
                    Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 12.dp)
            .onFocusChanged { state ->
                isFocused = state.isFocused
            },

        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = CustomSearch,
            contentDescription = "Search",
            tint = Color.Black.copy(alpha = 0.6f)
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = "Search Chat",
                        fontSize = 16.sp,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
                innerTextField()
            }
        )
    }
}


@Composable
fun AddChatDialog(
    onDismissRequest: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    onClick: () -> Unit,
    ) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Add New Chat",
                    fontSize = 20.sp,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))

                NewChatEmailTextField(
                    value = value,
                    onValueChange = onValueChange,
                    errorMessage = errorMessage
                )
                Spacer(modifier = Modifier.height(8.dp))

                AddNewChatButton(
                    onClick = onClick,
                )
                Spacer(modifier = Modifier.height(16.dp))

            }

        }
    }
}


@Composable
fun NewChatEmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = {
            Text(stringResource(R.string.email_sign_in))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(R.string.email_icon_sign_in)
            )
        },
        isError = errorMessage?.isNotEmpty() ?: false,
        supportingText = {
            if (errorMessage?.isNotEmpty() ?: false) {
                Text(errorMessage)
            }
        },
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            Text(stringResource(R.string.enter_user_s_email))
        }
    )
}


@Composable
fun AddNewChatButton(
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
    ) {
        Text(
            text = "Add New Chat",
            fontSize = 20.sp
        )
    }
}
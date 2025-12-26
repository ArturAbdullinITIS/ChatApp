package com.example.chatapp.presentation.util

import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import androidx.compose.ui.text.intl.Locale as ComposeLocale
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale


fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
}

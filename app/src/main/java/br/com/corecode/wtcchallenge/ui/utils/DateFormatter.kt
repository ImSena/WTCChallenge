package br.com.corecode.wtcchallenge.ui.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

fun formatChatTimestamp(timestamp: Long): String {
    val locale = Locale("pt", "BR")
    val now = System.currentTimeMillis()

    if (DateUtils.isToday(timestamp)) {
        return SimpleDateFormat("HH:mm", locale).format(timestamp)
    }

    if (DateUtils.isToday(timestamp + DateUtils.DAY_IN_MILLIS)) {
        return "Ontem"
    }

    return SimpleDateFormat("dd/MM/yy", locale).format(timestamp)
}

fun formatBubbleTimestamp(timestamp: Long): String {
    val locale = Locale("pt", "BR")
    return SimpleDateFormat("HH:mm", locale).format(timestamp)
}
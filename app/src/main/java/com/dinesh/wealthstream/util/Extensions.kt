package com.dinesh.wealthstream.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

// Number formatting
fun Double.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(this)
}

fun Double.toPercentage(): String {
    return String.format("%.2f%%", this)
}

fun Double.formatWithSign(): String {
    val prefix = if (this >= 0) "+" else ""
    return "$prefix${String.format("%.2f", this)}"
}

// Date formatting
fun Long.toDateString(): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.US)
    return sdf.format(Date(this))
}

fun Long.toTimeString(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.US)
    return sdf.format(Date(this))
}

fun Long.toRelativeTime(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    return when {
        diff < 60_000 -> "Just now"
        diff < 3600_000 -> "${diff / 60_000}m ago"
        diff < 86400_000 -> "${diff / 3600_000}h ago"
        diff < 604800_000 -> "${diff / 86400_000}d ago"
        else -> toDateString()
    }
}

// String formatting
fun String.formatVolume(): String {
    return when {
        this.endsWith("M") || this.endsWith("B") -> this
        else -> {
            val num = this.toDoubleOrNull() ?: return this
            when {
                num >= 1_000_000_000 -> String.format("%.2fB", num / 1_000_000_000)
                num >= 1_000_000 -> String.format("%.2fM", num / 1_000_000)
                num >= 1_000 -> String.format("%.2fK", num / 1_000)
                else -> String.format("%.0f", num)
            }
        }
    }
}
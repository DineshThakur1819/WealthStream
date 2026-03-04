package com.dinesh.wealthstream.presentation.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dinesh.wealthstream.ui.theme.*

@Composable
fun PriceChangeIndicator(
    change: Double,
    changePercent: Double,
    modifier: Modifier = Modifier
) {
    val isPositive = change >= 0
    val color = if (isPositive) Green500 else Red500
    val prefix = if (isPositive) "+" else ""

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isPositive) Icons.Filled.Refresh/*TrendingUp*/ else Icons.Filled.Done/*TrendingDown*/,
            contentDescription = if (isPositive) "Gain" else "Loss",
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "$prefix${String.format("%.2f", change)} ($prefix${String.format("%.2f", changePercent)}%)",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }
}
package com.dinesh.wealthstream.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dinesh.wealthstream.domain.model.MarketIndex
import com.dinesh.wealthstream.ui.theme.*

// ✅ REMOVED: Duplicate MarketIndex data class - use domain.model.MarketIndex instead

@Composable
fun MarketOverviewCard(
    index: MarketIndex,  // ✅ Uses domain.model.MarketIndex
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        index.isPositive -> Brush.linearGradient(listOf(Green50, Green100))
        else -> Brush.linearGradient(listOf(Red50, Red100))
    }

    val borderColor = when {
        index.isPositive -> Green200
        else -> Red200
    }

    Card(
        modifier = modifier,
        shape = CustomShapes.roundedCorner16,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .padding(Dimensions.spacing12)
        ) {
            Column {
                Text(
                    text = index.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimensions.spacing4))
                Text(
                    text = String.format("%,.2f", index.value),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Dimensions.spacing4))
                Text(
                    text = "${if (index.isPositive) "+" else ""}${String.format("%.2f", index.changePercent)}%",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = if (index.isPositive) Green600 else Red600
                )
            }
        }
    }
}
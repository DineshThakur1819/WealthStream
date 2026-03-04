package com.dinesh.wealthstream.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dinesh.wealthstream.ui.theme.*

@Composable
fun StockCard(
//    stock: Stock,
    isInWatchlist: Boolean = false,
    onCardClick: () -> Unit = {},
    onWatchlistClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevation.level2
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.iconMedium)
        ) {
            // Top Row: Icon, Name, Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Icon + Name
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing12),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Stock Icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Purple600, Blue500)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "text = stock.symbol.first().toString()",
                            style = MaterialTheme.typography.titleLarge,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Symbol and Name
                    Column {
                        Text(
                            "text = stock.symbol",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "text = stock.name",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Right: Price and Change
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$${"stock.price"}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    PriceChangeIndicator(
                        0.90,8.90 // remember to change the values
                        /*change = stock.change,
                        changePercent = stock.changePercent*/
                    )
                }
            }

            // Divider
            HorizontalDivider(
                modifier = Modifier.padding(vertical = Dimensions.spacing12),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Bottom Row: Volume and Watchlist
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vol: ${"stock.volume"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                IconButton(onClick = onWatchlistClick) {
                    Icon(
                        imageVector = if (isInWatchlist) Icons.Filled.Star else Icons.Outlined.Star/*StarOutline*/,
                        contentDescription = if (isInWatchlist) "Remove from watchlist" else "Add to watchlist",
                        tint = if (isInWatchlist) Orange500 else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
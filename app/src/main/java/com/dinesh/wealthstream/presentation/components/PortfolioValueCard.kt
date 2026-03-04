package com.dinesh.wealthstream.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dinesh.wealthstream.ui.theme.*

@Composable
fun PortfolioValueCard(
    totalValue: Double,
    dailyChange: Double,
    dailyChangePercent: Double,
    onNotificationClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CustomShapes.roundedCorner24,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Purple600, Blue500)
                    )
                )
                .padding(Dimensions.paddingLarge)
        ) {
            Column {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = "Portfolio Value",
                            style = MaterialTheme.typography.bodyMedium,
                            color = White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(Dimensions.spacing4))
                        Text(
                            text = "$${String.format("%,.2f", totalValue)}",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }

                    IconButton(
                        onClick = onNotificationClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = White.copy(alpha = 0.2f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.spacing16))

                // Daily Change
                Surface(
                    shape = CustomShapes.roundedCorner12,
                    color = White.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = Dimensions.spacing12,
                            vertical = Dimensions.spacing8
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing8)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = null,
                            tint = Green300,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "+$${String.format("%.2f", dailyChange)} Today (+${String.format("%.2f", dailyChangePercent)}%)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = White
                        )
                    }
                }
            }
        }
    }
}
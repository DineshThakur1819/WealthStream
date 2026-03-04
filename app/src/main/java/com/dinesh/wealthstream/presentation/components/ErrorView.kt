package com.dinesh.wealthstream.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dinesh.wealthstream.ui.theme.Dimensions
import com.dinesh.wealthstream.ui.theme.*

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacing16),
            modifier = Modifier.padding(Dimensions.paddingLarge)
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Error",
                tint = Red500,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetry,
                shape = CustomShapes.roundedCorner12
            ) {
                Text("Retry")
            }
        }
    }
}

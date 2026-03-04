package com.dinesh.wealthstream.presentation.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun NoInternetBanner(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Red500)
                .padding(
                    horizontal = Dimensions.paddingMedium,
                    vertical = Dimensions.spacing12
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "No internet",
                tint = White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(Dimensions.spacing8))
            Text(
                text = "No internet connection",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = White
            )
        }
    }
}
package com.dinesh.wealthstream.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dinesh.wealthstream.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "alpha"
    )

    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2500)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Purple600, Blue500)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .alpha(alphaAnim.value)
                .offset(y = (-20).dp)
        ) {
            // App Icon/Logo
            Text(
                text = "📊",
                fontSize = (80 * scaleAnim.value).sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // App Name
            Text(
                text = "WealthStream",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Track. Invest. Grow.",
                fontSize = 18.sp,
                color = White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Version at bottom
        Text(
            text = "v1.0.0",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(alphaAnim.value),
            color = White.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
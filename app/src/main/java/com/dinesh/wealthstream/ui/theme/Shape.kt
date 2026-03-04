package com.dinesh.wealthstream.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val WealthStreamShapes = Shapes(
    // Extra Small - Small chips, badges
    extraSmall = RoundedCornerShape(4.dp),

    // Small - Buttons, small cards
    small = RoundedCornerShape(8.dp),

    // Medium - Cards, dialogs
    medium = RoundedCornerShape(12.dp),

    // Large - Bottom sheets, larger cards
    large = RoundedCornerShape(16.dp),

    // Extra Large - Full screen dialogs
    extraLarge = RoundedCornerShape(24.dp)
)

// Custom shapes for specific use cases
object CustomShapes {
    val roundedCorner8 = RoundedCornerShape(8.dp)
    val roundedCorner12 = RoundedCornerShape(12.dp)
    val roundedCorner16 = RoundedCornerShape(16.dp)
    val roundedCorner20 = RoundedCornerShape(20.dp)
    val roundedCorner24 = RoundedCornerShape(24.dp)
    val roundedCorner32 = RoundedCornerShape(32.dp)

    val topRounded16 = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val topRounded24 = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    val bottomRounded16 = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    val bottomRounded24 = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
}

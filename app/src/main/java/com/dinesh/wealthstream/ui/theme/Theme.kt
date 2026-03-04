package com.dinesh.wealthstream.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.compose.ui.platform.LocalView

/*
*
* */
private val LightColorScheme = lightColorScheme(
    // Primary colors
    primary = Purple600,
    onPrimary = White,
    primaryContainer = Purple100,
    onPrimaryContainer = Purple900,

    // Secondary colors
    secondary = Blue500,
    onSecondary = White,
    secondaryContainer = Blue100,
    onSecondaryContainer = Blue900,

    // Tertiary colors
    tertiary = Green500,
    onTertiary = White,
    tertiaryContainer = Green100,
    onTertiaryContainer = Green900,

    // Error colors
    error = Red500,
    onError = White,
    errorContainer = Red100,
    onErrorContainer = Red900,

    // Background colors
    background = Gray50,
    onBackground = Gray900,

    // Surface colors
    surface = White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray700,

    // Outline
    outline = Gray300,
    outlineVariant = Gray200,

    // Inverse colors
    inverseSurface = Gray900,
    inverseOnSurface = Gray50,
    inversePrimary = Purple300,

    // Surface tint
    surfaceTint = Purple600,

    // Scrim
    scrim = Black.copy(alpha = 0.32f)
)
/*
 Dark Color Scheme
* */
private val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary = Purple400,
    onPrimary = Purple900,
    primaryContainer = Purple700,
    onPrimaryContainer = Purple100,

    // Secondary colors
    secondary = Blue400,
    onSecondary = Blue900,
    secondaryContainer = Blue700,
    onSecondaryContainer = Blue100,

    // Tertiary colors
    tertiary = Green400,
    onTertiary = Green900,
    tertiaryContainer = Green700,
    onTertiaryContainer = Green100,

    // Error colors
    error = Red400,
    onError = Red900,
    errorContainer = Red700,
    onErrorContainer = Red100,

    // Background colors
    background = Gray900,
    onBackground = Gray50,

    // Surface colors
    surface = Gray800,
    onSurface = Gray50,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray300,

    // Outline
    outline = Gray600,
    outlineVariant = Gray700,

    // Inverse colors
    inverseSurface = Gray100,
    inverseOnSurface = Gray900,
    inversePrimary = Purple600,

    // Surface tint
    surfaceTint = Purple400,

    // Scrim
    scrim = Black.copy(alpha = 0.32f)
)

@Composable
fun WealthStreamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = WealthStreamTypography,
        shapes = WealthStreamShapes,
        content = content
    )
}

/*
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    */
/* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    *//*

)

@Composable
fun WealthStreamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}*/

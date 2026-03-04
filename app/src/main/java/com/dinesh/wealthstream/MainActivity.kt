package com.dinesh.wealthstream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dinesh.wealthstream.ui.theme.WealthStreamTheme
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dinesh.wealthstream.presentation.navigation.AppNavigation
import com.dinesh.wealthstream.presentation.onboarding.OnboardingScreen
import com.dinesh.wealthstream.presentation.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Enable edge-to-edge
        enableEdgeToEdge()

        setContent {
            WealthStreamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WealthStreamApp()
                }
            }
        }
    }
}

@Composable
fun WealthStreamApp() {
    var showSplash by remember { mutableStateOf(true) }
    var showOnboarding by remember { mutableStateOf(false) }

    when {
        showSplash -> {
            SplashScreen {
                showSplash = false
                // Check if user has seen onboarding before
                // For now, always show onboarding
                showOnboarding = true
            }
        }

        showOnboarding -> {
            OnboardingScreen {
                showOnboarding = false
            }
        }

        else -> {
            AppNavigation()
        }
    }
}
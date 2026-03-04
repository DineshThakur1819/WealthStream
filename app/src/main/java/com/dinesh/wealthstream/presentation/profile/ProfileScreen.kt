package com.dinesh.wealthstream.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dinesh.wealthstream.ui.theme.*

@Composable
fun ProfileScreen(
    onSettingClick: () -> Unit = {}  // ✅ ADDED: Optional parameter with default
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader()

        Column(
            modifier = Modifier.padding(Dimensions.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = Dimensions.spacing8)
            )

            SettingsItem(
                icon = Icons.Filled.Person,
                title = "Account Settings",
                onClick = onSettingClick
            )

            SettingsItem(
                icon = Icons.Filled.Notifications,
                title = "Notifications",
                onClick = {}
            )

            SettingsItem(
                icon = Icons.Filled.Notifications/*NotificationsActive*/,
                title = "Price Alerts",
                onClick = {}
            )

            var darkModeEnabled by remember { mutableStateOf(false) }
            SettingsItemWithSwitch(
                icon = Icons.Filled.Person/*DarkMode*/,
                title = "Dark Mode",
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )

            SettingsItem(
                icon = Icons.Filled.Settings/*Help*/,
                title = "Help & Support",
                onClick = {}
            )

            SettingsItem(
                icon = Icons.Filled.Info,
                title = "About",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(Dimensions.spacing16))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red500
                ),
                shape = CustomShapes.roundedCorner16
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(Dimensions.spacing8))
                Text("Sign Out", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ProfileHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CustomShapes.bottomRounded24,
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CustomShapes.roundedCorner24,
                    color = White.copy(alpha = 0.2f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "JD",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.spacing16))

                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = White
                )

                Text(
                    text = "john.doe@email.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = CustomShapes.roundedCorner16
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.paddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingsItemWithSwitch(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CustomShapes.roundedCorner16
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.paddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}
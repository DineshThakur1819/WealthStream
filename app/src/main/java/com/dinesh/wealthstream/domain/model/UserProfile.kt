package com.dinesh.wealthstream.domain.model


data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val avatarUrl: String? = null,
    val preferences: UserPreferences = UserPreferences()
)

data class UserPreferences(
    val enableNotifications: Boolean = true,
    val enablePriceAlerts: Boolean = true,
    val darkMode: Boolean = false,
    val defaultTimeRange: TimeRange = TimeRange.ONE_DAY,
    val currency: String = "USD"
)
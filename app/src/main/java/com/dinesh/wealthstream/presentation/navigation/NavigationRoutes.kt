package com.dinesh.wealthstream.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Dashboard : Route

    @Serializable
    data object Search : Route

    @Serializable
    data object Watchlist : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data class StockDetail(val symbol: String) : Route
}
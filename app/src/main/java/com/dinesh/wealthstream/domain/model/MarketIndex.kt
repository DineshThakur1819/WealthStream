package com.dinesh.wealthstream.domain.model


data class MarketIndex(
    val name: String,
    val symbol: String,
    val value: Double,
    val change: Double,
    val changePercent: Double
) {
    val isPositive: Boolean
        get() = change >= 0
}
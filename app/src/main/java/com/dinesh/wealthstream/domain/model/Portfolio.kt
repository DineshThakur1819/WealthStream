package com.dinesh.wealthstream.domain.model


data class Portfolio(
    val totalValue: Double,
    val dailyChange: Double,
    val dailyChangePercent: Double,
    val stocks: List<PortfolioStock> = emptyList()
)

data class PortfolioStock(
    val stock: Stock,
    val quantity: Int,
    val averageCost: Double,
    val currentValue: Double,
    val totalGainLoss: Double,
    val gainLossPercent: Double
)
package com.dinesh.wealthstream.domain.model


data class PriceAlert(
    val id: String,
    val stockSymbol: String,
    val targetPrice: Double,
    val condition: AlertCondition,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

enum class AlertCondition {
    ABOVE,
    BELOW,
    PERCENT_GAIN,
    PERCENT_LOSS
}
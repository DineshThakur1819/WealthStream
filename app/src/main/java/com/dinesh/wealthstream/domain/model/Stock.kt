package com.dinesh.wealthstream.domain.model

data class Stock(
    val id: String,
    val symbol: String,
    val name: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
    val volume: String,
    val marketCap: String? = null,
    val peRatio: Double? = null,
    val high52Week: Double? = null,
    val low52Week: Double? = null,
    val openPrice: Double? = null,
    val previousClose: Double? = null,
    val timestamp: Long = System.currentTimeMillis()
) {
    val isGain: Boolean
        get() = change >= 0
}
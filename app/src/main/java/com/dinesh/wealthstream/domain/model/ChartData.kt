package com.dinesh.wealthstream.domain.model


data class ChartData(
    val prices: List<PricePoint>,
    val timeRange: TimeRange
)

data class PricePoint(
    val timestamp: Long,
    val price: Double,
    val volume: Long? = null
)

enum class TimeRange {
    ONE_DAY,
    ONE_WEEK,
    ONE_MONTH,
    THREE_MONTHS,
    SIX_MONTHS,
    ONE_YEAR,
    FIVE_YEARS,
    ALL
}
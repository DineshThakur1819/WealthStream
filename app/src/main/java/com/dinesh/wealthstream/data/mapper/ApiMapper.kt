package com.dinesh.wealthstream.data.mapper

import com.dinesh.wealthstream.data.local.entity.*
import com.dinesh.wealthstream.data.remote.dto.*
import com.dinesh.wealthstream.domain.model.*
import java.util.UUID

/*fun QuoteResponse.toStockEntity(symbol: String, name: String): StockEntity {
    return StockEntity(
        id = UUID.randomUUID().toString(),
        symbol = symbol,
        name = name,
        price = currentPrice,
        change = change,
        changePercent = percentChange,
        volume = formatVolume(highPrice, lowPrice),
        openPrice = openPrice,
        previousClose = previousClose,
        timestamp = System.currentTimeMillis()
    )
}*/

/*fun QuoteResponse.toStock(symbol: String, profile: CompanyProfile?): Stock {
    return Stock(
        id = UUID.randomUUID().toString(),
        symbol = symbol,
        name = profile?.name ?: symbol,
        price = currentPrice,
        change = change,
        changePercent = percentChange,
        volume = formatVolume(highPrice, lowPrice),
        marketCap = profile?.marketCap?.let { formatMarketCap(it) },
        openPrice = openPrice,
        previousClose = previousClose,
        timestamp = System.currentTimeMillis()
    )
}*/

private fun formatVolume(high: Double, low: Double): String {
    val avg = (high + low) / 2
    val volume = (avg * 1000000).toLong()
    return when {
        volume >= 1_000_000_000 -> "${volume / 1_000_000_000}B"
        volume >= 1_000_000 -> "${volume / 1_000_000}M"
        volume >= 1_000 -> "${volume / 1_000}K"
        else -> volume.toString()
    }
}

private fun formatMarketCap(marketCap: Double): String {
    return when {
        marketCap >= 1000 -> String.format("%.2fT", marketCap / 1000)
        marketCap >= 1 -> String.format("%.2fB", marketCap)
        else -> String.format("%.2fM", marketCap * 1000)
    }
}
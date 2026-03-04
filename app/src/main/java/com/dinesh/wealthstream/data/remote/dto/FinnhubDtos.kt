package com.dinesh.wealthstream.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    @SerialName("c") val currentPrice: Double = 0.0,      // Current price
    @SerialName("d") val change: Double = 0.0,            // Change
    @SerialName("dp") val percentChange: Double = 0.0,    // Percent change
    @SerialName("h") val highPrice: Double = 0.0,         // High price of the day
    @SerialName("l") val lowPrice: Double = 0.0,          // Low price of the day
    @SerialName("o") val openPrice: Double = 0.0,         // Open price
    @SerialName("pc") val previousClose: Double = 0.0,    // Previous close price
    @SerialName("t") val timestamp: Long = 0L             // Timestamp
)

@Serializable
data class CompanyProfile(
    @SerialName("name") val name: String = "",
    @SerialName("ticker") val ticker: String = "",
    @SerialName("marketCapitalization") val marketCap: Double = 0.0,
    @SerialName("shareOutstanding") val sharesOutstanding: Double = 0.0,
    @SerialName("country") val country: String = "",
    @SerialName("currency") val currency: String = "",
    @SerialName("exchange") val exchange: String = "",
    @SerialName("finnhubIndustry") val industry: String = "",
    @SerialName("weburl") val webUrl: String = "",
    @SerialName("logo") val logo: String = ""
)

@Serializable
data class CandleData(
    @SerialName("c") val closePrices: List<Double> = emptyList(),
    @SerialName("h") val highPrices: List<Double> = emptyList(),
    @SerialName("l") val lowPrices: List<Double> = emptyList(),
    @SerialName("o") val openPrices: List<Double> = emptyList(),
    @SerialName("t") val timestamps: List<Long> = emptyList(),
    @SerialName("v") val volumes: List<Long> = emptyList(),
    @SerialName("s") val status: String = ""
)
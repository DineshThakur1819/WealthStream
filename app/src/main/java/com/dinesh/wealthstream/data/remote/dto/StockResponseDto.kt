package com.dinesh.wealthstream.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockQuoteDto(
    @SerialName("c") val currentPrice: Double,
    @SerialName("d") val change: Double,
    @SerialName("dp") val percentChange: Double,
    @SerialName("h") val highPrice: Double,
    @SerialName("l") val lowPrice: Double,
    @SerialName("o") val openPrice: Double,
    @SerialName("pc") val previousClose: Double
)

@Serializable
data class CompanyProfileDto(
    @SerialName("name") val name: String,
    @SerialName("ticker") val ticker: String,
    @SerialName("exchange") val exchange: String,
    @SerialName("marketCapitalization") val marketCap: Double?,
    @SerialName("shareOutstanding") val sharesOutstanding: Double?,
    @SerialName("country") val country: String?,
    @SerialName("currency") val currency: String?,
    @SerialName("finnhubIndustry") val industry: String?,
    @SerialName("weburl") val webUrl: String?,
    @SerialName("logo") val logo: String?
)

@Serializable
data class MarketNewsDto(
    @SerialName("category") val category: String,
    @SerialName("datetime") val datetime: Long,
    @SerialName("headline") val headline: String,
    @SerialName("id") val id: Long,
    @SerialName("image") val image: String?,
    @SerialName("related") val related: String?,
    @SerialName("source") val source: String,
    @SerialName("summary") val summary: String,
    @SerialName("url") val url: String
)
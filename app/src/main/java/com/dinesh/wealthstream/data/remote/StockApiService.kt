package com.dinesh.wealthstream.data.remote

import com.dinesh.wealthstream.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApiService {
    // Get real-time quote for a symbol
    @GET("quote")
    suspend fun getQuote(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Response<QuoteResponse>

    // Get company profile
    @GET("stock/profile2")
    suspend fun getCompanyProfile(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Response<CompanyProfile>

    // Get historical candle data
    @GET("stock/candle")
    suspend fun getCandles(
        @Query("symbol") symbol: String,
        @Query("resolution") resolution: String, // 1, 5, 15, 30, 60, D, W, M
        @Query("from") from: Long,
        @Query("to") to: Long,
        @Query("token") token: String
    ): Response<CandleData>

    @GET("quote")
    suspend fun getStockQuote(
        @Query("symbol") symbol: String
    ): Response<StockQuoteDto>

    @GET("stock/profile2")
    suspend fun getCompanyProfile(
        @Query("symbol") symbol: String
    ): Response<CompanyProfileDto>

    @GET("news")
    suspend fun getMarketNews(
        @Query("category") category: String = "general"
    ): Response<List<MarketNewsDto>>

    @GET("company-news")
    suspend fun getCompanyNews(
        @Query("symbol") symbol: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<List<MarketNewsDto>>

    @GET("stock/candle")
    suspend fun getStockCandles(
        @Query("symbol") symbol: String,
        @Query("resolution") resolution: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ): Response<CandleDataDto>
}

@kotlinx.serialization.Serializable
data class CandleDataDto(
    @kotlinx.serialization.SerialName("c") val closePrices: List<Double>,
    @kotlinx.serialization.SerialName("h") val highPrices: List<Double>,
    @kotlinx.serialization.SerialName("l") val lowPrices: List<Double>,
    @kotlinx.serialization.SerialName("o") val openPrices: List<Double>,
    @kotlinx.serialization.SerialName("t") val timestamps: List<Long>,
    @kotlinx.serialization.SerialName("v") val volumes: List<Long>,
    @kotlinx.serialization.SerialName("s") val status: String
)
package com.dinesh.wealthstream.domain.repository

import com.dinesh.wealthstream.domain.model.*
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getTrendingStocks(): Flow<List<Stock>>
    fun getStockDetail(symbol: String): Flow<StockDetail>
    fun searchStocks(query: String): Flow<List<SearchResult>>
    fun getPortfolio(): Flow<Portfolio>
    fun getMarketIndices(): Flow<List<MarketIndex>>
    fun getChartData(symbol: String, timeRange: TimeRange): Flow<ChartData>
    suspend fun refreshStocks()
}
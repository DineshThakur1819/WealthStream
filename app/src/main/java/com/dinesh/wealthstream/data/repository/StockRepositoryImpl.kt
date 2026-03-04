package com.dinesh.wealthstream.data.repository

import com.dinesh.wealthstream.data.local.dao.*
import com.dinesh.wealthstream.data.mapper.toDomain
import com.dinesh.wealthstream.data.mapper.toEntity
import com.dinesh.wealthstream.data.mapper.toStock
import com.dinesh.wealthstream.data.remote.*
import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.domain.repository.*
import com.dinesh.wealthstream.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val apiService: StockApiService,
    private val stockDao: StockDao
) : StockRepository {

    override fun getTrendingStocks(): Flow<List<Stock>> = flow {
        try {
            // 1. First emit cached data if available
            val cachedStocks = stockDao.getAllStocks()
            if (cachedStocks.isNotEmpty()) {
                Timber.d("Emitting ${cachedStocks.size} cached stocks")
                emit(cachedStocks.map { it.toDomain() })
            }

            // 2. Fetch fresh data from API
            Timber.d("Fetching fresh stock data from API...")
            val freshStocks = fetchStocksFromApi()

            if (freshStocks.isNotEmpty()) {
                // 3. Save to database
                stockDao.deleteAllStocks()
                stockDao.insertStocks(freshStocks.map { it.toEntity() })

                // 4. Emit fresh data
                Timber.d("Emitting ${freshStocks.size} fresh stocks from API")
                emit(freshStocks)
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching stocks")
            // If API fails, try to emit cached data one more time
            val cachedStocks = stockDao.getAllStocks()
            if (cachedStocks.isNotEmpty()) {
                emit(cachedStocks.map { it.toDomain() })
            } else {
                throw e
            }
        }
    }

    private suspend fun fetchStocksFromApi(): List<Stock> = withContext(Dispatchers.IO) {
        val stocks = Constants.DEFAULT_STOCKS.map { symbol ->
            async {
                try {
                    // Fetch quote
                    val quoteResponse = apiService.getQuote(symbol, Constants.API_KEY)

                    if (quoteResponse.isSuccessful && quoteResponse.body() != null) {
                        val quote = quoteResponse.body()!!

                        // Fetch company profile for name and additional info
                        val profileResponse = apiService.getCompanyProfile(symbol, Constants.API_KEY)
                        val profile = if (profileResponse.isSuccessful) {
                            profileResponse.body()
                        } else null

                        quote.toStock(symbol, profile)
                    } else {
                        Timber.w("Failed to fetch quote for $symbol: ${quoteResponse.code()}")
                        null
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Error fetching stock $symbol")
                    null
                }
            }
        }.awaitAll().filterNotNull()

        Timber.d("Fetched ${stocks.size} stocks from API")
        stocks
    }

    override fun getStockDetail(symbol: String): Flow<StockDetail> = flow {
        try {
            // Fetch quote
            val quoteResponse = apiService.getQuote(symbol, Constants.API_KEY)
            val profileResponse = apiService.getCompanyProfile(symbol, Constants.API_KEY)

            if (quoteResponse.isSuccessful && quoteResponse.body() != null) {
                val quote = quoteResponse.body()!!
                val profile = profileResponse.body()

                val stock = quote.toStock(symbol, profile)

                emit(StockDetail(
                    stock = stock,
                    description = "Company description not available via Finnhub free tier",
                    sector = "Technology",
                    industry = profile?.industry ?: "Unknown",
                    ceo = null,
                    employees = null,
                    website = profile?.webUrl,
                    dividendYield = null,
                    eps = null,
                    beta = null
                ))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching stock detail for $symbol")
            throw e
        }
    }

    override fun getMarketIndices(): Flow<List<MarketIndex>> = flow {
        try {
            // Fetch major indices
            val indices = listOf(
                "^GSPC" to "S&P 500",      // S&P 500
                "^IXIC" to "NASDAQ",       // NASDAQ
                "^DJI" to "DOW"            // Dow Jones
            )

            val marketIndices = indices.mapNotNull { (symbol, name) ->
                try {
                    val response = apiService.getQuote(symbol, Constants.API_KEY)
                    if (response.isSuccessful && response.body() != null) {
                        val quote = response.body()!!
                        MarketIndex(
                            name = name,
                            symbol = symbol,
                            value = quote.currentPrice,
                            change = quote.change,
                            changePercent = quote.percentChange
                        )
                    } else null
                } catch (e: Exception) {
                    Timber.e(e, "Error fetching index $symbol")
                    null
                }
            }

            emit(marketIndices.ifEmpty { SampleData.marketIndices })
        } catch (e: Exception) {
            Timber.e(e, "Error fetching market indices")
            emit(SampleData.marketIndices)
        }
    }

    override fun getPortfolio(): Flow<Portfolio> = flow {
        // For now, return sample portfolio
        // In production, this would calculate from user's actual holdings
        emit(SampleData.portfolio)
    }

    override fun searchStocks(query: String): Flow<List<SearchResult>> = flow {
        // Finnhub free tier doesn't support search
        // Filter from our default stocks
        val stocks = fetchStocksFromApi()
        val results = stocks
            .filter {
                it.symbol.contains(query, ignoreCase = true) ||
                        it.name.contains(query, ignoreCase = true)
            }
            .map {
                SearchResult(
                    symbol = it.symbol,
                    name = it.name,
                    type = "Stock",
                    exchange = "NASDAQ"
                )
            }
        emit(results)
    }

    override fun getChartData(symbol: String, timeRange: TimeRange): Flow<ChartData> = flow {
        try {
            val resolution = when (timeRange) {
                TimeRange.ONE_DAY -> "5"      // 5 min intervals
                TimeRange.ONE_WEEK -> "30"    // 30 min intervals
                TimeRange.ONE_MONTH -> "D"    // Daily
                TimeRange.THREE_MONTHS -> "D"
                TimeRange.SIX_MONTHS -> "D"
                TimeRange.ONE_YEAR -> "W"     // Weekly
                TimeRange.FIVE_YEARS -> "M"   // Monthly
                TimeRange.ALL -> "M"
            }

            val to = System.currentTimeMillis() / 1000
            val from = when (timeRange) {
                TimeRange.ONE_DAY -> to - (24 * 60 * 60)
                TimeRange.ONE_WEEK -> to - (7 * 24 * 60 * 60)
                TimeRange.ONE_MONTH -> to - (30 * 24 * 60 * 60)
                TimeRange.THREE_MONTHS -> to - (90 * 24 * 60 * 60)
                TimeRange.SIX_MONTHS -> to - (180 * 24 * 60 * 60)
                TimeRange.ONE_YEAR -> to - (365 * 24 * 60 * 60)
                TimeRange.FIVE_YEARS -> to - (5 * 365 * 24 * 60 * 60)
                TimeRange.ALL -> to - (10 * 365 * 24 * 60 * 60)
            }

            val response = apiService.getCandles(symbol, resolution, from, to, Constants.API_KEY)

            if (response.isSuccessful && response.body() != null) {
                val candles = response.body()!!

                if (candles.status == "ok" && candles.timestamps.isNotEmpty()) {
                    val pricePoints = candles.timestamps.indices.map { i ->
                        PricePoint(
                            timestamp = candles.timestamps[i] * 1000,
                            price = candles.closePrices[i],
                            volume = candles.volumes.getOrNull(i)
                        )
                    }

                    emit(ChartData(prices = pricePoints, timeRange = timeRange))
                    return@flow
                }
            }

            // Fallback to sample data
            emit(ChartData(
                prices = generateSamplePrices(timeRange),
                timeRange = timeRange
            ))
        } catch (e: Exception) {
            Timber.e(e, "Error fetching chart data")
            emit(ChartData(
                prices = generateSamplePrices(timeRange),
                timeRange = timeRange
            ))
        }
    }

    override suspend fun refreshStocks() {
        try {
            val freshStocks = fetchStocksFromApi()
            if (freshStocks.isNotEmpty()) {
                stockDao.deleteAllStocks()
                stockDao.insertStocks(freshStocks.map { it.toEntity() })
                Timber.d("Refreshed ${freshStocks.size} stocks")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing stocks")
        }
    }

    private fun generateSamplePrices(timeRange: TimeRange): List<PricePoint> {
        val basePrice = 150.0
        val dataPoints = when (timeRange) {
            TimeRange.ONE_DAY -> 24
            TimeRange.ONE_WEEK -> 7
            TimeRange.ONE_MONTH -> 30
            TimeRange.THREE_MONTHS -> 90
            TimeRange.SIX_MONTHS -> 180
            TimeRange.ONE_YEAR -> 365
            TimeRange.FIVE_YEARS -> 60
            TimeRange.ALL -> 120
        }

        return (0 until dataPoints).map { i ->
            PricePoint(
                timestamp = System.currentTimeMillis() - (i * 86400000L),
                price = basePrice + (Math.random() * 20 - 10),
                volume = (1000000..5000000).random().toLong()
            )
        }.reversed()
    }
}
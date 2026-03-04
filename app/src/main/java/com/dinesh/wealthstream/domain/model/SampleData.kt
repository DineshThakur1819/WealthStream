// ========================================
// SAMPLE DATA for Testing/Preview
// ========================================

package com.dinesh.wealthstream.domain.model

object SampleData {
    val stocks = listOf(
        Stock(
            id = "1",
            symbol = "AAPL",
            name = "Apple Inc.",
            price = 178.25,
            change = 2.5,
            changePercent = 1.42,
            volume = "52.3M",
            marketCap = "2.85T",
            peRatio = 28.45,
            high52Week = 198.23,
            low52Week = 124.17
        ),
        Stock(
            id = "2",
            symbol = "GOOGL",
            name = "Alphabet Inc.",
            price = 142.80,
            change = -1.2,
            changePercent = -0.83,
            volume = "28.1M",
            marketCap = "1.78T",
            peRatio = 25.32,
            high52Week = 156.45,
            low52Week = 102.34
        ),
        Stock(
            id = "3",
            symbol = "MSFT",
            name = "Microsoft Corp.",
            price = 378.91,
            change = 5.3,
            changePercent = 1.42,
            volume = "31.2M",
            marketCap = "2.82T",
            peRatio = 32.18,
            high52Week = 398.54,
            low52Week = 245.67
        ),
        Stock(
            id = "4",
            symbol = "TSLA",
            name = "Tesla Inc.",
            price = 242.64,
            change = -3.8,
            changePercent = -1.54,
            volume = "89.5M",
            marketCap = "770.5B",
            peRatio = 65.43,
            high52Week = 299.29,
            low52Week = 138.80
        ),
        Stock(
            id = "5",
            symbol = "AMZN",
            name = "Amazon.com Inc.",
            price = 178.35,
            change = 1.8,
            changePercent = 1.02,
            volume = "45.7M",
            marketCap = "1.84T",
            peRatio = 58.92,
            high52Week = 191.70,
            low52Week = 118.35
        ),
        Stock(
            id = "6",
            symbol = "NVDA",
            name = "NVIDIA Corp.",
            price = 495.22,
            change = 8.4,
            changePercent = 1.73,
            volume = "38.9M",
            marketCap = "1.22T",
            peRatio = 78.45,
            high52Week = 502.66,
            low52Week = 212.41
        )
    )

    val marketIndices = listOf(
        MarketIndex(
            name = "S&P 500",
            symbol = "SPX",
            value = 4783.45,
            change = 27.56,
            changePercent = 0.58
        ),
        MarketIndex(
            name = "NASDAQ",
            symbol = "IXIC",
            value = 15282.01,
            change = 139.23,
            changePercent = 0.92
        ),
        MarketIndex(
            name = "DOW",
            symbol = "DJI",
            value = 37545.33,
            change = -86.45,
            changePercent = -0.23
        )
    )

    val portfolio = Portfolio(
        totalValue = 124580.50,
        dailyChange = 2340.80,
        dailyChangePercent = 1.92,
        stocks = listOf(
            PortfolioStock(
                stock = stocks[0],
                quantity = 50,
                averageCost = 150.0,
                currentValue = 8912.50,
                totalGainLoss = 1412.50,
                gainLossPercent = 18.83
            ),
            PortfolioStock(
                stock = stocks[2],
                quantity = 25,
                averageCost = 320.0,
                currentValue = 9472.75,
                totalGainLoss = 1472.75,
                gainLossPercent = 18.41
            )
        )
    )

    val news = listOf(
        News(
            id = "1",
            title = "Apple announces new AI features",
            description = "Apple unveils groundbreaking AI capabilities...",
            source = "TechCrunch",
            url = "https://techcrunch.com/...",
            imageUrl = null,
            publishedAt = System.currentTimeMillis() - 3600000,
            relatedStocks = listOf("AAPL")
        ),
        News(
            id = "2",
            title = "Tesla stock surges on new Model 3 sales",
            description = "Tesla reports record-breaking sales numbers...",
            source = "Bloomberg",
            url = "https://bloomberg.com/...",
            imageUrl = null,
            publishedAt = System.currentTimeMillis() - 7200000,
            relatedStocks = listOf("TSLA")
        )
    )

    val userProfile = UserProfile(
        id = "user123",
        name = "John Doe",
        email = "john.doe@email.com",
        avatarUrl = null,
        preferences = UserPreferences(
            enableNotifications = true,
            enablePriceAlerts = true,
            darkMode = false
        )
    )
}
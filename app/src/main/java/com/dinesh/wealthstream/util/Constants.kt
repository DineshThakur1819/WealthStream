package com.dinesh.wealthstream.util


object Constants {
    // API
    const val BASE_URL = "https://finnhub.io/api/v1/"
    const val API_KEY = "d6g11h1r01qqnmbqc500d6g11h1r01qqnmbqc50g"

    // Alternative: Alpha Vantage (if you prefer)
    // const val BASE_URL = "https://www.alphavantage.co/query?"
    // const val API_KEY = "YOUR_ALPHAVANTAGE_KEY"

    // Popular stock symbols to fetch
    val DEFAULT_STOCKS = listOf(
        "AAPL", "GOOGL", "MSFT", "AMZN", "TSLA",
        "META", "NVDA", "AMD", "NFLX", "DIS"
    )

    // Database
    const val DATABASE_NAME = "wealthstream_database"
    const val DATABASE_VERSION = 1

    // Network
    const val NETWORK_TIMEOUT = 30L // seconds
    const val CONNECT_TIMEOUT = 30L // seconds
    const val READ_TIMEOUT = 30L // seconds

    // WorkManager
    const val WORK_NAME_PRICE_CHECK = "price_check_worker"
    const val WORK_NAME_DATA_SYNC = "data_sync_worker"
    const val WORK_INTERVAL_HOURS = 1L
    const val SYNC_INTERVAL_MINUTES = 30L

    // Preferences
    const val PREFS_NAME = "wealthstream_prefs"
    const val KEY_DARK_MODE = "dark_mode"
    const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"

    // Notification
    const val NOTIFICATION_CHANNEL_NAME = "Stock Price Alerts"
    const val NOTIFICATION_CHANNEL_DESC = "Notifications for stock price changes"
    const val NOTIFICATION_CHANNEL_ID = "wealthstream_channel"

    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE = 40

    // Cache
    const val CACHE_TIMEOUT_MINUTES = 5L
}
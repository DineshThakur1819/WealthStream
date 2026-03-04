package com.dinesh.wealthstream.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dinesh.wealthstream.data.local.dao.*
import com.dinesh.wealthstream.data.local.entity.*

@Database(
    entities = [
        StockEntity::class,
        WatchlistEntity::class,
        PortfolioStockEntity::class,
        PriceAlertEntity::class,
        NewsEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
    abstract fun watchlistDao(): WatchlistDao
    abstract fun portfolioDao(): PortfolioDao
    abstract fun priceAlertDao(): PriceAlertDao
    abstract fun newsDao(): NewsDao
}
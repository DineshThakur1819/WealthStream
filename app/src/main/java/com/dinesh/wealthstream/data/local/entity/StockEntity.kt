package com.dinesh.wealthstream.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class StockEntity(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
    val volume: String,
    val marketCap: String? = null,
    val peRatio: Double? = null,
    val high52Week: Double? = null,
    val low52Week: Double? = null,
    val openPrice: Double? = null,
    val previousClose: Double? = null,
    val timestamp: Long = System.currentTimeMillis()
)
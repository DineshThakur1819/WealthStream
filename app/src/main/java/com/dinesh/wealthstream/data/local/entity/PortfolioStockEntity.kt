package com.dinesh.wealthstream.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio_stocks")
data class PortfolioStockEntity(
    @PrimaryKey
    val id: String,
    val stockSymbol: String,
    val quantity: Int,
    val averageCost: Double,
    val purchaseDate: Long,
    val lastUpdated: Long = System.currentTimeMillis()
)
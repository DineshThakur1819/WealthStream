package com.dinesh.wealthstream.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_alerts")
data class PriceAlertEntity(
    @PrimaryKey
    val id: String,
    val stockSymbol: String,
    val targetPrice: Double,
    val condition: String, // "ABOVE" or "BELOW"
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
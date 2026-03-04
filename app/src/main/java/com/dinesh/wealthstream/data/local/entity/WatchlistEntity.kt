package com.dinesh.wealthstream.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey
    val id: String,
    val stockSymbol: String,
    val addedAt: Long = System.currentTimeMillis()
)
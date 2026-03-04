package com.dinesh.wealthstream.domain.model

data class Watchlist(
    val id: String,
    val stockSymbol: String,
    val addedAt: Long = System.currentTimeMillis()
)
package com.dinesh.wealthstream.domain.repository

import com.dinesh.wealthstream.domain.model.Watchlist
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun getWatchlist(): Flow<List<Watchlist>>
    suspend fun addToWatchlist(symbol: String)
    suspend fun removeFromWatchlist(symbol: String)
    suspend fun isInWatchlist(symbol: String): Boolean
}
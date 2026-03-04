package com.dinesh.wealthstream.data.repository

import com.dinesh.wealthstream.data.local.dao.WatchlistDao
import com.dinesh.wealthstream.data.local.entity.WatchlistEntity
import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistRepositoryImpl @Inject constructor(
    private val watchlistDao: WatchlistDao
) : WatchlistRepository {

    override fun getWatchlist(): Flow<List<Watchlist>> {
        return watchlistDao.getAllWatchlist().map { entities ->
            entities.map {
                Watchlist(
                    id = it.id, stockSymbol = it.stockSymbol, addedAt = it.addedAt
                )
            }
        }
    }

    override suspend fun addToWatchlist(symbol: String) {
        val entity = WatchlistEntity(
            id = System.currentTimeMillis().toString(),
            stockSymbol = symbol,
            addedAt = System.currentTimeMillis()
        )
        watchlistDao.insertWatchlist(entity)
    }

    override suspend fun removeFromWatchlist(symbol: String) {
        watchlistDao.deleteWatchlistBySymbol(symbol)
    }

    override suspend fun isInWatchlist(symbol: String): Boolean {
        return watchlistDao.isInWatchlist(symbol)
    }
}
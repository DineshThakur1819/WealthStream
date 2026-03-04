package com.dinesh.wealthstream.data.local.dao


import androidx.room.*
import com.dinesh.wealthstream.data.local.entity.WatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY addedAt DESC")
    fun getAllWatchlist(): Flow<List<WatchlistEntity>>

    @Query("SELECT * FROM watchlist WHERE stockSymbol = :symbol LIMIT 1")
    suspend fun getWatchlistBySymbol(symbol: String): WatchlistEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE stockSymbol = :symbol)")
    suspend fun isInWatchlist(symbol: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE stockSymbol = :symbol)")
    fun observeIsInWatchlist(symbol: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlist(watchlist: WatchlistEntity)

    @Delete
    suspend fun deleteWatchlist(watchlist: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE stockSymbol = :symbol")
    suspend fun deleteWatchlistBySymbol(symbol: String)

    @Query("DELETE FROM watchlist")
    suspend fun deleteAllWatchlist()

    @Query("SELECT COUNT(*) FROM watchlist")
    suspend fun getWatchlistCount(): Int
}
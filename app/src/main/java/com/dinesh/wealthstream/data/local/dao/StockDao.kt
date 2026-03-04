package com.dinesh.wealthstream.data.local.dao


import androidx.room.*
import com.dinesh.wealthstream.data.local.entity.StockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Query("SELECT * FROM stocks ORDER BY timestamp DESC")
    fun getAllStocks(): List<StockEntity>

    @Query("SELECT * FROM stocks ORDER BY timestamp DESC")
    fun observeAllStocks(): Flow<List<StockEntity>>

    @Query("SELECT * FROM stocks WHERE symbol = :symbol LIMIT 1")
    suspend fun getStockBySymbol(symbol: String): StockEntity?

    @Query("SELECT * FROM stocks WHERE symbol = :symbol LIMIT 1")
    fun observeStockBySymbol(symbol: String): Flow<StockEntity?>

    @Query("SELECT * FROM stocks WHERE symbol IN (:symbols)")
    suspend fun getStocksBySymbols(symbols: List<String>): List<StockEntity>

    @Query("SELECT * FROM stocks WHERE symbol IN (:symbols)")
    fun observeStocksBySymbols(symbols: List<String>): Flow<List<StockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(stocks: List<StockEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StockEntity)

    @Update
    suspend fun updateStock(stock: StockEntity)

    @Delete
    suspend fun deleteStock(stock: StockEntity)

    @Query("DELETE FROM stocks")
    suspend fun deleteAllStocks()

    @Query("DELETE FROM stocks WHERE timestamp < :timestamp")
    suspend fun deleteOldStocks(timestamp: Long)

    @Query("SELECT COUNT(*) FROM stocks")
    suspend fun getStockCount(): Int
}
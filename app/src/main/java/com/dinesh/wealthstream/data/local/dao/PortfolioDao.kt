package com.dinesh.wealthstream.data.local.dao


import androidx.room.*
import com.dinesh.wealthstream.data.local.entity.PortfolioStockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolio_stocks ORDER BY lastUpdated DESC")
    fun getAllPortfolioStocks(): Flow<List<PortfolioStockEntity>>

    @Query("SELECT * FROM portfolio_stocks WHERE stockSymbol = :symbol LIMIT 1")
    suspend fun getPortfolioStockBySymbol(symbol: String): PortfolioStockEntity?

    @Query("SELECT * FROM portfolio_stocks WHERE stockSymbol = :symbol LIMIT 1")
    fun observePortfolioStockBySymbol(symbol: String): Flow<PortfolioStockEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortfolioStock(portfolioStock: PortfolioStockEntity)

    @Update
    suspend fun updatePortfolioStock(portfolioStock: PortfolioStockEntity)

    @Delete
    suspend fun deletePortfolioStock(portfolioStock: PortfolioStockEntity)

    @Query("DELETE FROM portfolio_stocks WHERE stockSymbol = :symbol")
    suspend fun deletePortfolioStockBySymbol(symbol: String)

    @Query("DELETE FROM portfolio_stocks")
    suspend fun deleteAllPortfolioStocks()

    @Query("SELECT SUM(quantity * averageCost) FROM portfolio_stocks")
    suspend fun getTotalInvestment(): Double?

    @Query("SELECT COUNT(*) FROM portfolio_stocks")
    suspend fun getPortfolioCount(): Int
}
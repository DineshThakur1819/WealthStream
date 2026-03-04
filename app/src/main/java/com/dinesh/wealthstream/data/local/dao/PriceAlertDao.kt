package com.dinesh.wealthstream.data.local.dao


import androidx.room.*
import com.dinesh.wealthstream.data.local.entity.PriceAlertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceAlertDao {
    @Query("SELECT * FROM price_alerts ORDER BY createdAt DESC")
    fun getAllPriceAlerts(): Flow<List<PriceAlertEntity>>

    @Query("SELECT * FROM price_alerts WHERE isActive = 1")
    fun getActivePriceAlerts(): Flow<List<PriceAlertEntity>>

    @Query("SELECT * FROM price_alerts WHERE stockSymbol = :symbol")
    fun getPriceAlertsBySymbol(symbol: String): Flow<List<PriceAlertEntity>>

    @Query("SELECT * FROM price_alerts WHERE id = :id LIMIT 1")
    suspend fun getPriceAlertById(id: String): PriceAlertEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceAlert(priceAlert: PriceAlertEntity)

    @Update
    suspend fun updatePriceAlert(priceAlert: PriceAlertEntity)

    @Delete
    suspend fun deletePriceAlert(priceAlert: PriceAlertEntity)

    @Query("DELETE FROM price_alerts WHERE id = :id")
    suspend fun deletePriceAlertById(id: String)

    @Query("DELETE FROM price_alerts WHERE stockSymbol = :symbol")
    suspend fun deletePriceAlertsBySymbol(symbol: String)

    @Query("DELETE FROM price_alerts")
    suspend fun deleteAllPriceAlerts()

    @Query("UPDATE price_alerts SET isActive = :isActive WHERE id = :id")
    suspend fun updatePriceAlertStatus(id: String, isActive: Boolean)

    @Query("SELECT COUNT(*) FROM price_alerts WHERE isActive = 1")
    suspend fun getActivePriceAlertCount(): Int
}
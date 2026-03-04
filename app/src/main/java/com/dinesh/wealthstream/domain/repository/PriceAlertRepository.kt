package com.dinesh.wealthstream.domain.repository

import com.dinesh.wealthstream.domain.model.PriceAlert
import kotlinx.coroutines.flow.Flow

interface PriceAlertRepository {
    fun getAllPriceAlerts(): Flow<List<PriceAlert>>
    fun getActivePriceAlerts(): Flow<List<PriceAlert>>
    suspend fun addPriceAlert(alert: PriceAlert)
    suspend fun removePriceAlert(alertId: String)
    suspend fun updateAlertStatus(alertId: String, isActive: Boolean)
}
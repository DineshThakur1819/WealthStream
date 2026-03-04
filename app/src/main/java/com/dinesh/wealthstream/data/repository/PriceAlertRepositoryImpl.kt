package com.dinesh.wealthstream.data.repository

import com.dinesh.wealthstream.data.local.dao.PriceAlertDao
import com.dinesh.wealthstream.data.local.entity.PriceAlertEntity
import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.domain.repository.PriceAlertRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceAlertRepositoryImpl @Inject constructor(
    private val priceAlertDao: PriceAlertDao
) : PriceAlertRepository {

    override fun getAllPriceAlerts(): Flow<List<PriceAlert>> {
        return priceAlertDao.getAllPriceAlerts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getActivePriceAlerts(): Flow<List<PriceAlert>> {
        return priceAlertDao.getActivePriceAlerts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addPriceAlert(alert: PriceAlert) {
        priceAlertDao.insertPriceAlert(alert.toEntity())
    }

    override suspend fun removePriceAlert(alertId: String) {
        priceAlertDao.deletePriceAlertById(alertId)
    }

    override suspend fun updateAlertStatus(alertId: String, isActive: Boolean) {
        priceAlertDao.updatePriceAlertStatus(alertId, isActive)
    }
}

private fun PriceAlertEntity.toDomain(): PriceAlert {
    return PriceAlert(
        id = id,
        stockSymbol = stockSymbol,
        targetPrice = targetPrice,
        condition = AlertCondition.valueOf(condition),
        isActive = isActive,
        createdAt = createdAt
    )
}

private fun PriceAlert.toEntity(): PriceAlertEntity {
    return PriceAlertEntity(
        id = id,
        stockSymbol = stockSymbol,
        targetPrice = targetPrice,
        condition = condition.name,
        isActive = isActive,
        createdAt = createdAt
    )
}
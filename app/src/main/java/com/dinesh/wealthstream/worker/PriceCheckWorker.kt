package com.dinesh.wealthstream.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.dinesh.wealthstream.data.local.dao.*
import com.dinesh.wealthstream.util.notification.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltWorker
class PriceCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val priceAlertDao: PriceAlertDao,
    private val stockDao: StockDao,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Timber.d("PriceCheckWorker: Starting price check...")

            // Get all active price alerts
            val activeAlerts = priceAlertDao.getActivePriceAlerts().first()

            if (activeAlerts.isEmpty()) {
                Timber.d("PriceCheckWorker: No active alerts")
                return Result.success()
            }

            // Check each alert
            activeAlerts.forEach { alert ->
                val stock = stockDao.getStockBySymbol(alert.stockSymbol)

                if (stock != null) {
                    val shouldNotify = when (alert.condition) {
                        "ABOVE" -> stock.price >= alert.targetPrice
                        "BELOW" -> stock.price <= alert.targetPrice
                        else -> false
                    }

                    if (shouldNotify) {
                        notificationHelper.showPriceAlert(
                            symbol = alert.stockSymbol,
                            currentPrice = stock.price,
                            targetPrice = alert.targetPrice,
                            condition = alert.condition
                        )

                        // Deactivate alert after triggering
                        priceAlertDao.updatePriceAlertStatus(alert.id, false)
                    }
                }
            }

            Timber.d("PriceCheckWorker: Price check completed")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "PriceCheckWorker: Error during price check")
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "price_check_worker"

        fun createWorkRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            return PeriodicWorkRequestBuilder<PriceCheckWorker>(
                1, TimeUnit.HOURS,
                15, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    10, TimeUnit.MINUTES
                )
                .addTag(WORK_NAME)
                .build()
        }
    }
}
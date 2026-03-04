package com.dinesh.wealthstream.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.dinesh.wealthstream.domain.repository.StockRepository
import com.dinesh.wealthstream.util.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val stockRepository: StockRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Timber.d("DataSyncWorker: Starting background data sync...")

            // Refresh stock data from API
            stockRepository.refreshStocks()

            Timber.d("DataSyncWorker: Data sync completed successfully")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "DataSyncWorker: Error during sync")

            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        const val WORK_NAME = "data_sync_worker"

        fun createWorkRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false) // Allow even on low battery
                .build()

            return PeriodicWorkRequestBuilder<DataSyncWorker>(
                Constants.SYNC_INTERVAL_MINUTES, TimeUnit.MINUTES
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
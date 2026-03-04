package com.dinesh.wealthstream.util

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.dinesh.wealthstream.worker.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerScheduler @Inject constructor(
    private val workManager: WorkManager
) {
    fun scheduleAllWorkers() {
        schedulePriceCheckWorker()
        scheduleDataSyncWorker()
    }

    private fun schedulePriceCheckWorker() {
        workManager.enqueueUniquePeriodicWork(
            PriceCheckWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            PriceCheckWorker.createWorkRequest()
        )
    }

    private fun scheduleDataSyncWorker() {
        workManager.enqueueUniquePeriodicWork(
            DataSyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            DataSyncWorker.createWorkRequest()
        )
    }

    fun cancelAllWorkers() {
        workManager.cancelUniqueWork(PriceCheckWorker.WORK_NAME)
        workManager.cancelUniqueWork(DataSyncWorker.WORK_NAME)
    }
}
package com.dinesh.wealthstream

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.dinesh.wealthstream.util.WorkerScheduler
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class WealthStreamApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workerScheduler: WorkerScheduler

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Schedule background workers
        workerScheduler.scheduleAllWorkers()

        Timber.d("WealthStreamApplication initialized")
    }
}
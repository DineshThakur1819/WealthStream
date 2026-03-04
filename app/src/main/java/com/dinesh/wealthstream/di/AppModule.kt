package com.dinesh.wealthstream.di

import android.content.Context
import androidx.work.WorkManager
import com.dinesh.wealthstream.util.connectivity.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
    // REMOVED: Duplicate WorkManager provider
    // WorkManager is provided in WorkManagerModule.kt
}
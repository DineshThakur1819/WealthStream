package com.dinesh.wealthstream.di

import android.content.Context
import androidx.room.Room
import com.dinesh.wealthstream.util.*
import com.dinesh.wealthstream.data.local.AppDatabase
import com.dinesh.wealthstream.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStockDao(database: AppDatabase): StockDao {
        return database.stockDao()
    }

    @Provides
    @Singleton
    fun provideWatchlistDao(database: AppDatabase): WatchlistDao {
        return database.watchlistDao()
    }
}
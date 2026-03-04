package com.dinesh.wealthstream.di

import com.dinesh.wealthstream.data.repository.NewsRepositoryImpl
import com.dinesh.wealthstream.data.repository.PriceAlertRepositoryImpl
import com.dinesh.wealthstream.data.repository.StockRepositoryImpl
import com.dinesh.wealthstream.data.repository.WatchlistRepositoryImpl
import com.dinesh.wealthstream.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistRepository(
        watchlistRepositoryImpl: WatchlistRepositoryImpl
    ): WatchlistRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    @Singleton
    abstract fun bindPriceAlertRepository(
        priceAlertRepositoryImpl: PriceAlertRepositoryImpl
    ): PriceAlertRepository
}
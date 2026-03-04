package com.dinesh.wealthstream.data.repository

import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.domain.repository.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor() : NewsRepository {

    override fun getLatestNews(limit: Int): Flow<List<News>> = flow {
        delay(500)
        emit(SampleData.news.take(limit))  // Emit the first 5 news items
    }

    override fun getStockNews(symbol: String): Flow<List<News>> = flow {
        delay(500)
        emit(SampleData.news.filter { it.relatedStocks.contains(symbol) })
    }
}
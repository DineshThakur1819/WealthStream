package com.dinesh.wealthstream.domain.repository

import com.dinesh.wealthstream.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getLatestNews(limit: Int): Flow<List<News>>
    fun getStockNews(symbol: String): Flow<List<News>>
}
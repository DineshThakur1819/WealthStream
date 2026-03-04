package com.dinesh.wealthstream.data.local.dao


import androidx.room.*
import com.dinesh.wealthstream.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedAt DESC LIMIT :limit")
    fun getLatestNews(limit: Int = 20): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE relatedStocks LIKE '%' || :symbol || '%' ORDER BY publishedAt DESC")
    fun getNewsBySymbol(symbol: String): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE id = :id LIMIT 1")
    suspend fun getNewsById(id: String): NewsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleNews(news: NewsEntity)

    @Delete
    suspend fun deleteNews(news: NewsEntity)

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

    @Query("DELETE FROM news WHERE publishedAt < :timestamp")
    suspend fun deleteOldNews(timestamp: Long)

    @Query("SELECT COUNT(*) FROM news")
    suspend fun getNewsCount(): Int
}
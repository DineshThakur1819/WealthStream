package com.dinesh.wealthstream.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Long,
    val relatedStocks: String, // JSON string of list
    val timestamp: Long = System.currentTimeMillis()
)
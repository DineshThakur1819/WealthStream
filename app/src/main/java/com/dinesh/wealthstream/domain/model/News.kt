package com.dinesh.wealthstream.domain.model


data class News(
    val id: String,
    val title: String,
    val description: String?,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Long,
    val relatedStocks: List<String> = emptyList()
)
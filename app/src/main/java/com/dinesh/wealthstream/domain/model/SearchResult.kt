package com.dinesh.wealthstream.domain.model


data class SearchResult(
    val symbol: String,
    val name: String,
    val type: String, // "Stock", "ETF", "Crypto"
    val exchange: String
)
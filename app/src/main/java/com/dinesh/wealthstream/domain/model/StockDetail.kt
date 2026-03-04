package com.dinesh.wealthstream.domain.model


data class StockDetail(
    val stock: Stock,
    val description: String? = null,
    val sector: String? = null,
    val industry: String? = null,
    val ceo: String? = null,
    val employees: Int? = null,
    val website: String? = null,
    val dividendYield: Double? = null,
    val eps: Double? = null,
    val beta: Double? = null
)
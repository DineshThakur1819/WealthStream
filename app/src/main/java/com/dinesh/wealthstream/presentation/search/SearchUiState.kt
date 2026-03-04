package com.dinesh.wealthstream.presentation.search

import com.dinesh.wealthstream.domain.model.Stock

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(
        val stocks: List<Stock>,
        val watchlist: Set<String>
    ) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

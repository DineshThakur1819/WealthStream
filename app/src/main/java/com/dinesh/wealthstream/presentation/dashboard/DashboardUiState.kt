package com.dinesh.wealthstream.presentation.dashboard

import com.dinesh.wealthstream.domain.model.*

sealed interface DashboardUiState {
    data object Loading : DashboardUiState

    data class Success(
        val portfolio: Portfolio,
        val marketIndices: List<MarketIndex>,
        val stocks: List<Stock>,
        val watchlist: Set<String>,
        val searchQuery: String = ""
    ) : DashboardUiState

    data class Error(
        val message: String
    ) : DashboardUiState
}

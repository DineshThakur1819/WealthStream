package com.dinesh.wealthstream.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.wealthstream.domain.repository.*
import com.dinesh.wealthstream.util.connectivity.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val watchlistRepository: WatchlistRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val isOffline: StateFlow<Boolean> = connectivityObserver.isOffline
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading

            try {
                // Combine multiple flows
                combine(
                    stockRepository.getPortfolio(),
                    stockRepository.getMarketIndices(),
                    stockRepository.getTrendingStocks(),
                    watchlistRepository.getWatchlist(),
                    _searchQuery
                ) { portfolio, indices, stocks, watchlist, query ->
                    DashboardUiState.Success(
                        portfolio = portfolio,
                        marketIndices = indices,
                        stocks = stocks,
                        watchlist = watchlist.map { it.stockSymbol }.toSet(),
                        searchQuery = query
                    )
                }.catch { error ->
                    _uiState.value = DashboardUiState.Error(
                        message = error.message ?: "Failed to load dashboard data"
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun toggleWatchlist(symbol: String) {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                if (currentState is DashboardUiState.Success) {
                    if (currentState.watchlist.contains(symbol)) {
                        watchlistRepository.removeFromWatchlist(symbol)
                    } else {
                        watchlistRepository.addToWatchlist(symbol)
                    }
                }
            } catch (e: Exception) {
                // Handle error - could show a Snackbar
            }
        }
    }
    fun refresh() {
        loadDashboardData()
    }
    fun retry() {
        loadDashboardData()
    }
}
package com.dinesh.wealthstream.presentation.watchlist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface WatchlistUiState {
    data object Loading : WatchlistUiState
    data class Success(val stocks: List<Stock>) : WatchlistUiState
    data class Error(val message: String) : WatchlistUiState
}

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    init {
        loadWatchlist()
    }

    private fun loadWatchlist() {
        viewModelScope.launch {
            _uiState.value = WatchlistUiState.Loading

            try {
                combine(
                    watchlistRepository.getWatchlist(),
                    stockRepository.getTrendingStocks()
                ) { watchlist, allStocks ->
                    val watchlistSymbols = watchlist.map { it.stockSymbol }.toSet()
                    allStocks.filter { it.symbol in watchlistSymbols }
                }.catch { error ->
                    _uiState.value = WatchlistUiState.Error(
                        message = error.message ?: "Failed to load watchlist"
                    )
                }.collect { stocks ->
                    _uiState.value = WatchlistUiState.Success(stocks)
                }
            } catch (e: Exception) {
                _uiState.value = WatchlistUiState.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun removeFromWatchlist(symbol: String) {
        viewModelScope.launch {
            try {
                watchlistRepository.removeFromWatchlist(symbol)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun retry() {
        loadWatchlist()
    }
}
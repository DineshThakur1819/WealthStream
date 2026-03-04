package com.dinesh.wealthstream.presentation.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface StockDetailUiState {
    data object Loading : StockDetailUiState
    data class Success(
        val stockDetail: StockDetail,
        val selectedTimeRange: TimeRange = TimeRange.ONE_DAY
    ) : StockDetailUiState
    data class Error(val message: String) : StockDetailUiState
}

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StockDetailUiState>(StockDetailUiState.Loading)
    val uiState: StateFlow<StockDetailUiState> = _uiState.asStateFlow()

    private val _isInWatchlist = MutableStateFlow(false)
    val isInWatchlist: StateFlow<Boolean> = _isInWatchlist.asStateFlow()

    fun loadStockDetail(symbol: String) {
        viewModelScope.launch {
            _uiState.value = StockDetailUiState.Loading

            try {
                stockRepository.getStockDetail(symbol).collect { detail ->
                    _uiState.value = StockDetailUiState.Success(detail)
                }

                // Check watchlist status
                _isInWatchlist.value = watchlistRepository.isInWatchlist(symbol)
            } catch (e: Exception) {
                _uiState.value = StockDetailUiState.Error(
                    message = e.message ?: "Failed to load stock details"
                )
            }
        }
    }

    fun selectTimeRange(timeRange: TimeRange) {
        val currentState = _uiState.value
        if (currentState is StockDetailUiState.Success) {
            _uiState.value = currentState.copy(selectedTimeRange = timeRange)
        }
    }

    fun toggleWatchlist(symbol: String) {
        viewModelScope.launch {
            try {
                if (_isInWatchlist.value) {
                    watchlistRepository.removeFromWatchlist(symbol)
                    _isInWatchlist.value = false
                } else {
                    watchlistRepository.addToWatchlist(symbol)
                    _isInWatchlist.value = true
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
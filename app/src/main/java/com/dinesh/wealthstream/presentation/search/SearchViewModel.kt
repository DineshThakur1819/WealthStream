package com.dinesh.wealthstream.presentation.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dinesh.wealthstream.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _uiState.value = SearchUiState.Idle
                    } else {
                        performSearch(query)
                    }
                }
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            try {
                combine(
                    stockRepository.getTrendingStocks(),
                    watchlistRepository.getWatchlist()
                ) { stocks, watchlist ->
                    val filteredStocks = stocks.filter {
                        it.symbol.contains(query, ignoreCase = true) ||
                                it.name.contains(query, ignoreCase = true)
                    }
                    SearchUiState.Success(
                        stocks = filteredStocks,
                        watchlist = watchlist.map { it.stockSymbol }.toSet()
                    )
                }.catch { error ->
                    _uiState.value = SearchUiState.Error(
                        message = error.message ?: "Search failed"
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(
                    message = e.message ?: "An error occurred"
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
                val isInWatchlist = watchlistRepository.isInWatchlist(symbol)
                if (isInWatchlist) {
                    watchlistRepository.removeFromWatchlist(symbol)
                } else {
                    watchlistRepository.addToWatchlist(symbol)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun retry() {
        if (_searchQuery.value.isNotBlank()) {
            performSearch(_searchQuery.value)
        }
    }
}
package com.dinesh.wealthstream.presentation.search


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dinesh.wealthstream.presentation.components.*
import com.dinesh.wealthstream.ui.theme.*


@Composable
fun SearchScreen(
    onStockClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimensions.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacing16)
    ) {
        // Search Bar
        WealthStreamSearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
            placeholder = "Search stocks..."
        )

        // Search Results
        when (val state = uiState) {
            is SearchUiState.Idle -> {
                EmptyState(
                    title = "Search for stocks",
                    description = "Enter a stock symbol or company name"
                )
            }

            is SearchUiState.Loading -> {
                LoadingIndicator(message = "Searching...")
            }

            is SearchUiState.Success -> {
                if (state.stocks.isEmpty()) {
                    EmptyState(
                        title = "No results found",
                        description = "Try searching with a different keyword"
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
                    ) {
                        items(
                            items = state.stocks,
                            key = { it.id }
                        ) { stock ->
                            /*StockCard(
                                stock = stock,
                                isInWatchlist = state.watchlist.contains(stock.symbol),
                                onCardClick = { onStockClick(stock.symbol) },
                                onWatchlistClick = { viewModel.toggleWatchlist(stock.symbol) }
                            )*/
                        }
                    }
                }
            }

            is SearchUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.retry() }
                )
            }
        }
    }
}
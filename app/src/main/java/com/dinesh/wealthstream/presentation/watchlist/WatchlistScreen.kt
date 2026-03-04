package com.dinesh.wealthstream.presentation.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dinesh.wealthstream.domain.model.Stock
import com.dinesh.wealthstream.presentation.components.*
import com.dinesh.wealthstream.ui.theme.Dimensions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    onStockClick: (String) -> Unit,
    onAddStockClick: () -> Unit,
    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Watchlist") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            if (uiState is WatchlistUiState.Success && (uiState as WatchlistUiState.Success).stocks.isNotEmpty()) {
                FloatingActionButton(
                    onClick = onAddStockClick,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Filled.Add, "Add stock")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is WatchlistUiState.Loading -> {
                    LoadingIndicator(message = "Loading watchlist...")
                }

                is WatchlistUiState.Success -> {
                    if (state.stocks.isEmpty()) {
                        EmptyWatchlist(onAddStockClick = onAddStockClick)
                    } else {
                        WatchlistContent(
                            stocks = state.stocks,
                            onStockClick = onStockClick,
                            onRemoveClick = viewModel::removeFromWatchlist
                        )
                    }
                }

                is WatchlistUiState.Error -> {
                    ErrorView(
                        message = state.message,
                        onRetry = viewModel::retry
                    )
                }
            }
        }
    }
}

@Composable
private fun WatchlistContent(
    stocks: List<Stock>,
    onStockClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimensions.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
    ) {
        items(
            items = stocks,
            key = { it.id }
        ) { /*stock ->
            StockCard(
                stock = stock,
                isInWatchlist = true,
                onCardClick = { onStockClick(stock.symbol) },
                onWatchlistClick = { onRemoveClick(stock.symbol) }
            )*/
        }
    }
}

@Composable
private fun EmptyWatchlist(onAddStockClick: () -> Unit) {
    EmptyState(
        icon = Icons.Filled.Star,
        title = "No stocks in watchlist",
        description = "Add stocks to track your favorites and monitor their performance",
        actionText = "Browse Stocks",
        onActionClick = onAddStockClick
    )
}
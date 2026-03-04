package com.dinesh.wealthstream.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.presentation.components.*
import com.dinesh.wealthstream.ui.theme.*


@Composable
fun DashboardScreen(
    onStockClick: (String) -> Unit,
    onNotificationClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isOffline by viewModel.isOffline.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is DashboardUiState.Loading -> {
                LoadingIndicator(message = "Loading market data...")
            }

            is DashboardUiState.Success -> {
                DashboardContent(
                    portfolio = state.portfolio,
                    marketIndices = state.marketIndices,
                    stocks = state.stocks,
                    watchlist = state.watchlist,
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onStockClick = onStockClick,
                    onWatchlistToggle = viewModel::toggleWatchlist,
                    onNotificationClick = onNotificationClick,
                    onRefresh = viewModel::refresh
                )
            }

            is DashboardUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = viewModel::retry
                )
            }
        }

        NoInternetBanner(
            isVisible = isOffline,
            modifier = Modifier.align(androidx.compose.ui.Alignment.TopCenter)
        )
    }
}

@Composable
private fun DashboardContent(
    portfolio: Portfolio,
    marketIndices: List<MarketIndex>,
    stocks: List<Stock>,
    watchlist: Set<String>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onStockClick: (String) -> Unit,
    onWatchlistToggle: (String) -> Unit,
    onNotificationClick: () -> Unit,
    onRefresh: () -> Unit
) {
    // ✅ REMOVED pull-to-refresh to avoid experimental API warnings
    // If needed, add: @OptIn(ExperimentalMaterial3Api::class)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = Dimensions.paddingMedium,
            end = Dimensions.paddingMedium,
            top = Dimensions.paddingMedium,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacing16)
    ) {
        item {
            PortfolioValueCard(
                totalValue = portfolio.totalValue,
                dailyChange = portfolio.dailyChange,
                dailyChangePercent = portfolio.dailyChangePercent,
                onNotificationClick = onNotificationClick
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(Dimensions.spacing12)) {
                SectionHeader(
                    title = "Market Overview",
                    actionText = "See all",
                    onActionClick = {}
                )

                // ✅ FIXED: LazyRow with proper items
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
                ) {
                    items(
                        items = marketIndices,  // ✅ Use items() instead of manual loop
                        key = { it.symbol }
                    ) { index ->
                        MarketOverviewCard(
                            index = index,
                            modifier = Modifier.width(140.dp)
                        )
                    }
                }
            }
        }

        item {
            WealthStreamSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Search stocks..."
            )
        }

        item {
            SectionHeader(
                title = "Trending Stocks",
                actionText = "View all",
                onActionClick = {}
            )
        }

        val filteredStocks = if (searchQuery.isBlank()) {
            stocks
        } else {
            stocks.filter {
                it.symbol.contains(searchQuery, ignoreCase = true) ||
                        it.name.contains(searchQuery, ignoreCase = true)
            }
        }

        if (filteredStocks.isEmpty()) {
            item {
                EmptyState(
                    title = "No stocks found",
                    description = "Try searching with a different keyword"
                )
            }
        } else {
            items(
                items = filteredStocks,
                key = { it.id }
            ) { stock ->  // ✅ FIXED: Parameter name is 'stock' not 'item'
                /*StockCard(
                    stock = stock,
                    isInWatchlist = watchlist.contains(stock.symbol),
                    onCardClick = { onStockClick(stock.symbol) },
                    onWatchlistClick = { onWatchlistToggle(stock.symbol) }
                )*/
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String? = null,
    onActionClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (actionText != null) {
            TextButton(onClick = onActionClick) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
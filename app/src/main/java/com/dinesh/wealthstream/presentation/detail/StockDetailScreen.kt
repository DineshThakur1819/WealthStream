package com.dinesh.wealthstream.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dinesh.wealthstream.domain.model.*
import com.dinesh.wealthstream.presentation.components.*
import com.dinesh.wealthstream.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailScreen(
    symbol: String,
    onNavigateBack: () -> Unit,
    viewModel: StockDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isInWatchlist by viewModel.isInWatchlist.collectAsStateWithLifecycle()

    LaunchedEffect(symbol) {
        viewModel.loadStockDetail(symbol)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(symbol) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleWatchlist(symbol) }) {
                        Icon(
                            imageVector = if (isInWatchlist) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Watchlist",
                            tint = if (isInWatchlist) Orange500 else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is StockDetailUiState.Loading -> {
                    LoadingIndicator(message = "Loading stock details...")
                }

                is StockDetailUiState.Success -> {
                    StockDetailContent(
                        stockDetail = state.stockDetail,
                        selectedTimeRange = state.selectedTimeRange,
                        onTimeRangeSelected = viewModel::selectTimeRange,
                        onBuyClick = { /* TODO: Implement buy */ },
                        onAlertClick = { /* TODO: Implement alert */ }
                    )
                }

                is StockDetailUiState.Error -> {
                    ErrorView(
                        message = state.message,
                        onRetry = { viewModel.loadStockDetail(symbol) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StockDetailContent(
    stockDetail: StockDetail,
    selectedTimeRange: TimeRange,
    onTimeRangeSelected: (TimeRange) -> Unit,
    onBuyClick: () -> Unit,
    onAlertClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimensions.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(Dimensions.spacing16)
    ) {
        // Price Card
        PriceCard(stock = stockDetail.stock)

        // Chart Section
        ChartSection(
            selectedTimeRange = selectedTimeRange,
            onTimeRangeSelected = onTimeRangeSelected
        )

        // Stats Grid
        StatsGrid(stock = stockDetail.stock)

        // Company Info
        CompanyInfoCard(stockDetail = stockDetail)

        // Action Buttons
        ActionButtons(
            onBuyClick = onBuyClick,
            onAlertClick = onAlertClick
        )
    }
}

@Composable
private fun PriceCard(stock: Stock) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CustomShapes.roundedCorner24,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Purple600, Blue500)
                    )
                )
                .padding(Dimensions.paddingLarge)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stock.symbol,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                        Text(
                            text = stock.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.spacing16))

                Text(
                    text = "$${stock.price}",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = White
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing8)
                ) {
                    Icon(
                        imageVector = if (stock.isGain) Icons.AutoMirrored.Filled.KeyboardArrowRight else Icons.AutoMirrored.Filled.KeyboardArrowLeft/*TrendingDown*/,
                        contentDescription = null,
                        tint = if (stock.isGain) Green300 else Red300,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "${if (stock.isGain) "+" else ""}${String.format("%.2f", stock.change)} (${if (stock.isGain) "+" else ""}${String.format("%.2f", stock.changePercent)}%)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
private fun ChartSection(
    selectedTimeRange: TimeRange,
    onTimeRangeSelected: (TimeRange) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CustomShapes.roundedCorner16,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(Dimensions.paddingMedium)) {
            // Time Range Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing8)
            ) {
                TimeRange.entries.forEach { range ->
                    FilterChip(
                        selected = selectedTimeRange == range,
                        onClick = { onTimeRangeSelected(range) },
                        label = {
                            Text(
                                text = when (range) {
                                    TimeRange.ONE_DAY -> "1D"
                                    TimeRange.ONE_WEEK -> "1W"
                                    TimeRange.ONE_MONTH -> "1M"
                                    TimeRange.THREE_MONTHS -> "3M"
                                    TimeRange.SIX_MONTHS -> "6M"
                                    TimeRange.ONE_YEAR -> "1Y"
                                    TimeRange.FIVE_YEARS -> "5Y"
                                    TimeRange.ALL -> "ALL"
                                },
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.spacing16))

            // Chart Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Purple50, Blue50)
                        ),
                        shape = CustomShapes.roundedCorner16
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Dimensions.spacing8)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star/*ShowChart*/,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "Interactive Chart",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "(Vico/MPAndroidChart)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsGrid(stock: Stock) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimensions.spacing12)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
        ) {
            StatCard(
                label = "Market Cap",
                value = stock.marketCap ?: "N/A",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "P/E Ratio",
                value = stock.peRatio?.let { String.format("%.2f", it) } ?: "N/A",
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
        ) {
            StatCard(
                label = "52W High",
                value = stock.high52Week?.let { "$${String.format("%.2f", it)}" } ?: "N/A",
                valueColor = Green600,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "52W Low",
                value = stock.low52Week?.let { "$${String.format("%.2f", it)}" } ?: "N/A",
                valueColor = Red600,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = CustomShapes.roundedCorner16,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.paddingMedium)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(Dimensions.spacing4))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
        }
    }
}

@Composable
private fun CompanyInfoCard(stockDetail: StockDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CustomShapes.roundedCorner16,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
        ) {
            Text(
                text = "Company Info",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            stockDetail.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            HorizontalDivider()

            InfoRow(label = "CEO", value = stockDetail.ceo ?: "N/A")
            InfoRow(label = "Employees", value = stockDetail.employees?.toString() ?: "N/A")
            InfoRow(label = "Sector", value = stockDetail.sector ?: "N/A")
            InfoRow(label = "Industry", value = stockDetail.industry ?: "N/A")
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ActionButtons(
    onBuyClick: () -> Unit,
    onAlertClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacing12)
    ) {
        Button(
            onClick = onBuyClick,
            modifier = Modifier.weight(1f).height(56.dp),
            shape = CustomShapes.roundedCorner16
        ) {
            Text(
                text = "Buy",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = onAlertClick,
            modifier = Modifier.weight(1f).height(56.dp),
            shape = CustomShapes.roundedCorner16
        ) {
            Text(
                text = "Set Alert",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

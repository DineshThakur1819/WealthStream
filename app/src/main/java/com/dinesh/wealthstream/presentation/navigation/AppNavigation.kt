package com.dinesh.wealthstream.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dinesh.wealthstream.presentation.dashboard.DashboardScreen
import com.dinesh.wealthstream.presentation.detail.StockDetailScreen
import com.dinesh.wealthstream.presentation.profile.ProfileScreen
import com.dinesh.wealthstream.presentation.search.SearchScreen
import com.dinesh.wealthstream.presentation.watchlist.WatchlistScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Check if current route should show bottom bar
    val showBottomBar = currentDestination?.hierarchy?.any { destination ->
        bottomNavItems.any { destination.hasRoute(it.route::class) }
    } == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any {
                            it.hasRoute(item.route::class)
                        } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    popUpTo(Route.Dashboard) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.Dashboard,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Dashboard
            composable<Route.Dashboard> {
                DashboardScreen(
                    onStockClick = { symbol ->
                        navController.navigate(Route.StockDetail(symbol))
                    },
                    onNotificationClick = {
                        // Handle notification click
                    }
                )
            }

            // Search
            composable<Route.Search> {
                SearchScreen(
                    onStockClick = { symbol ->
                        navController.navigate(Route.StockDetail(symbol))
                    }
                )
            }

            // Watchlist
            composable<Route.Watchlist> {
                /*WatchlistScreen(
                    onStockClick = { symbol ->
                        navController.navigate(Route.StockDetail(symbol))
                    },
                    onAddStocksClick = {
                        navController.navigate(Route.Search)
                    }
                )*/
            }

            // Profile
            composable<Route.Profile> {
                ProfileScreen()
            }

            // Stock Detail
            composable<Route.StockDetail> { backStackEntry ->
                val stockDetail: Route.StockDetail = backStackEntry.toRoute()
                /*StockDetailScreen(
                    symbol = stockDetail.symbol,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )*/
            }
        }
    }
}
package com.dinesh.wealthstream.util.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
    val isOffline: Flow<Boolean>
}
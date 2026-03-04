package com.dinesh.wealthstream.util

import kotlinx.coroutines.flow.*

/**
 * A generic class that implements the Offline-First pattern.
 *
 * Flow:
 * 1. Emit cached data from database first
 * 2. Fetch fresh data from network
 * 3. Save to database
 * 4. Emit updated data from database
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType?) -> Boolean = { true },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
): Flow<Resource<ResultType>> = flow {
    // Emit Loading state
    emit(Resource.Loading)

    // Get cached data first
    val cachedData = query().first()

    // Decide if we should fetch from network
    if (shouldFetch(cachedData)) {
        // Emit cached data while fetching
        emit(Resource.Success(cachedData))

        try {
            // Fetch from network
            val networkData = fetch()

            // Save to database
            saveFetchResult(networkData)

            // Emit fresh data from database
            query().collect { freshData ->
                emit(Resource.Success(freshData))
            }
        } catch (throwable: Throwable) {
            // If fetch fails, continue showing cached data
            onFetchFailed(throwable)
            emit(Resource.Error(throwable.message ?: "Unknown error occurred"))
        }
    } else {
        // Just emit cached data without fetching
        emit(Resource.Success(cachedData))
    }
}
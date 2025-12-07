package com.tritiumgaming.feature.newsletter.ui.screen

data class NewsletterRefreshingUiState(
    val isRefreshing: Boolean = false,
    val lastRefreshEpoch: Long = 0L
)

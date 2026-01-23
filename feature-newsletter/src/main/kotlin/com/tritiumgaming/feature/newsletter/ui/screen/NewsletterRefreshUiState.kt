package com.tritiumgaming.feature.newsletter.ui.screen

data class NewsletterRefreshUiState(
    val isRefreshing: Boolean = false,
    val lastRefreshEpoch: Long = 0L
)

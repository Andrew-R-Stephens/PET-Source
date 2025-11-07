package com.tritiumgaming.feature.home.ui.newsletter.screen

data class NewsletterRefreshingUiState(
    val isRefreshing: Boolean = false,
    val lastRefreshEpoch: Long = 0L
)

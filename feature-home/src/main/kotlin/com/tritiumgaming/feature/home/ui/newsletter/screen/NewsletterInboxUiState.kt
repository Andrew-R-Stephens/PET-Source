package com.tritiumgaming.feature.home.ui.newsletter.screen

import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterInbox

data class NewsletterInboxUiState (
    val inbox: NewsletterInbox,
    val lastReadDate: Long = 0L
)
package com.tritiumgaming.feature.newsletter.ui.screen

import com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox

data class NewsletterInboxUiState (
    val inbox: NewsletterInbox,
    val lastReadDate: Long = 0L
)
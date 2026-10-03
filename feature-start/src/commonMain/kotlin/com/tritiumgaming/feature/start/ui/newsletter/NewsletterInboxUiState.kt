package com.tritiumgaming.feature.start.ui.newsletter

import com.tritiumgaming.data.newsletter.model.NewsletterInbox

data class NewsletterInboxUiState(
    val inbox: NewsletterInbox = NewsletterInbox(),
    val lastReadDate: Long = 0L
)

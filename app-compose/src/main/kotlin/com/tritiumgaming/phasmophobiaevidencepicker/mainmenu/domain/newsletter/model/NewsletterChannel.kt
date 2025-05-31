package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale

class NewsletterChannel(
    val language: String? = Locale.ENGLISH.language,
    messages: List<NewsletterMessage> = mutableListOf()
) {
    val messages = MutableStateFlow(messages)
}

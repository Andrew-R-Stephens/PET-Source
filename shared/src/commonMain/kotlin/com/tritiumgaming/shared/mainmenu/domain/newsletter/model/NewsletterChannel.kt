package com.tritiumgaming.shared.mainmenu.domain.newsletter.model

data class NewsletterChannel(
    val language: String,
    val messages: List<NewsletterMessage> = mutableListOf()
)
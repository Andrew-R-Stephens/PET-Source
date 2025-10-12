package com.tritiumgaming.shared.home.domain.newsletter.model

data class NewsletterChannel(
    val language: String,
    val messages: List<NewsletterMessage> = mutableListOf()
)
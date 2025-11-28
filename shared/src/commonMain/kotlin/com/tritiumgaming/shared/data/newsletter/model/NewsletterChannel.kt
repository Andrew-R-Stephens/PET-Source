package com.tritiumgaming.shared.data.newsletter.model

data class NewsletterChannel(
    val language: String,
    val messages: List<com.tritiumgaming.shared.data.newsletter.model.NewsletterMessage> = mutableListOf()
)
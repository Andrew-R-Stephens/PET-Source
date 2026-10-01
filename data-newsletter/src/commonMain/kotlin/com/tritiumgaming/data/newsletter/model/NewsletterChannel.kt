package com.tritiumgaming.data.newsletter.model

data class NewsletterChannel(
    val language: String,
    val messages: List<NewsletterMessage> = mutableListOf()
)
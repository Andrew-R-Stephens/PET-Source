package com.tritiumgaming.data.newsletter.dto.flat

import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterChannel

data class FlattenedNewsletterChannelDto(
    val language: String,
    val messages: List<FlattenedNewsletterMessageDto>? = null
)

fun FlattenedNewsletterChannelDto.toExternal(): NewsletterChannel {
    return NewsletterChannel(
        language = language,
        messages = messages?.toExternal() ?: listOf()
    )
}
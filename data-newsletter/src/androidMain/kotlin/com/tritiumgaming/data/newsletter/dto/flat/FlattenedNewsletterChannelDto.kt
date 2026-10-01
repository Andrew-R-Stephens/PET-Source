package com.tritiumgaming.data.newsletter.dto.flat

import com.tritiumgaming.data.newsletter.model.NewsletterChannel


data class FlattenedNewsletterChannelDto(
    val language: String,
    val messages: List<com.tritiumgaming.data.newsletter.dto.flat.FlattenedNewsletterMessageDto>? = null
)

fun com.tritiumgaming.data.newsletter.dto.flat.FlattenedNewsletterChannelDto.toExternal(): NewsletterChannel {

    val messages = messages?.toExternal()

    return NewsletterChannel(
        language = language,
        messages = messages ?: listOf()
    )
}
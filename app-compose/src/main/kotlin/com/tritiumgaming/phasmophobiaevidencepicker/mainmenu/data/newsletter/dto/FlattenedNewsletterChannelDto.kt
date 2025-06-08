package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterChannel

data class FlattenedNewsletterChannelDto(
    val language: String? = null,
    val messages: List<FlattenedNewsletterMessageDto>? = null
)

fun FlattenedNewsletterChannelDto.toExternal(): NewsletterChannel {
    return NewsletterChannel(
        language = language,
        messages = messages?.toExternal() ?: listOf()
    )
}
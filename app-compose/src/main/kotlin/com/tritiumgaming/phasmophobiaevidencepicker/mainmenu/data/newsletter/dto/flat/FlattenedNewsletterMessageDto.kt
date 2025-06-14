package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterMessage

data class FlattenedNewsletterMessageDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: Long? = null
)

fun FlattenedNewsletterMessageDto.toExternal(): NewsletterMessage =
    NewsletterMessage(
        id = id ?: "0",
        title = title,
        description = description,
        date = date
    )


fun List<FlattenedNewsletterMessageDto>.toExternal(): List<NewsletterMessage> =
    mapIndexed { index, dto ->
        NewsletterMessage(
            id = dto.id ?: "$index",
            title = dto.title,
            description = dto.description,
            date = dto.date
        )
    }
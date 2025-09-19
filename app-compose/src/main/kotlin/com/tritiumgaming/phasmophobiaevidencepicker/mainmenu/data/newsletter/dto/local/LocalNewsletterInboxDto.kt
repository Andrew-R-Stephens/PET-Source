package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.local

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat.FlattenedNewsletterInboxDto
import com.tritiumgaming.shared.mainmenu.domain.newsletter.mapper.NewsletterResources

data class LocalNewsletterInboxDto(
    val id: String,
    val title: NewsletterResources.NewsletterTitle,
    val url: String,
    val icon: NewsletterResources.NewsletterIcon,
)

fun LocalNewsletterInboxDto.toExternal(): FlattenedNewsletterInboxDto =
    FlattenedNewsletterInboxDto(
        id = id,
        title = title,
        url = url,
        icon = icon
    )

fun List<LocalNewsletterInboxDto>.toInternal(): List<FlattenedNewsletterInboxDto> =
    map { dto ->
        FlattenedNewsletterInboxDto(
            id = dto.id,
            title = dto.title,
            url = dto.url,
            icon = dto.icon
        )
    }
package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat.FlattenedNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterTitle

data class LocalNewsletterInboxDto(
    val id: String,
    val title: NewsletterTitle,
    val url: String,
    val icon: NewsletterIcon,
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
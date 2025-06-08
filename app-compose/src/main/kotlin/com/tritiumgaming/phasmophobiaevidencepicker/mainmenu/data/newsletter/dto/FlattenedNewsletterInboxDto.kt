package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterChannel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox

data class FlattenedNewsletterInboxDto(
    val id: String? = null,
    @StringRes val title: Int? = null,
    val url: String? = null,
    @DrawableRes val icon: Int? = null,
    var channel: FlattenedNewsletterChannelDto? = null
)

fun FlattenedNewsletterInboxDto.toExternal(): NewsletterInbox =
    NewsletterInbox(
        id = id,
        title = title,
        url = url,
        icon = icon
    )

fun List<FlattenedNewsletterInboxDto>.toExternal(): List<NewsletterInbox> =
    map { dto ->
        NewsletterInbox(
            id = dto.id,
            title = dto.title,
            url = dto.url,
            icon = dto.icon,
            channel = dto.channel?.toExternal() ?: NewsletterChannel()
        )
    }
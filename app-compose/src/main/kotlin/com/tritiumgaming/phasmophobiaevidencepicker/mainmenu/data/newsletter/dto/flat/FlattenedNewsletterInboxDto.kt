package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterTitle
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterChannel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox

data class FlattenedNewsletterInboxDto(
    val id: String? = null,
    val title: NewsletterTitle? = null,
    val url: String? = null,
    val icon: NewsletterIcon? = null,
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
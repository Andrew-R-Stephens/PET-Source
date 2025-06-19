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
        title = title ?: NewsletterTitle.GENERAL_NEWS,
        url = url,
        icon = icon ?: NewsletterIcon.GENERAL_NEWS
    )

fun List<FlattenedNewsletterInboxDto>.toExternal(): List<NewsletterInbox> =
    map { dto ->
        NewsletterInbox(
            id = dto.id,
            title = dto.title ?: NewsletterTitle.GENERAL_NEWS,
            url = dto.url,
            icon = dto.icon ?: NewsletterIcon.GENERAL_NEWS,
            channel = dto.channel?.toExternal() ?: NewsletterChannel()
        )
    }
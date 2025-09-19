package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat

import com.tritiumgaming.shared.mainmenu.domain.newsletter.mapper.NewsletterResources
import com.tritiumgaming.shared.mainmenu.domain.newsletter.model.NewsletterInbox

data class FlattenedNewsletterInboxDto(
    val id: String? = null,
    val title: NewsletterResources.NewsletterTitle? = null,
    val url: String? = null,
    val icon: NewsletterResources.NewsletterIcon? = null,
    var channel: FlattenedNewsletterChannelDto? = null
)

fun FlattenedNewsletterInboxDto.toExternal(): NewsletterInbox =
    NewsletterInbox(
        id = id,
        title = title ?: NewsletterResources.NewsletterTitle.GENERAL_NEWS,
        url = url,
        icon = icon ?: NewsletterResources.NewsletterIcon.GENERAL_NEWS
    )

fun List<FlattenedNewsletterInboxDto>.toExternal(): List<NewsletterInbox> =
    map { dto ->
        NewsletterInbox(
            id = dto.id,
            title = dto.title ?: NewsletterResources.NewsletterTitle.GENERAL_NEWS,
            url = dto.url,
            icon = dto.icon ?: NewsletterResources.NewsletterIcon.GENERAL_NEWS,
            channel = dto.channel?.toExternal()
        )
    }
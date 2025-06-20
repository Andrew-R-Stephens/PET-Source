package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.local.LocalNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterTitle

class NewsletterLocalDataSourceImpl(
    private val applicationContext: Context
): NewsletterLocalDataSource {

    private val inboxResourcesDto: List<NewsletterInboxResourceDto>
        get() = listOf(
            // General Inbox
            NewsletterInboxResourceDto(
                id = R.string.newsletter_inbox_id_general,
                title = NewsletterTitle.GENERAL_NEWS,
                url = R.string.preference_general_news_link,
                icon = NewsletterIcon.GENERAL_NEWS
            ),
            // PET Inbox
            NewsletterInboxResourceDto(
                id = R.string.newsletter_inbox_id_pet,
                title = NewsletterTitle.PET_CHANGELOG,
                url = R.string.preference_pet_changelog_link,
                icon = NewsletterIcon.PET_CHANGELOG
            ),
            // Official Phasmophobia Inbox
            NewsletterInboxResourceDto(
                id = R.string.newsletter_inbox_id_phasmophobia,
                title = NewsletterTitle.PHASMOPHOBIA_CHANGELOG,
                url = R.string.preference_phasmophobia_changelog_link,
                icon = NewsletterIcon.PHASMOPHOBIA_CHANGELOG
            )
        )

    override fun fetchInboxes(): Result<List<LocalNewsletterInboxDto>> =
       Result.success(inboxResourcesDto.toLocalNewsletterInboxDto())

    private fun List<NewsletterInboxResourceDto>.toLocalNewsletterInboxDto() = map {
        it.toLocalNewsletterInboxDto()
    }

    private fun NewsletterInboxResourceDto.toLocalNewsletterInboxDto() = LocalNewsletterInboxDto(
        id = applicationContext.resources.getString(id),
        title = title,
        url = applicationContext.resources.getString(url),
        icon = icon
    )

    private data class NewsletterInboxResourceDto(
        @StringRes val id: Int,
        val title: NewsletterTitle,
        @StringRes val url: Int,
        val icon: NewsletterIcon
    )

}

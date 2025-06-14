package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.local.LocalNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterLocalDataSource

class NewsletterLocalDataSource(
    private val applicationContext: Context
): NewsletterLocalDataSource {

    private val inboxResourcesDto: List<NewsletterInboxResourceDto>
        get() = listOf(
            // General Inbox
            NewsletterInboxResourceDto(
                id = R.string.newsletter_inbox_id_general,
                title = R.string.newsletter_inbox_title_general,
                url = R.string.preference_general_news_link,
                icon = R.drawable.ic_news
            ),
            // PET Inbox
            NewsletterInboxResourceDto(
                id = R.string.newsletter_inbox_id_pet,
                title = R.string.newsletter_inbox_title_pet,
                url = R.string.preference_pet_changelog_link,
                icon = R.drawable.icon_logo_app
            ),
            // Official Phasmophobia Inbox
            NewsletterInboxResourceDto(
                id = R.string.newsletter_inbox_id_phasmophobia,
                title = R.string.newsletter_inbox_title_phasmophobia,
                url = R.string.preference_phasmophobia_changelog_link,
                icon = R.drawable.icon_logo_phasmophobia
            )
        )

    override fun fetchInboxes(): Result<List<LocalNewsletterInboxDto>> {

        val resources = applicationContext.resources

        val inboxes: MutableList<LocalNewsletterInboxDto> = mutableListOf()

        inboxResourcesDto.forEach { resDto ->

            inboxes.add(
               LocalNewsletterInboxDto(
                   id = resources.getString(resDto.id),
                   title = resDto.title,
                   url = resources.getString(resDto.url),
                   icon = resDto.icon
               )
            )

        }

        return Result.success(inboxes)
    }

}

private data class NewsletterInboxResourceDto(
    @StringRes val id: Int,
    @StringRes val title: Int,
    @StringRes val url: Int,
    @DrawableRes val icon: Int
)

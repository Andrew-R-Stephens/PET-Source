package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

class NewsletterInboxType(
    val id: Int,
    val title: Int,
    val url: String,
    @DrawableRes val icon: Int
) {

    enum class NewsletterInboxTypeDTO(
        val id: Int,
        @StringRes val title: Int,
        @StringRes val url: Int,
        @DrawableRes val icon: Int
    ) {
        GENERAL(
            id = 0,
            title = R.string.messagecenter_inbox_general,
            url = R.string.preference_general_news_link,
            icon = R.drawable.ic_news
        ),
        PET(
            id = 1,
            title = R.string.messagecenter_inbox_pet,
            url = R.string.preference_pet_changelog_link,
            icon = R.drawable.icon_logo_app
        ),
        PHASMOPHOBIA(
            id = 2,
            title = R.string.messagecenter_inbox_phas,
            url = R.string.preference_phasmophobia_changelog_link,
            icon = R.drawable.icon_logo_phas
        )
    }

    companion object  {

        fun create(
            context: Context,
            inboxType: NewsletterInboxTypeDTO
        ): NewsletterInboxType {

            return NewsletterInboxType(
                id = inboxType.id,
                title = inboxType.title,
                url = context.getString(inboxType.url),
                icon = inboxType.icon
            )

        }

        fun getInbox(id: Int): NewsletterInboxTypeDTO? {
            return NewsletterInboxTypeDTO.entries.firstOrNull { it.id == id }
        }

    }

}

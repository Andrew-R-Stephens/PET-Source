package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterTitle

@StringRes fun NewsletterTitle.toStringResource(): Int =
    when (this) {
        NewsletterTitle.GENERAL_NEWS -> R.string.newsletter_inbox_title_general
        NewsletterTitle.PET_CHANGELOG -> R.string.newsletter_inbox_title_pet
        NewsletterTitle.PHASMOPHOBIA_CHANGELOG -> R.string.newsletter_inbox_title_phasmophobia
    }

@DrawableRes fun NewsletterIcon.toDrawableResource(): Int =
    when (this) {
        NewsletterIcon.GENERAL_NEWS -> R.drawable.ic_news
        NewsletterIcon.PET_CHANGELOG -> R.drawable.icon_logo_app
        NewsletterIcon.PHASMOPHOBIA_CHANGELOG -> R.drawable.icon_logo_phasmophobia
    }
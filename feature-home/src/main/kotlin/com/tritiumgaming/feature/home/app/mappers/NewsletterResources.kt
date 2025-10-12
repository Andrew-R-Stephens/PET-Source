package com.tritiumgaming.feature.home.app.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.shared.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.shared.home.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.shared.home.domain.newsletter.mapper.NewsletterResources.NewsletterTitle

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

@Composable
fun NewsletterIcon.toIconResource(): IconResource =
    when (this) {
        NewsletterIcon.GENERAL_NEWS -> IconResource.NEWS
        NewsletterIcon.PET_CHANGELOG -> IconResource.ICON_LOGO_APP
        NewsletterIcon.PHASMOPHOBIA_CHANGELOG -> IconResource.ICON_LOGO_PHASMOPHOBIA
    }

@Composable
fun NewsletterIcon.ToComposable(
    modifier: Modifier = Modifier
) = when (this) {
        NewsletterIcon.GENERAL_NEWS -> IconResource.NEWS.ToComposable(modifier = modifier)
        NewsletterIcon.PET_CHANGELOG -> IconResource.ICON_LOGO_APP.ToComposable(modifier = modifier)
        NewsletterIcon.PHASMOPHOBIA_CHANGELOG -> IconResource.ICON_LOGO_PHASMOPHOBIA.ToComposable(modifier = modifier)
    }

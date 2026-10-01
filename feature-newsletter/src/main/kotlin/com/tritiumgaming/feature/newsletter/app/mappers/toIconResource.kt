package com.tritiumgaming.feature.newsletter.app.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.core.ui.mappers.IconResources.IconResource
import com.tritiumgaming.data.newsletter.mapper.NewsletterResources.NewsletterIcon

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

package com.tritiumgaming.feature.start.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.core.navigation.NavRoute
import com.tritiumgaming.core.ui.icon.impl.base.GearIcon
import com.tritiumgaming.core.ui.icon.impl.base.HamburgerMenuIcon
import com.tritiumgaming.core.ui.icon.impl.base.InfoIcon
import com.tritiumgaming.core.ui.icon.impl.base.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.impl.base.PersonIcon
import com.tritiumgaming.core.ui.icon.impl.base.ReviewIcon
import com.tritiumgaming.core.ui.icon.impl.base.StoreIcon
import com.tritiumgaming.core.ui.icon.impl.composite.BadgeIcon
import com.tritiumgaming.core.ui.icon.impl.composite.LanguageIcon
import com.tritiumgaming.core.ui.icon.impl.composite.NotificationIndicator
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.core.ui.mappers.IconResources.IconResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.account.AccountBannerIcon
import com.tritiumgaming.core.ui.widgets.menus.IconDropdownMenu
import com.tritiumgaming.core.ui.widgets.menus.IconDropdownMenuColors
import com.tritiumgaming.core.ui.widgets.menus.SecondarySelector

@Composable
internal fun HeaderNavBar(
    inboxNotificationState: Boolean,
    canRequestReview: Boolean,
    currentUser: FirebaseUser?,
    onNavigate: (route: String) -> Unit = {},
    onOpenPatreon: () -> Unit = {},
    onOpenDiscord: () -> Unit = {}
) {
    val context = LocalContext.current

    IconDropdownMenu(
        primaryContent = { HeaderMenuButton() },
        dropdownContent = @Composable {
            SecondarySelector(onClick = { onNavigate(NavRoute.SCREEN_APP_INFO.route) }) {
                HeaderInfoButton()
            }
            SecondarySelector(onClick = { onNavigate(NavRoute.SCREEN_SETTINGS.route) }) {
                HeaderGearButton()
            }
            SecondarySelector(onClick = { onNavigate(NavRoute.SCREEN_LANGUAGE.route) }) {
                HeaderLanguageButton(onNavigate = onNavigate)
            }
            SecondarySelector(onClick = onOpenPatreon) {
                HeaderPatreonButton()
            }
            SecondarySelector(onClick = onOpenDiscord) {
                HeaderDiscordButton()
            }
        },
        colors = IconDropdownMenuColors(
            primaryContentBackground = LocalPalette.current.surfaceContainer,
            dropdownContentBackground = LocalPalette.current.surfaceContainer
        )
    ) { false }

    // News Button
    NotificationIndicator(
        isActive = inboxNotificationState,
        baseComponent = @Composable { modifier ->
            IconResource.NEWS.ToComposable(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.surfaceContainerLow,
                    strokeColor = LocalPalette.current.onSurface
                ),
            )
        },
        badgeComponent = @Composable { modifier ->
            IconResource.NOTIFY.ToComposable(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.surfaceContainer,
                    strokeColor = LocalPalette.current.error
                )
            )
        },
    ) {
        onNavigate(NavRoute.NAVIGATION_NEWSLETTER.route)
    }

    if (canRequestReview) {
        HeaderReviewButton()

    }

    IconDropdownMenu(
        primaryContent = {
            if (!LocalInspectionMode.current) {
                HeaderAccountButton(currentUser = currentUser)
            }
        },
        dropdownContent = @Composable {
            SecondarySelector(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                onClick = { onNavigate(NavRoute.SCREEN_ACCOUNT_OVERVIEW.route) }
            ) {
                HeaderPersonButton()
            }

            SecondarySelector(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                onClick = { onNavigate(NavRoute.NAVIGATION_MARKETPLACE.route) }
            ) {
                HeaderStoreButton()
            }
        },
        colors = IconDropdownMenuColors(
            primaryContentBackground = LocalPalette.current.surfaceContainer,
            dropdownContentBackground = LocalPalette.current.surfaceContainer
        )
    ) { false }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
private fun HeaderMenuButton() {
    HamburgerMenuIcon(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp),
        colors = IconVectorColors.defaults(
            fillColor = LocalPalette.current.surfaceContainerLow,
            strokeColor = LocalPalette.current.onSurface
        )
    )
}

@Composable
private fun HeaderInfoButton() {
    InfoIcon(
        modifier = Modifier.size(48.dp),
        colors = IconVectorColors.defaults(
            fillColor = LocalPalette.current.surfaceContainerLow,
            strokeColor = LocalPalette.current.onSurface
        )
    )
}

@Composable
private fun HeaderGearButton() {
    GearIcon(
        modifier = Modifier.size(48.dp),
        colors = IconVectorColors.defaults(
            fillColor = LocalPalette.current.surfaceContainerLow,
            strokeColor = LocalPalette.current.onSurface
        )
    )
}

@Composable
private fun HeaderLanguageButton(onNavigate: (route: String) -> Unit) {
    LanguageIcon(
        modifier = Modifier.size(48.dp),
        colors = IconVectorColors.defaults(
            fillColor = LocalPalette.current.surfaceContainerLow,
            strokeColor = LocalPalette.current.onSurface
        )
    ) {
        onNavigate(NavRoute.SCREEN_LANGUAGE.route)
    }
}

@Composable
private fun HeaderDiscordButton() {
    BadgeIcon(
        modifier = Modifier.size(48.dp),
        baseComponent = {
            IconResource.DISCORD.ToComposable(
                colors = IconVectorColors.defaults(
                    fillColor = LocalPalette.current.discordColor.color,
                    strokeColor = LocalPalette.current.discordColor.onColor,
                )
            )
        }
    ) {
        OpenInNewIcon(
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        )
    }
}

@Composable
private fun HeaderPatreonButton() {
    BadgeIcon(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp),
        baseComponent = {
            IconResource.PATREON.ToComposable(
                colors = IconVectorColors.defaults(
                    fillColor = LocalPalette.current.patreonColor.onColor,
                    strokeColor = LocalPalette.current.patreonColor.color,
                )
            )
        }
    )
}

@Composable
private fun HeaderAccountButton(currentUser: FirebaseUser?) {
    val username = currentUser?.displayName ?: ""
    AccountBannerIcon(
        modifier = Modifier
            .heightIn(max = 48.dp)
            .padding(4.dp)
            .fillMaxHeight()
            .aspectRatio(1f),
        name = username,
        icon = { modifier ->
            Image(
                modifier = modifier,
                painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                contentDescription = "",
                contentScale = ContentScale.Inside,
                alpha = .75f
            )
        }
    )
}

@Composable
private fun HeaderPersonButton() {
    PersonIcon(
        modifier = Modifier,
        colors = IconVectorColors.defaults(
            strokeColor = LocalPalette.current.onSurface
        )
    )
}

@Composable
private fun HeaderStoreButton() {
    StoreIcon(
        modifier = Modifier,
        colors = IconVectorColors.defaults(
            fillColor = LocalPalette.current.onSurface,
            strokeColor = LocalPalette.current.onSurface
        )
    )
}

@Composable
private fun HeaderReviewButton() {

    val context = LocalContext.current

    ReviewIcon(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp)
            .clickable {
                val activity = context.findActivity()
                if (activity != null) {
                    try {
                        val manager = ReviewManagerFactory.create(activity)
                        manager.requestReviewFlow().addOnCompleteListener { requestTask ->
                            if (requestTask.isSuccessful) {
                                requestTask.result?.let { reviewInfo ->
                                    manager.launchReviewFlow(activity, reviewInfo)
                                        .addOnCompleteListener {
                                            Log.d("ReviewFlow", "Native in-app review flow completed.")
                                        }
                                }
                            } else {
                                requestTask.exception?.printStackTrace()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
        colors = IconVectorColors.defaults(
            fillColor = LocalPalette.current.surfaceContainerLow,
            strokeColor = LocalPalette.current.onSurface
        )
    )
}

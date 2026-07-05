package com.tritiumgaming.feature.start.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.ButtonScratchedIcon
import com.tritiumgaming.core.ui.icon.impl.base.CalendarIcon
import com.tritiumgaming.core.ui.icon.impl.base.GearIcon
import com.tritiumgaming.core.ui.icon.impl.base.HamburgerMenuIcon
import com.tritiumgaming.core.ui.icon.impl.base.InfoIcon
import com.tritiumgaming.core.ui.icon.impl.base.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.impl.base.PersonIcon
import com.tritiumgaming.core.ui.icon.impl.base.ReviewIcon
import com.tritiumgaming.core.ui.icon.impl.base.StoreIcon
import com.tritiumgaming.core.ui.icon.impl.composite.AccountIcon
import com.tritiumgaming.core.ui.icon.impl.composite.AccountIconPrimaryContent
import com.tritiumgaming.core.ui.icon.impl.composite.BadgeIcon
import com.tritiumgaming.core.ui.icon.impl.composite.LanguageIcon
import com.tritiumgaming.core.ui.icon.impl.composite.NotificationIndicator
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.admob.BannerAd
import com.tritiumgaming.core.ui.widgets.menus.IconDropdownMenu
import com.tritiumgaming.core.ui.widgets.menus.IconDropdownMenuColors
import com.tritiumgaming.core.ui.widgets.menus.SecondarySelector
import com.tritiumgaming.feature.start.BuildConfig
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.core.ui.mappers.IconResources.IconResource
import java.util.Locale

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@DevicePreviews
@Composable
@Preview
private fun StartScreenPreview() {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            StartContent(
                inboxNotificationState = true,
                canRequestReview = true,
                currentLanguage = "English",
                currentUser = null,
                onNavigate = {},
                onOpenPatreon = {},
                onOpenDiscord = {}
            )
        }
    }
}

@Composable
fun StartScreen(
    startScreenViewModel: StartScreenViewModel,
    navController: NavHostController
) {
    val newsletterInboxesUiState by startScreenViewModel.inboxesUiState.collectAsStateWithLifecycle()
    val reviewUiState by startScreenViewModel.reviewFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.link_discordInvite)
    val patreonInvitation = stringResource(R.string.link_patreonInvite)

    val inboxNotificationState = remember(newsletterInboxesUiState) {
        newsletterInboxesUiState.inboxes
            .sortedByDescending { it.lastReadDate }
            .firstOrNull { inboxUiState ->
                inboxUiState.inbox.compareDates(inboxUiState.lastReadDate)
            } != null
    }

    val rememberLocale = remember { Locale.getDefault() }
    var currentLanguage = rememberLocale.displayLanguage
    if (currentLanguage.isNotEmpty()) {
        currentLanguage = currentLanguage.substring(0, 1)
            .uppercase(rememberLocale) + currentLanguage.substring(1)
    }

    val currentUser = Firebase.auth.currentUser

    val onNavigate = { route: String ->
        navController.navigate(route)
    }

    val onOpenPatreon = {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "https://patreon.com/ $patreonInvitation".toUri()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val onOpenDiscord = {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "https://discord.gg/ $discordInvitation".toUri()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    StartContent(
        inboxNotificationState = inboxNotificationState,
        canRequestReview = reviewUiState.canRequestReview,
        currentLanguage = currentLanguage,
        currentUser = currentUser,
        onNavigate = onNavigate,
        onOpenPatreon = onOpenPatreon,
        onOpenDiscord = onOpenDiscord
    )

}


@Composable
private fun StartContent(
    inboxNotificationState: Boolean,
    canRequestReview: Boolean,
    currentLanguage: String,
    currentUser: FirebaseUser?,
    onNavigate: (route: String) -> Unit,
    onOpenPatreon: () -> Unit,
    onOpenDiscord: () -> Unit
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            HeaderNavBar(
                inboxNotificationState = inboxNotificationState,
                canRequestReview = canRequestReview,
                currentUser = currentUser,
                onNavigate = onNavigate,
                onOpenPatreon = onOpenPatreon,
                onOpenDiscord = onOpenDiscord
            )
        }

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                StartContentPortrait(
                    modifier = Modifier
                        .weight(1f, true),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                StartContentLandscape(
                    modifier = Modifier
                        .weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.TABLET_PORTRAIT -> {
                StartContentPortrait(
                    modifier = Modifier
                        .weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.TABLET_LANDSCAPE -> {
                StartContentLandscape(
                    modifier = Modifier
                        .weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.DESKTOP -> {
                StartContentLandscape(
                    modifier = Modifier
                        .weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
        }

        BannerAd(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .heightIn(min = 50.dp),
            adId = stringResource(R.string.ad_banner_1)
        )
    }

}

@Composable
private fun StartContentPortrait(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    onNavigate: (route: String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LogoSection(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .weight(1f, false)
        )

        Spacer(modifier = Modifier.height(32.dp))

        StartSection(
            modifier = Modifier
                .weight(1f, false),
            currentLanguage = currentLanguage,
            onNavigate = onNavigate
        )
    }
}

@Composable
internal fun StartContentLandscape(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    onNavigate: (route: String) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoSection(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .weight(1f, false)
        )

        StartSection(
            modifier = Modifier
                .weight(1f),
            currentLanguage = currentLanguage,
            onNavigate = onNavigate
        )
    }
}

@Composable
private fun LogoSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {

        IconResource.ICON_LOGO_APP.ToComposable(
            modifier = Modifier
                .widthIn(max = 256.dp)
                .weight(1f, false)
                .aspectRatio(1f),

            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        )

        BasicText(
            modifier = Modifier
                .widthIn(max = 256.dp)
                .fillMaxWidth()
                .height(36.dp),
            text = stringResource(R.string.titlescreen_description).uppercase(),
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.primary,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp, maxFontSize = 36.sp, stepSize = 2.sp)
        )

    }
}

@Composable
private fun StartSection(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    onNavigate: (route: String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StartButton {
            onNavigate(NavRoute.SCREEN_INVESTIGATION.route)
        }

        LanguageButton(
            currentLanguage = currentLanguage
        ) {
            onNavigate(NavRoute.SCREEN_LANGUAGE.route)
        }

    }
}

@Composable
private fun LanguageButton(
    currentLanguage: String,
    onClick: () -> Unit = {}
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LanguageIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        ) {
            onClick()
        }

        BasicText(
            modifier = Modifier
                .wrapContentSize(),
            text = currentLanguage,
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.onSurface,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            ),
            maxLines = 1
        )

    }
}

@Composable
private fun StartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .width(IntrinsicSize.Max),
        onClick = { onClick() },
        enabled = true,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
        ),

    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            contentAlignment = Alignment.Center,
        ) {

            ButtonScratchedIcon(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max),
                colors = IconVectorColors.defaults(
                    fillColor = LocalPalette.current.surfaceContainerLow,
                    strokeColor = LocalPalette.current.onSurface
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .fillMaxHeight(.65f),
                contentAlignment = Alignment.Center,
                propagateMinConstraints = true
            ) {
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = stringResource(R.string.titlescreen_button).uppercase(),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.onSurface,
                        textAlign = TextAlign.Center,
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 5.sp)
                )
            }


        }

    }

}

@Composable
private fun HeaderNavBar(
    inboxNotificationState: Boolean,
    canRequestReview: Boolean,
    currentUser: FirebaseUser?,
    onNavigate: (route: String) -> Unit = {},
    onOpenPatreon: () -> Unit = {},
    onOpenDiscord: () -> Unit = {}
) {

    val menuIcon: @Composable () -> Unit = {
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
    val infoIcon: @Composable () -> Unit = {
        InfoIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        )
    }
    val gearIcon: @Composable () -> Unit = {
        GearIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        )
    }
    val languageIcon: @Composable () -> Unit = {
        LanguageIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        ) {
            onNavigate(NavRoute.SCREEN_LANGUAGE.route)
        }
    }
    val calendarIcon: @Composable () -> Unit = {
        CalendarIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.onSurface
            )
        )
    }
    val discordIcon: @Composable () -> Unit = {
        BadgeIcon(
            modifier = Modifier
                .size(48.dp),
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
    val patreonIcon: @Composable () -> Unit = {
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

    val reviewIcon: @Composable (modifier: Modifier) -> Unit = { modifier ->
        ReviewIcon(
            modifier = modifier
                .size(48.dp)
                .padding(4.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        )
    }

    val accountIcon: @Composable () -> Unit = {
        AccountIcon(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            borderColor =  LocalPalette.current.onSurface,
            backgroundColor = LocalPalette.current.surfaceContainerLow,
            placeholder = {
                IconResource.PERSON.ToComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    colors = IconVectorColors.defaults(
                        fillColor = LocalPalette.current.surfaceContainerLow,
                        strokeColor = LocalPalette.current.onSurface
                    )
                )
            },
            content = {
                currentUser?.let { auth ->
                    val authUserName = auth.displayName ?: ""

                    val names: List<String?> = (authUserName).split(" ")

                    AccountIconPrimaryContent(
                        firstName = names.getOrNull(0) ?: "",
                        lastName = names.getOrNull(1) ?: "",
                        textStyle = LocalTypography.current.primary.bold.copy(
                            color = LocalPalette.current.onSurface,
                            textAlign = TextAlign.Center,
                            shadow = Shadow(
                                color = LocalPalette.current.surfaceContainer,
                                blurRadius = 8f
                            ),
                        )
                    ) {
                        Image(
                            painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                            contentDescription = "",
                            contentScale = ContentScale.Inside,
                            alpha = .5f
                        )
                    }
                }
            }
        )
    }

    val personIcon: @Composable () -> Unit = { PersonIcon(
        modifier = Modifier,
        colors = IconVectorColors.defaults(
            strokeColor = LocalPalette.current.onSurface
        )
    ) }

    val storeIcon: @Composable () -> Unit = { StoreIcon(
        modifier = Modifier,
        colors = IconVectorColors.defaults(
            fillColor = LocalPalette.current.onSurface,
            strokeColor = LocalPalette.current.onSurface
        )
    ) }

    IconDropdownMenu(
        primaryContent = menuIcon,
        dropdownContent = @Composable {
            SecondarySelector(
                onClick = { onNavigate(NavRoute.SCREEN_APP_INFO.route) }) {
                infoIcon()
            }
            SecondarySelector(
                onClick = { onNavigate(NavRoute.SCREEN_SETTINGS.route) }) {
                gearIcon()
            }
            SecondarySelector(
                onClick = { onNavigate(NavRoute.SCREEN_LANGUAGE.route) }) {
                languageIcon()
            }
            SecondarySelector(
                onClick = onOpenPatreon
            ) {
                patreonIcon()
            }
            SecondarySelector(
                onClick = onOpenDiscord
            ) {
                discordIcon()
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

    val context = LocalContext.current
    if(canRequestReview) {
        reviewIcon(
            Modifier.clickable(onClick = {
                try {
                    val appInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        context.packageManager.getApplicationInfo(
                            context.packageName,
                            PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        context.packageManager.getApplicationInfo(
                            context.packageName,
                            PackageManager.GET_META_DATA
                        )
                    }

                    val metaData: Bundle? = appInfo.metaData
                    if (metaData != null) {
                        // Note: If not explicitly defined in the manifest, these will return false or null
                        val allowAdUserData = metaData.getBoolean("google_analytics_default_allow_ad_user_data", false)
                        val allowAdPersonalization = metaData.getBoolean("google_analytics_default_allow_ad_personalization_signals", false)

                        Log.d("ConsentManifest", "Default Ad User Data: $allowAdUserData")
                        Log.d("ConsentManifest", "Default Ad Personalization: $allowAdPersonalization")
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    Log.e("ConsentManifest", "Failed to load meta-data", e)
                }
            })
        )
    }

    IconDropdownMenu(
        primaryContent = {
            if (!LocalInspectionMode.current) {
                accountIcon()
            }
        },
        dropdownContent = @Composable {
            SecondarySelector(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                onClick = { onNavigate(NavRoute.SCREEN_ACCOUNT_OVERVIEW.route) }) {
                personIcon()
            }

            if(BuildConfig.USE_MARKETPLACE) {
                SecondarySelector(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                    onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_UNLOCKS.route) }) {
                    storeIcon()
                }
            }
        },
        colors = IconDropdownMenuColors(
            primaryContentBackground = LocalPalette.current.surfaceContainer,
            dropdownContentBackground = LocalPalette.current.surfaceContainer
        )
    ) { false }
}

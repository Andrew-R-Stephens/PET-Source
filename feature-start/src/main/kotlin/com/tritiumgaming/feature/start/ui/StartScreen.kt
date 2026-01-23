package com.tritiumgaming.feature.start.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.admob.BannerAd
import com.tritiumgaming.core.ui.common.menus.IconDropdownMenu
import com.tritiumgaming.core.ui.common.menus.IconDropdownMenuColors
import com.tritiumgaming.core.ui.common.menus.SecondarySelector
import com.tritiumgaming.core.ui.common.prefabicon.AccountIcon
import com.tritiumgaming.core.ui.common.prefabicon.AccountIconPrimaryContent
import com.tritiumgaming.core.ui.common.prefabicon.BadgeIcon
import com.tritiumgaming.core.ui.common.prefabicon.LanguageIcon
import com.tritiumgaming.core.ui.common.prefabicon.NotificationIndicator
import com.tritiumgaming.core.ui.icon.ButtonScratchedIcon
import com.tritiumgaming.core.ui.icon.GearIcon
import com.tritiumgaming.core.ui.icon.HamburgerMenuIcon
import com.tritiumgaming.core.ui.icon.InfoIcon
import com.tritiumgaming.core.ui.icon.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.PersonIcon
import com.tritiumgaming.core.ui.icon.ReviewIcon
import com.tritiumgaming.core.ui.icon.StoreIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.start.ui.newsletter.NewsletterInboxesUiState
import com.tritiumgaming.feature.start.ui.reviewpopup.ReviewUiState
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.core.ui.mappers.IconResources.IconResource
import java.util.Locale

@Composable
@Preview(locale = "uk")
private fun StartButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        StartButton()
    }
}

@Composable
@Preview
private fun StartButtonPreview2() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        StartButton()
    }
}

@Composable
fun StartScreen(
    startViewModel: StartViewModel,
    navController: NavHostController
) {

    StartContent(
        startViewModel = startViewModel,
        navController = navController
    )

}


@Composable
private fun StartContent(
    startViewModel: StartViewModel,
    navController: NavController
) {

    val newsletterInboxesUiState by startViewModel.inboxesUiState.collectAsStateWithLifecycle()
    val reviewUiState by startViewModel.reviewFlow.collectAsStateWithLifecycle()

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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderNavBar(
                newsletterInboxesUiState = newsletterInboxesUiState,
                reviewUiState = reviewUiState,
                navController = navController)
        }

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> { StartContentCompactPortrait(
                navController = navController) }
            DeviceConfiguration.MOBILE_LANDSCAPE -> { StartContentCompactLandscape(
                navController = navController) }
            DeviceConfiguration.TABLET_PORTRAIT -> { StartContentCompactPortrait(
                navController = navController) }
            DeviceConfiguration.TABLET_LANDSCAPE -> { StartContentCompactLandscape(
                navController = navController) }
            DeviceConfiguration.DESKTOP -> { StartContentCompactLandscape(
                navController = navController) }
        }

        BannerAd(
            modifier = Modifier
                .fillMaxWidth(),
            adId = stringResource(R.string.ad_banner_1)
        )

        //AdmobBanner()
    }

}

@Composable
private fun ColumnScope.StartContentCompactPortrait(
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize(1f)
            .padding(PaddingValues(vertical = 16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
    ) {
        LogoSection(
            modifier = Modifier
                .weight(.6f, false)
        )
        StartSection(
            modifier = Modifier
                .weight(.4f, true),
            navController = navController
        )
    }
}

@Composable
internal fun ColumnScope.StartContentCompactLandscape(
    navController: NavController = rememberNavController()
) {
    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
            .padding(PaddingValues(vertical = 16.dp)),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoSection()
        StartSection(navController = navController)
    }
}

@Composable
private fun ColumnScope.StartContentOther(
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize(1f)
            .padding(PaddingValues(vertical = 16.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoSection()
        StartSection(navController = navController,)
    }
}

@Composable
private fun ColumnScope.LogoSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .weight(1f)
            .fillMaxWidth(.75f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconResource.ICON_LOGO_APP.ToComposable(
            modifier = Modifier
                .heightIn(max = 256.dp)
                .aspectRatio(1f)
                .weight(1f),

            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.secondary
            )
        )

        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
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
private fun RowScope.LogoSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .fillMaxSize(.9f)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconResource.ICON_LOGO_APP.ToComposable(
            modifier = Modifier
                .heightIn(max = 256.dp)
                .aspectRatio(1f, false)
                .weight(1f, false),

            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.secondary
                // fillColor = LocalPalette.current.surface,
                // strokeColor = LocalPalette.current.onSurface
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicText(
            modifier = Modifier
                .fillMaxWidth(.75f)
                .wrapContentWidth()
                .heightIn(max = 48.dp)
                .wrapContentHeight(),
            text = stringResource(R.string.titlescreen_description),
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.primary,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            autoSize = TextAutoSize.StepBased(minFontSize = 14.sp, maxFontSize = 36.sp, stepSize = 2.sp)
        )

    }
}

@Composable
private fun ColumnScope.StartSection(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    Column(
        modifier = modifier
            .weight(1f, false),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StartButton {
            navController.navigate(NavRoute.SCREEN_INVESTIGATION.route)
        }

        Spacer(modifier = Modifier.height(32.dp))

        LanguageButton {
            navController.navigate(NavRoute.SCREEN_LANGUAGE.route)
        }

    }
}

@Composable
private fun RowScope.StartSection(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = modifier
            .weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StartButton {
            navController.navigate(NavRoute.SCREEN_INVESTIGATION.route)
        }

        Spacer(modifier = Modifier.height(32.dp))

        LanguageButton {
            navController.navigate(NavRoute.SCREEN_LANGUAGE.route)
        }

    }
}

@Composable
private fun LanguageButton(
    onClick: () -> Unit = {}
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LanguageIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                //fillColor = LocalPalette.current.onSecondary,
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.secondary
            )
        ) {
            onClick()
        }

        val rememberLocale by remember {
            mutableStateOf(Locale.getDefault())
        }

        var currentLanguage = rememberLocale.displayLanguage
        if (currentLanguage.isNotEmpty()) {
            currentLanguage = currentLanguage.substring(0, 1)
                .uppercase(rememberLocale) + currentLanguage.substring(1)
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
                    //fillColor = LocalPalette.current.onSecondary,
                    fillColor = LocalPalette.current.surfaceContainerLow,
                    strokeColor = LocalPalette.current.secondary
                )
            )

            BasicText(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.titlescreen_button).uppercase(),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.secondary,
                    textAlign = TextAlign.Center,
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 5.sp)
            )


        }

    }

}

@Composable
private fun HeaderNavBar(
    navController: NavController,
    newsletterInboxesUiState: NewsletterInboxesUiState,
    reviewUiState: ReviewUiState
) {

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.link_discordInvite)
    val patreonInvitation = stringResource(R.string.link_patreonInvite)

    val inboxNotificationState = newsletterInboxesUiState.inboxes
        .sortedByDescending { it.lastReadDate }
        .firstOrNull { inboxUiState ->
            inboxUiState.inbox.compareDates(inboxUiState.lastReadDate)
        } != null

    val timesOpened = reviewUiState.timesOpened
    Log.d("StartScreen", "$timesOpened")
    val canRequestReview = reviewUiState.canRequestReview

    val menuIcon: @Composable () -> Unit = {
        HamburgerMenuIcon(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),

            colors = IconVectorColors.defaults(
                //fillColor = LocalPalette.current.onSecondary,
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.secondary
            )
        )
    }
    val infoIcon: @Composable () -> Unit = {
        InfoIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                //fillColor = LocalPalette.current.onSecondary,
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.secondary
            )
        )
    }
    val gearIcon: @Composable () -> Unit = {
        GearIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                //fillColor = LocalPalette.current.onSecondary,
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.secondary
            )
        )
    }
    val languageIcon: @Composable () -> Unit = {
        LanguageIcon(
            modifier = Modifier
                .size(48.dp),
            colors = IconVectorColors.defaults(
                //fillColor = LocalPalette.current.onSecondary,
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.secondary
            )
        ) {
            navController.navigate(NavRoute.SCREEN_LANGUAGE.route)
        }
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
                    //fillColor = LocalPalette.current.onSecondary,
                    fillColor = LocalPalette.current.surfaceContainerLow,
                    strokeColor = LocalPalette.current.secondary
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

    val reviewIcon: @Composable () -> Unit = { ReviewIcon(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp),
        colors = IconVectorColors.defaults(
            //fillColor = LocalPalette.current.onSecondary,
            fillColor = LocalPalette.current.surfaceContainerLow,
            strokeColor = LocalPalette.current.secondary
        )
    ) }

    val accountIcon: @Composable () -> Unit = {
        AccountIcon(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
            borderColor =  LocalPalette.current.secondary,
            backgroundColor = LocalPalette.current.surfaceContainerLow,
            // borderColor =  LocalPalette.current.secondary,
            // backgroundColor = LocalPalette.current.onSecondary,
            placeholder = {
                IconResource.PERSON.ToComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    colors = IconVectorColors.defaults(
                        //fillColor = LocalPalette.current.onSecondary,
                        fillColor = LocalPalette.current.surfaceContainerLow,
                        strokeColor = LocalPalette.current.secondary
                    )
                )
            },
            content = {
                val authUser = Firebase.auth.currentUser
                authUser?.let { auth ->
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
            fillColor = LocalPalette.current.secondary,
            strokeColor = LocalPalette.current.secondary
        )
    ) }

    IconDropdownMenu(
        primaryContent = menuIcon,
        dropdownContent = @Composable {
            SecondarySelector(
                onClick = { navController.navigate(NavRoute.SCREEN_APP_INFO.route) }) {
                infoIcon()
            }
            SecondarySelector(
                onClick = { navController.navigate(NavRoute.SCREEN_SETTINGS.route) }) {
                gearIcon()
            }
            SecondarySelector(
                onClick = { navController.navigate(NavRoute.SCREEN_LANGUAGE.route) }) {
                languageIcon()
            }
            SecondarySelector(
                onClick = {
                    try {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://patreon.com/ $patreonInvitation".toUri()
                            )
                        )
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                }
            ) {
                patreonIcon()
            }
            SecondarySelector(
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            "https://discord.gg/ $discordInvitation".toUri()
                        )
                    )
                }) {
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
                    //fillColor = LocalPalette.current.onSecondary,
                    fillColor = LocalPalette.current.surfaceContainerLow,
                    strokeColor = LocalPalette.current.secondary
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
        navController.navigate(NavRoute.NAVIGATION_NEWSLETTER.route)
    }

    /*NotificationIndicator(
        isActive = notificationState,
        alertIcon = IconResource.NOTIFY,
        alertTint = IconVectorColors(
            fillColor = LocalPalette.current.surface,
            strokeColor = LocalPalette.current.inboxNotification
        )
    ) {
        navController.navigate(NavRoute.NAVIGATION_NEWSLETTER.route)
    }*/

    //reviewIcon()
    if(canRequestReview) {
        reviewIcon()
    }

    IconDropdownMenu(
        primaryContent = accountIcon,
        dropdownContent = @Composable {
            SecondarySelector(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                onClick = { navController.navigate(NavRoute.SCREEN_ACCOUNT_OVERVIEW.route) }) {
                personIcon()
            }
            SecondarySelector(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                onClick = { navController.navigate(NavRoute.SCREEN_MARKETPLACE_UNLOCKS.route) }) {
                storeIcon()
            }
        },
        colors = IconDropdownMenuColors(
            primaryContentBackground = LocalPalette.current.surfaceContainer,
            dropdownContentBackground = LocalPalette.current.surfaceContainer
        )
    ) { false }
}

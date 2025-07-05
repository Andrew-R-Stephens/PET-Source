package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.AccountIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.DiscordIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.LanguageIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.NotificationIndicator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.config.DeviceConfiguration
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.ButtonScratchedIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.GearIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.HamburgerMenuIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.InfoIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.PersonIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.ReviewIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.StoreIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.menus.DropdownClickPair
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.menus.DropdownNavPair
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.menus.IconDropdownMenu
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.NewsletterViewModel
import java.util.Locale

@Composable
@Preview
private fun StartScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        StartScreen(
            rememberNavController()
        )
    }

}

@Composable
@Preview
private fun StartButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        StartButton()
    }
}

@Composable
fun StartScreen(
    navController: NavHostController
) {

    MainMenuScreen {
        StartContent(navController)
    }

}


@Composable
private fun StartContent(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderNavBar(navController)
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

        AdmobBanner()
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
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoSection()
        StartSection(navController)
    }
}

@Composable
private fun ColumnScope.StartContentCompactLandscape(
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
        StartSection(navController)
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
        StartSection(navController)
    }
}

@Composable
private fun ColumnScope.LogoSection() {
    Column(
        modifier = Modifier
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconResource.ICON_LOGO_APP.ToComposable(
            modifier = Modifier
                .aspectRatio(1f)
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp)
        )

        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = stringResource(R.string.titlescreen_description),
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.splashTextColor,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            autoSize = TextAutoSize.StepBased(minFontSize = 14.sp, maxFontSize = 36.sp, stepSize = 2.sp)
        )

    }
}

@Composable
private fun RowScope.LogoSection() {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconResource.ICON_LOGO_APP.ToComposable(
            modifier = Modifier
                .aspectRatio(1f, false)
                .weight(1f, false)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = stringResource(R.string.titlescreen_description),
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.splashTextColor,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            autoSize = TextAutoSize.StepBased(minFontSize = 14.sp, maxFontSize = 36.sp, stepSize = 2.sp)
        )

    }
}

@Composable
private fun ColumnScope.StartSection(
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = Modifier
            .weight(1f, false),
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
private fun RowScope.StartSection(
    navController: NavController = rememberNavController()
) {
    Column(
        modifier = Modifier
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

        LanguageIcon {
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
                color = LocalPalette.current.textFamily.body,
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
            .wrapContentWidth(),
        onClick = { onClick() },
        enabled = true,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LocalPalette.current.background.color,
            contentColor = LocalPalette.current.textFamily.body
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            contentAlignment = Alignment.Center,
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
            ) {

                ButtonScratchedIcon()

            }
            BasicText(
                modifier = Modifier
                    .padding(8.dp),
                text = stringResource(R.string.titlescreen_button),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center,
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 5.sp, maxFontSize = 48.sp, stepSize = 5.sp)
            )

        }

    }

}

@Composable
private fun HeaderNavBar(
    navController: NavController,
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
) {

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.aboutinfo_discordInvite)

    val menuIcon: @Composable () -> Unit = { HamburgerMenuIcon() }
    val infoIcon: @Composable () -> Unit = { InfoIcon() }
    val gearIcon: @Composable () -> Unit = { GearIcon() }
    val languageIcon: @Composable () -> Unit = { LanguageIcon() }
    val discordIcon: @Composable () -> Unit = { DiscordIcon() }
    val reviewIcon: @Composable () -> Unit = { ReviewIcon() }
    val accountIcon: @Composable () -> Unit = { AccountIcon() }
    val personIcon: @Composable () -> Unit = { PersonIcon() }
    val storeIcon: @Composable () -> Unit = { StoreIcon() }

    val rememberNewsUpToDate = newsletterViewModel.mainNotificationState.collectAsStateWithLifecycle()

    IconDropdownMenu(
        menuIcon,
        navController,
        arrayOf(
            DropdownNavPair(infoIcon, NavRoute.SCREEN_APP_INFO),
            DropdownNavPair(gearIcon, NavRoute.SCREEN_SETTINGS),
            DropdownNavPair(languageIcon, NavRoute.SCREEN_LANGUAGE),
            DropdownClickPair(discordIcon) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, "https://discord.gg/ $discordInvitation".toUri()
                    )
                )
            }
        )
    ) { false }

    // News Button
    NotificationIndicator(
        isActive = rememberNewsUpToDate.value,
        alertIcon = IconResource.NOTIFY,
    ) {
        navController.navigate(NavRoute.NAVIGATION_NEWSLETTER.route)
    }

    reviewIcon


    IconDropdownMenu(
        accountIcon,
        navController,
        arrayOf(
            DropdownNavPair(personIcon, NavRoute.SCREEN_ACCOUNT_OVERVIEW),
            DropdownNavPair(storeIcon, NavRoute.SCREEN_MARKETPLACE_UNLOCKS)
        )
    )

}
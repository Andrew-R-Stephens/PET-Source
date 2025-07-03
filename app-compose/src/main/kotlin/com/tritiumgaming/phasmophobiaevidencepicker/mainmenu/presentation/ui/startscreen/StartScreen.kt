package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.AccountIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.LanguageIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.NotificationIndicator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
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

        Column(
            modifier = Modifier
                .fillMaxWidth(.75f)
                .weight(1f)
                .padding(PaddingValues(vertical = 16.dp)),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconResource.ICON_LOGO_APP.ToComposable(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
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
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StartButton(
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                ) {
                    navController.navigate(NavRoute.SCREEN_INVESTIGATION.route)
                }

                LanguageButton {
                    navController.navigate(NavRoute.SCREEN_LANGUAGE.route)
                }

            }

        }

        AdmobBanner()
    }

}

@Composable
private fun LanguageButton(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
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

        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = currentLanguage,
            style = LocalTypography.current.primary.regular,
            maxLines = 1,
            color = LocalPalette.current.textFamily.body,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
private fun StartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .height(48.dp),
        onClick = { onClick() },
        enabled = true,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {

            IconResource.BUTTON_SCRATCHED.ToComposable(
                modifier = Modifier
                    .fillMaxSize()
            )

            BasicText(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                text = stringResource(R.string.titlescreen_button),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
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

    val menuIcon: @Composable () -> Unit = { IconResource.HAMBURGER_MENU.ToComposable() }
    val infoIcon: @Composable () -> Unit = { IconResource.INFO.ToComposable() }
    val gearIcon: @Composable () -> Unit = { IconResource.GEAR.ToComposable() }
    val languageIcon: @Composable () -> Unit = { LanguageIcon() }
    val discordIcon: @Composable () -> Unit = { IconResource.DISCORD.ToComposable() }
    val reviewIcon: @Composable () -> Unit = { IconResource.REVIEW.ToComposable() }
    val accountIcon: @Composable () -> Unit = { AccountIcon() }
    val personIcon: @Composable () -> Unit = { IconResource.PERSON.ToComposable() }
    val storeIcon: @Composable () -> Unit = { IconResource.STORE.ToComposable() }

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

    //DO NOT DELETE
    /*IconDropdownMenu(
        accountIcon,
        navController,
        arrayOf(
            DropdownNavPair(R.drawable.ic_person, NavRoute.SCREEN_ACCOUNT_OVERVIEW),
            DropdownNavPair(R.drawable.ic_store, NavRoute.SCREEN_MARKETPLACE_UNLOCKS)
        )
    )*/
}
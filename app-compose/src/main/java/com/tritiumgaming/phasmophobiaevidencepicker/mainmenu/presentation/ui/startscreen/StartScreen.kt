package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen

import android.content.Intent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.component.AccountIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common.LanguageIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common.NotificationIndicator
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
    navController: NavController,
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory)
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

            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.icon_logo_app),
                contentDescription = "",
                contentScale = ContentScale.Fit
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
                //val activity = (LocalContext.current) as PETActivity

                StartButton(
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                ) {
                    navController.navigate(NavRoute.SCREEN_INVESTIGATION.route)

                    /*activity.signIn(
                        activity,
                        SignInCredentialManager.SignInOptions.GOOGLE,
                        onSuccess = {
                            //activity.recreate()
                        }
                    )*/
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

            Image(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = ImageVector.vectorResource(R.drawable.button_scratched),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {

                BasicText(
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

}

@Composable
private fun HeaderNavBar(
    navController: NavController,
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
) {

    val context = LocalContext.current
    val discordInvitation = stringResource(R.string.aboutinfo_discordInvite)
    val languageIcon: @Composable () -> Unit = { LanguageIcon() }

    val rememberNewsUpToDate = newsletterViewModel.mainNotificationState.collectAsStateWithLifecycle()

    IconDropdownMenu(
        R.drawable.ic_menu,
        navController,
        arrayOf(
            DropdownNavPair(R.drawable.ic_info, NavRoute.SCREEN_APP_INFO),
            DropdownNavPair(R.drawable.ic_gear, NavRoute.SCREEN_SETTINGS),
            DropdownNavPair(languageIcon, NavRoute.SCREEN_LANGUAGE),
            DropdownClickPair(R.drawable.ic_discord) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, "https://discord.gg/ $discordInvitation".toUri()
                    )
                )
            }
        )
    ) { false }

    // News Button
    NotificationIndicator(isActive = rememberNewsUpToDate.value) {
        navController.navigate(NavRoute.NAVIGATION_NEWSLETTER.route)
    }

    Image(
        painter = painterResource(R.drawable.ic_review),
        contentDescription = ""
    )

    val accountIcon: @Composable () -> Unit = {
        AccountIcon()
    }

    IconDropdownMenu(
        accountIcon,
        navController,
        arrayOf(
            DropdownNavPair(R.drawable.ic_person, NavRoute.SCREEN_ACCOUNT_OVERVIEW),
            DropdownNavPair(R.drawable.ic_store, NavRoute.SCREEN_MARKETPLACE_UNLOCKS)
        )
    )
}
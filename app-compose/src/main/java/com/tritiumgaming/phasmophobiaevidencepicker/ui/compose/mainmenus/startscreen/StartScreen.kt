package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.startscreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoutes
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.DropdownClickPair
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.DropdownNavPair
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.IconDropdownMenu
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.LanguageIcon
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.NewsAlert
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.ui.views.account.AccountIcon

@Composable
@Preview
private fun StartScreenPreview() {
    SelectiveTheme(
        theme = Non_Colorblind,
        typography = Classic
    ) {
        StartScreen(
            rememberNavController()
        )
    }

}

@Composable
@Preview
private fun StartButtonPreview() {
    StartButton()
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
    mainMenuViewModel: MainMenuViewModel = viewModel(),
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
                .padding(PaddingValues(top = 16.dp)),
            verticalArrangement = Arrangement.Top,
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

            AutoResizedText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = stringResource(R.string.titlescreen_description),
                textAlign = TextAlign.Center,
                color = LocalPalette.current.splashTextColor,
                style = LocalTypography.current.primary.regular.copy(
                    fontSize = 64.sp,
                    drawStyle = Stroke(
                        miter = 1f,
                        width = .5f,
                        join = StrokeJoin.Bevel,
                    ),
                    color = LocalPalette.current.surface.onColor
                )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                StartButton(
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                ) {
                    navController.navigate(NavRoutes.NAVIGATION_INVESTIGATION.route)
                }
            }
        }

        AdmobBanner()
    }

}

@Composable
private fun StartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clickable { onClick() }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            imageVector = ImageVector.vectorResource(R.drawable.button_scratched),
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AutoResizedText(
                modifier = Modifier
                    .fillMaxSize(.9f)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = stringResource(R.string.titlescreen_button),
                textAlign = TextAlign.Center,
                constrainWidth = true,
                constrainHeight = true,
                color = LocalPalette.current.textFamily.body,
                style = LocalTypography.current.primary.regular
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
    val languageIcon: @Composable () -> Unit = { LanguageIcon() }
    val rememberNewsUpToDate by remember{
        mutableStateOf(!newsletterViewModel.isUpToDate)
    }

    IconDropdownMenu(
        R.drawable.ic_menu,
        navController,
        arrayOf(
            DropdownNavPair(R.drawable.ic_info, NavRoutes.SCREEN_APP_INFO),
            DropdownNavPair(R.drawable.ic_gear, NavRoutes.SCREEN_SETTINGS),
            DropdownNavPair(languageIcon, NavRoutes.SCREEN_LANGUAGE),
            DropdownClickPair(R.drawable.ic_discord) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(
                            "https://discord.gg/ $discordInvitation"
                        )
                    )
                )
            }
        )
    ) { false }

    // News Button
    NewsAlert(isActive = rememberNewsUpToDate) {
        navController.navigate(NavRoutes.NAVIGATION_NEWSLETTER.route)
    }

    Image(
        painter = painterResource(R.drawable.ic_review),
        contentDescription = ""
    )

    val accountIcon: @Composable () -> Unit = {
        AccountIcon(
            authUser = com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.user.FirestoreUser.currentFirebaseUser
        )
    }

    IconDropdownMenu(
        accountIcon,
        navController,
        arrayOf(
            DropdownNavPair(R.drawable.ic_person, NavRoutes.SCREEN_ACCOUNT_OVERVIEW),
            DropdownNavPair(R.drawable.ic_store, NavRoutes.SCREEN_MARKETPLACE_UNLOCKS)
        )
    )
}
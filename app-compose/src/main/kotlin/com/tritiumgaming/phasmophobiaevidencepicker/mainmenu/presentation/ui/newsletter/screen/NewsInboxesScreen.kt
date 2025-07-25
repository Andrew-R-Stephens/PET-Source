package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon.NotificationIndicator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.config.DeviceConfiguration
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.mappers.toIconResource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.NewsletterViewModel

@Composable
@Preview
private fun NewsInboxesScreenPreview() {

    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface.color
        ) {
            NewsInboxesScreen()
        }
    }
}

@Composable
fun NewsInboxesScreen(
    //content: @Composable () -> Unit
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory)
) {

    MainMenuScreen {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NavigationHeaderComposable(
                NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    rightType = PETImageButtonType.NONE,
                    centerTitleRes = R.string.newsletter_title,
                    leftOnClick = { navController.popBackStack() }
                )
            )

            HorizontalDivider()

            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

            when(deviceConfiguration) {
                DeviceConfiguration.MOBILE_PORTRAIT -> {
                    NewsInboxesContentPortrait(
                        navController = navController,
                        newsletterViewModel = newsletterViewModel
                    )
                }
                DeviceConfiguration.MOBILE_LANDSCAPE,
                DeviceConfiguration.TABLET_PORTRAIT,
                DeviceConfiguration.TABLET_LANDSCAPE,
                DeviceConfiguration.DESKTOP -> {
                    NewsInboxesContentLandscape(
                        navController = navController
                    )
                }
            }

            AdmobBanner()
        }

    }

}


@Composable
private fun ColumnScope.NewsInboxesContentPortrait(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory)
) {

    val inboxes = newsletterViewModel.inboxes.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
    ) {

        items(items = inboxes.value) { inbox ->

            InboxCard(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                title = inbox.title.toStringResource(),
                icon = inbox.icon,
                isActive = false,
                onClick = {
                    navController.navigate(
                        route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGES.route}/${inbox.id}")
                }
            )

        }

    }

}

@Composable
private fun ColumnScope.NewsInboxesContentLandscape(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory)
) {

    val inboxes = newsletterViewModel.inboxes.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .widthIn(max = 600.dp)
            .padding(8.dp),
        verticalArrangement = Arrangement.Top
    ) {

        items(items = inboxes.value) { inbox ->

            InboxCard(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                title = inbox.title.toStringResource(),
                icon = inbox.icon,
                isActive = false,
                onClick = {
                    navController.navigate(
                        route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGES.route}/${inbox.id}")
                }
            )

        }

    }

}

@Composable
private fun InboxCard(
    modifier: Modifier = Modifier,
    title: Int = R.string.newsletter_title,
    @DrawableRes icon: Int = R.drawable.ic_notify,
    isActive: Boolean = true,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(CornerSize(16.dp)),
        onClick = { onClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            NotificationIndicator(
                modifier = Modifier
                    .size(98.dp),
                isActive = isActive,
                baseDrawableId = icon
            ) {
                onClick()
            }

            var rememberOverflow by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (rememberOverflow) Modifier.wrapContentHeight()
                        else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {

                BasicText(
                    text = stringResource(title),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.textFamily.primary,
                        textAlign = TextAlign.Center
                    ),
                    softWrap = true,
                    maxLines = if (rememberOverflow) 2 else 1,
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 24.sp, maxFontSize = 36.sp, stepSize = 5.sp),
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.hasVisualOverflow) {
                            rememberOverflow = true
                        }
                    },
                )

            }

        }

    }
}

@Composable
private fun InboxCard(
    modifier: Modifier = Modifier,
    title: Int = R.string.newsletter_title,
    icon: NewsletterIcon = NewsletterIcon.GENERAL_NEWS,
    isActive: Boolean = true,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(CornerSize(16.dp)),
        onClick = { onClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            NotificationIndicator(
                modifier = Modifier
                    .size(98.dp),
                isActive = isActive,
                baseIcon = icon.toIconResource()
            ) {
                onClick()
            }

            var rememberOverflow by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (rememberOverflow) Modifier.wrapContentHeight()
                        else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {

                BasicText(
                    text = stringResource(title),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.textFamily.primary,
                        textAlign = TextAlign.Center
                    ),
                    softWrap = true,
                    maxLines = if (rememberOverflow) 2 else 1,
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 24.sp, maxFontSize = 36.sp, stepSize = 5.sp),
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.hasVisualOverflow) {
                            rememberOverflow = true
                        }
                    },
                )

            }

        }

    }
}

@Composable
@Preview(locale = "de")
private fun InboxButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            InboxCard(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                title = R.string.newsletter_inbox_title_general,
                icon = NewsletterIcon.GENERAL_NEWS
            )

            InboxCard(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                title = R.string.newsletter_inbox_title_phasmophobia,
                icon = NewsletterIcon.PET_CHANGELOG
            )

            InboxCard(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                title = R.string.newsletter_inbox_title_pet,
                icon = NewsletterIcon.PHASMOPHOBIA_CHANGELOG
            )

        }
    }
}
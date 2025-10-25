package com.tritiumgaming.feature.home.ui.newsletter.screen

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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.admob.BannerAd
import com.tritiumgaming.core.ui.common.menus.NavHeaderComposableParams
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.PETImageButtonType
import com.tritiumgaming.core.ui.common.prefabicon.NotificationIndicator
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.home.app.mappers.toIconResource
import com.tritiumgaming.feature.home.app.mappers.toStringResource
import com.tritiumgaming.feature.home.ui.MainMenuScreen
import com.tritiumgaming.feature.home.ui.newsletter.NewsletterViewModel
import com.tritiumgaming.shared.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.home.domain.newsletter.mapper.NewsletterResources.NewsletterIcon

@Composable
fun NewsInboxesScreen(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    MainMenuScreen {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NavigationHeaderComposable(
                params = NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    rightType = PETImageButtonType.NONE,
                    centerTitleRes = R.string.newsletter_title,
                    leftOnClick = { navController.popBackStack() }
                )
            )

            HorizontalDivider()

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
                        navController = navController,
                        newsletterViewModel = newsletterViewModel
                    )
                }
            }

            BannerAd(
                modifier = Modifier
                    .fillMaxWidth(),
                adId = stringResource(R.string.ad_banner_1)
            )

            // AdmobBanner()
        }

    }

}


@Composable
private fun ColumnScope.NewsInboxesContentPortrait(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel
) {
    val context = LocalContext.current

    val inboxesUiState = newsletterViewModel.inboxesUiState.collectAsStateWithLifecycle()
    val inboxes = inboxesUiState.value.inboxes

    val refreshUiState = newsletterViewModel.refreshUiState.collectAsStateWithLifecycle()
    val isRefreshing = refreshUiState.value.isRefreshing

    PullToRefresh(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp),
        onRefresh = {
            newsletterViewModel.refreshInboxes(
                /*onFailure = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }*/
            )

        },
        isRefreshing = isRefreshing
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
        ) {

            items(items = inboxes) { inboxUiState ->

                val inbox = inboxUiState.inbox
                val notificationState = inbox.compareDates(inboxUiState.lastReadDate)

                InboxCard(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    title = inbox.title.toStringResource(),
                    icon = inbox.icon,
                    isActive = notificationState,
                    onClick = {
                        navController.navigate(
                            route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGES.route}/${inbox.id}")
                    }
                )

            }

        }
    }

}

@Composable
private fun ColumnScope.NewsInboxesContentLandscape(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel
) {
    val context = LocalContext.current

    val inboxesUiState = newsletterViewModel.inboxesUiState.collectAsStateWithLifecycle()
    val inboxes = inboxesUiState.value.inboxes

    val refreshUiState = newsletterViewModel.refreshUiState.collectAsStateWithLifecycle()
    val isRefreshing = refreshUiState.value.isRefreshing

    PullToRefresh(
        modifier = Modifier
            .weight(1f)
            .widthIn(max = 600.dp)
            .padding(8.dp),
        onRefresh = {
            newsletterViewModel.refreshInboxes(
                /*onFailure = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }*/
            )
        },
        isRefreshing = isRefreshing
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top
        ) {

            items(items = inboxes) { inboxUiState ->

                val inbox = inboxUiState.inbox
                val notificationState = inbox.compareDates(inboxUiState.lastReadDate)

                InboxCard(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    title = inbox.title.toStringResource(),
                    icon = inbox.icon,
                    isActive = notificationState,
                    onClick = {
                        navController.navigate(
                            route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGES.route}/${inbox.id}"
                        )
                    }
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
                baseComponent = @Composable { modifier ->
                    icon.toIconResource().ToComposable(
                        modifier = modifier,
                        colors = IconVectorColors(
                            fillColor = LocalPalette.current.background.color,
                            strokeColor = LocalPalette.current.textFamily.body
                        ),
                    )
                },
                badgeComponent = @Composable { modifier ->
                    IconResource.NOTIFY.ToComposable(
                        modifier = modifier,
                        colors = IconVectorColors(
                            fillColor = LocalPalette.current.background.color,
                            strokeColor = LocalPalette.current.inboxNotification
                        )
                    )
                },
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
fun PullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        content()
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
package com.tritiumgaming.feature.home.ui.newsletter.screen

import android.widget.Toast
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.admob.AdmobBanner
import com.tritiumgaming.core.ui.common.admob.BannerAd
import com.tritiumgaming.core.ui.common.menus.NavHeaderComposableParams
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.PETImageButtonType
import com.tritiumgaming.core.ui.common.prefabicon.NotificationIndicator
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.home.app.mappers.toStringResource
import com.tritiumgaming.feature.home.ui.MainMenuScreen
import com.tritiumgaming.feature.home.ui.newsletter.NewsletterViewModel
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterMessage


@Composable
@Preview
private fun NewsMessagesScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface.color
        ) {
            NewsMessagesScreen(
                navController = rememberNavController(),
                newsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
                inboxID = "0"
            )
        }
    }
}

@Composable
fun NewsMessagesScreen(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    inboxID: String = "0"
) {
    val rememberInboxID by remember{ mutableStateOf(inboxID) }

    val inboxesUiState = newsletterViewModel.inboxesUiState.collectAsStateWithLifecycle()
    val inboxes = inboxesUiState.value.inboxes

    val inboxUiState = inboxes.first { inboxUiState -> inboxUiState.inbox.id == rememberInboxID }
    val inbox = inboxUiState.inbox

    MainMenuScreen {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            NavigationHeaderComposable(
                params = NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    rightType = PETImageButtonType.NONE,
                    centerTitleRes = inbox.title.toStringResource(),
                    leftOnClick = { navController.popBackStack() }
                )
            )

            HorizontalDivider()

            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

            when (deviceConfiguration) {
                DeviceConfiguration.MOBILE_PORTRAIT -> {

                    NewsMessagesContentCompactPortrait(
                        navController = navController,
                        newsletterViewModel = newsletterViewModel,
                        inbox = inbox
                    )

                }

                DeviceConfiguration.MOBILE_LANDSCAPE -> {

                    NewsMessagesContentCompactLandscape(
                        navController = navController,
                        newsletterViewModel = newsletterViewModel,
                        inbox = inbox
                    )

                }

                DeviceConfiguration.TABLET_PORTRAIT,
                DeviceConfiguration.TABLET_LANDSCAPE,
                DeviceConfiguration.DESKTOP -> {
                    NewsMessagesContentCompactLandscape(
                        navController = navController,
                        newsletterViewModel = newsletterViewModel,
                        inbox = inbox
                    )

                }
            }

            BannerAd(
                modifier = Modifier
                    .fillMaxWidth(),
                adId = stringResource(R.string.ad_banner_1)
            )

            //AdmobBanner()
        }

    }

}

@Composable
fun ColumnScope.NewsMessagesContentCompactPortrait(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    inbox: NewsletterInbox,
) {
    val context = LocalContext.current

    val inboxesUiState = newsletterViewModel.inboxesUiState.collectAsStateWithLifecycle()

    val inboxUiState = inboxesUiState.value.inboxes.first { inboxUiState ->
        inboxUiState.inbox.id == inbox.id }
    val lastReadDate = inboxUiState.lastReadDate

    val refreshUiState = newsletterViewModel.refreshUiState.collectAsStateWithLifecycle()
    val isRefreshing = refreshUiState.value.isRefreshing

    val messages = inbox.channel?.messages

    val rememberListState = rememberLazyListState()

    Box(
        modifier = modifier
            .padding(PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
            .align(Alignment.End)
    ) {
        MarkAsReadButton(
            newsletterViewModel,
            inboxesUiState.value.inboxes.firstOrNull {
                it.inbox.compareDates(lastReadDate) } != null,
            inbox,
            messages
        )
    }

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
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            state = rememberListState
        ) {

            items(items = messages ?: listOf()) { message ->

                val rememberMessage by remember { mutableStateOf(message) }

                MessageCard(
                    message = rememberMessage,
                    isActive = rememberMessage.compareDate(lastReadDate) > 0,
                    onClick = {
                        inbox.id?.let { id ->
                            newsletterViewModel.saveInboxLastReadDate(
                                id, rememberMessage.dateEpoch
                            )
                            navController.navigate(
                                route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGE.route}/" +
                                        "${id}/${rememberMessage.id}"
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )

            }

        }
    }

}

@Composable
fun ColumnScope.NewsMessagesContentCompactLandscape(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    inbox: NewsletterInbox,
) {
    val context = LocalContext.current

    val inboxesUiState = newsletterViewModel.inboxesUiState.collectAsStateWithLifecycle()

    val inboxUiState = inboxesUiState.value.inboxes.first { inboxUiState ->
        inboxUiState.inbox.id == inbox.id }
    val lastReadDate = inboxUiState.lastReadDate

    val refreshUiState = newsletterViewModel.refreshUiState.collectAsStateWithLifecycle()
    val isRefreshing = refreshUiState.value.isRefreshing

    val messages = inbox.channel?.messages

    val rememberListState = rememberLazyListState()

    Row (
        modifier = modifier
            .weight(1f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        PullToRefresh(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth(),
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
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                state = rememberListState
            ) {

                items(items = messages ?: listOf()) { message ->

                    val rememberMessage by remember { mutableStateOf(message) }

                    MessageCard(
                        message = rememberMessage,
                        isActive = rememberMessage.compareDate(lastReadDate) > 0,
                        onClick = {
                            inbox.id?.let { id ->
                                newsletterViewModel.saveInboxLastReadDate(
                                    id, rememberMessage.dateEpoch
                                )
                                navController.navigate(
                                    route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGE.route}/" +
                                            "${id}/${rememberMessage.id}"
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )

                }

            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Top)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            MarkAsReadButton(
                newsletterViewModel,
                inboxesUiState.value.inboxes.firstOrNull {
                    it.inbox.compareDates(lastReadDate) } != null,
                inbox,
                messages
            )
        }
    }
}

@Composable
private fun MarkAsReadButton(
    newsletterViewModel: NewsletterViewModel,
    inboxNotificationState: Boolean?,
    inbox: NewsletterInbox?,
    messages: List<NewsletterMessage>?
) {
    Button(
        modifier = Modifier
            .height(48.dp)
            .wrapContentWidth()
            .alpha(if (inboxNotificationState == true) 1f else .4f),
        colors = ButtonDefaults.buttonColors(
            containerColor = LocalPalette.current.buttonColor,
        ),
        contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(size = 8.dp),
        enabled = inboxNotificationState == true,
        onClick = {
            inbox?.let { box ->
                messages?.firstOrNull()?.dateEpoch?.let {
                    box.id?.let { id -> newsletterViewModel.saveInboxLastReadDate(id, it) }
                }
            }
        }
    ) {

        Text(
            modifier = Modifier.padding(0.dp),
            text = stringResource(R.string.newsletter_markallread),
            color = LocalPalette.current.textFamily.body,
            style = LocalTypography.current.quaternary.bold,
            maxLines = 1
        )

    }
}

@Composable
private fun MessageCard(
    modifier: Modifier = Modifier,
    message: NewsletterMessage,
    isActive: Boolean = false,
    onClick: () -> Unit = {},
) {

    val rememberMessage by remember {
        mutableStateOf(message)
    }

    Surface(
        modifier = modifier
            .height(48.dp),
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(CornerSize(16.dp)),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if(isActive) {
                NotificationIndicator(
                    modifier = Modifier
                        .fillMaxHeight(),
                    isActive = true,
                    baseIcon = null,
                    baseTint = IconVectorColors(
                        fillColor = LocalPalette.current.background.color,
                        strokeColor = LocalPalette.current.textFamily.body
                    ),
                    alertTint = IconVectorColors(
                        fillColor = LocalPalette.current.background.color,
                        strokeColor = LocalPalette.current.inboxNotification
                    )
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                    ),
                text = rememberMessage.title ?: "",
                style = LocalTypography.current.quaternary.bold,
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )

        }

    }
}

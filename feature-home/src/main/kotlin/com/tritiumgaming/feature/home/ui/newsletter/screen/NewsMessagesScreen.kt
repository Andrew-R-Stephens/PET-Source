package com.tritiumgaming.feature.home.ui.newsletter.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.admob.BannerAd
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderSideButton
import com.tritiumgaming.core.ui.common.prefabicon.NotificationIndicator
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.home.app.mappers.toStringResource
import com.tritiumgaming.feature.home.ui.MainMenuScreen
import com.tritiumgaming.feature.home.ui.newsletter.NewsletterViewModel
import com.tritiumgaming.shared.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterMessage

@Composable
fun NewsMessagesScreen(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel,
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            NavigationHeader(
                title = stringResource(inbox.title.toStringResource()),
                onLeftClick = { navController.popBackStack() }
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

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
    newsletterViewModel: NewsletterViewModel,
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

    messages?.get(0)?.dateEpoch?.let { newestDate ->
        if(newestDate > lastReadDate) {
            Box(
                modifier = modifier
                    .padding(PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
                    .align(Alignment.End)
            ) {
                MarkAsReadButton(
                    newsletterViewModel,
                    inbox,
                    messages
                )
            }
        }
    }

    PullToRefresh(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp),
        onRefresh = {
            newsletterViewModel.refreshInboxes(
                onFailure = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
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
fun NewsMessagesContentCompactLandscape(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel,
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
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        PullToRefresh(
            modifier = Modifier
                .weight(1f, false)
                .widthIn(max = 600.dp),
            contentAlignment = Alignment.Center,
            onRefresh = {
                newsletterViewModel.refreshInboxes(
                    onFailure = { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
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

        messages?.get(0)?.dateEpoch?.let { newestDate ->
            if(newestDate > lastReadDate) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Top)
                        .wrapContentWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    MarkAsReadButton(
                        newsletterViewModel,
                        inbox,
                        messages
                    )
                }
            }
        }

    }
}

@Composable
private fun MarkAsReadButton(
    newsletterViewModel: NewsletterViewModel,
    inbox: NewsletterInbox?,
    messages: List<NewsletterMessage>?
) {
    TextButton(
        modifier = Modifier
            .wrapContentWidth()
            .height(48.dp),
        content = {
            Text(
                modifier = Modifier
                    .wrapContentHeight(),
                text = stringResource(id = R.string.newsletter_markallread).uppercase(),
                maxLines = 1,
                style = LocalTypography.current.quaternary.bold.copy(
                    fontSize = 12.sp
                ),
                textAlign = TextAlign.Center,
            )
        },
        contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
        colors = ButtonColors(
            contentColor = LocalPalette.current.onPrimary,
            containerColor = LocalPalette.current.primary,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(percent = 20),
        onClick = {
            inbox?.let { box ->
                messages?.firstOrNull()?.dateEpoch?.let {
                    box.id?.let { id -> newsletterViewModel.saveInboxLastReadDate(id, it) }
                }
            }
        },
    )
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
        color = LocalPalette.current.surfaceContainer,
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
                    isActive = true,
                    badgeComponent = @Composable { modifier ->
                        IconResource.NOTIFY.ToComposable(
                            modifier = modifier,
                            colors = IconVectorColors(
                                fillColor = LocalPalette.current.onError,
                                strokeColor = LocalPalette.current.error
                            )
                        )
                    },
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
                color = LocalPalette.current.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )

        }

    }
}

@Composable
private fun NavigationHeader(
    title: String = "",
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    NavigationHeaderComposable(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 64.dp),
        leftContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
                iconContent = { iconModifier ->
                    Image(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.ic_arrow_60_left),
                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                        contentDescription = ""
                    )
                }
            ) { onLeftClick() }
        },
        rightContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
            )
        },
        centerContent = { outerModifier ->
            NavigationHeaderCenter(
                modifier = outerModifier,
                textContent = { modifier ->
                    BasicText(
                        modifier = modifier,
                        text = title,
                        style = LocalTypography.current.primary.regular.copy(
                            color = LocalPalette.current.primary,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
                    )
                }
            )
        }
    )
}

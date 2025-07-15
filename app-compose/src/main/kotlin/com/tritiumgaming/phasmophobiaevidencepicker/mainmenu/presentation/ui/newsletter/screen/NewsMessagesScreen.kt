package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.screen

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterMessage
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter.NewsletterViewModel


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

    val inboxes = newsletterViewModel.inboxes.collectAsStateWithLifecycle()
    val inbox = inboxes.value.first { inbox -> inbox.id == rememberInboxID }

    MainMenuScreen {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            NavigationHeaderComposable(
                NavHeaderComposableParams(
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

            AdmobBanner()
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
    val activeState = inbox.inboxNotificationState

    val messages = inbox.channel?.messages
    var inboxLastReadDateState = inbox.inboxLastReadDate

    Box(
        modifier = Modifier
            .padding(PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
            .align(Alignment.End)
    ) {
        MarkAsReadButton(newsletterViewModel, activeState, inbox, messages)
    }

    val rememberListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        state = rememberListState
    ) {

        items(items = messages ?: listOf()) { message ->

            val rememberMessage by remember { mutableStateOf(message) }

            MessageCard(
                message = rememberMessage,
                isActive = rememberMessage.dateEpoch - inboxLastReadDateState > 0,
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

@Composable
fun ColumnScope.NewsMessagesContentCompactLandscape(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    inbox: NewsletterInbox,
) {

    val activeState = inbox.inboxNotificationState

    val messages = inbox.channel?.messages
    var inboxLastReadDateState = inbox.inboxLastReadDate

    val rememberListState = rememberLazyListState()

    Row (
        modifier = Modifier
            .weight(1f)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        LazyColumn(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            state = rememberListState
        ) {

            items(items = messages ?: listOf()) { message ->

                val rememberMessage by remember { mutableStateOf(message) }

                MessageCard(
                    modifier = Modifier,
                    message = rememberMessage,
                    isActive = rememberMessage.dateEpoch - inboxLastReadDateState > 0,
                    onClick = {
                        inbox.id?.let { id ->
                            newsletterViewModel.saveInboxLastReadDate(
                                id, rememberMessage.dateEpoch)

                            navController.navigate(
                                route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGE.route}/" +
                                        "${id}/${rememberMessage.id}"
                            )
                        }

                    }
                )

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
                newsletterViewModel, activeState, inbox, messages)
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
                    baseIcon = null
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
